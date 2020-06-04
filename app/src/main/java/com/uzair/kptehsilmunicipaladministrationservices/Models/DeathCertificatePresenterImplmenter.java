package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.DeathCertificatePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.DeathCertificateView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeathCertificatePresenterImplmenter implements DeathCertificatePresenter
{

    private DeathCertificateView deathView;
    private Context context;
    private List<String> urls = new ArrayList<>();
    private  int counter = 0;

    public DeathCertificatePresenterImplmenter(DeathCertificateView deathView, Context context) {
        this.deathView = deathView;
        this.context = context;
    }

    @Override
    public void onSubmitForm(final DatabaseReference dbRef, StorageReference storeRef, final FirebaseAuth mAuth,
                             final String deceasedName, final String deceasedCnic,
                             final String religion, final String relation, final String fName, final String fCnic, final String mName,
                             final String mCnic, final String husbandName, final String husbandCnic, final String deceasedDateOfBirth,
                             final String gravyardName, final String placeOfBirth, final String uc, final String gender, final List<Uri> uriList)
    {


        if(dbRef != null && storeRef !=null && mAuth != null &&
          !deceasedName.isEmpty() && !deceasedCnic.isEmpty() &&
           !religion.isEmpty() && !relation.isEmpty() && !fName.isEmpty() && !fCnic.isEmpty() &&
           !mName.isEmpty() && !mCnic.isEmpty() && !husbandName.isEmpty() && !husbandCnic.isEmpty() &&
           !deceasedDateOfBirth.isEmpty() && ! gravyardName.isEmpty() && !placeOfBirth.isEmpty() && !uc.isEmpty()
        &&!gender.isEmpty() &&!uriList.isEmpty())
        {

            deathView.onShowProgressDialog();

            for (int i = 0; i < uriList.size(); i++) {

                Uri Image = uriList.get(i);

                // storage reference to add images
                final StorageReference imagePath = storeRef.child("DeathCertificateImages")
                        .child("image/" + Image.getLastPathSegment());

                imagePath.putFile(uriList.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // to get download url
                        imagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                counter = counter + 1;
                                String url = String.valueOf(uri);
                                urls.add(url);

                                // counter is equal to urls arraylist then upload all urls and data
                                if( counter  == uriList.size()) {

                                    dbRef.child("Users").child(mAuth.getCurrentUser().getUid())
                                            .addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    String date = DateFormat.getDateTimeInstance().
                                                            format(Calendar.getInstance().getTime());

                                                    // get the applicant name and cnic
                                                    String userName = dataSnapshot.child("user_name").getValue().toString();
                                                    String userCnic = dataSnapshot.child("user_cnic").getValue().toString();

                                                    // push key ref
                                                    DatabaseReference databaseRef = dbRef.child("Certificates").push();

                                                    Map formData = new HashMap<>();
                                                    formData.put("certificateType" , "Death");
                                                    formData.put("applicantName" , userName);
                                                    formData.put("applicantCnic" , userCnic);
                                                    formData.put("deceasedName" , deceasedName);
                                                    formData.put("deceasedCnic" , deceasedCnic);
                                                    formData.put("religion" , religion);
                                                    formData.put("relation" , relation);
                                                    formData.put("fatherName" , fName);
                                                    formData.put("fatherCnic" , fCnic);
                                                    formData.put("motherName" , mName);
                                                    formData.put("motherCnic" , mCnic);
                                                    formData.put("husbandName" , husbandName);
                                                    formData.put("husbandCnic" , husbandCnic);
                                                    formData.put("placeOfBirth" , placeOfBirth);
                                                    formData.put("unionCouncil" , uc);
                                                    formData.put("gravyard" , gravyardName);
                                                    formData.put("deceasedDateOfBirth" , deceasedDateOfBirth);
                                                    formData.put("date" , date);
                                                    formData.put("pushKey" , databaseRef.getRef().getKey());
                                                    formData.put("uid" , mAuth.getCurrentUser().getUid());
                                                    formData.put("cnicImages" , urls);
                                                    formData.put("status" , "Pending");
                                                    formData.put("gender" , gender);



                                                    databaseRef.setValue(formData)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    if (task.isSuccessful()) {

                                                                        deathView.onDismissProgressDialog();
                                                                        deathView.clearAllFileds();
                                                                        deathView.showMessage("SuccessFully Apply");

                                                                    } else {

                                                                        deathView.showMessage("Error in uploading");
                                                                        deathView.onDismissProgressDialog();
                                                                    }
                                                                }
                                                            });
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {}
                                            });
                                }
                            }
                        });
                    }
                });
            }
        }
        else
        {
            deathView.showMessage("Please cnic and all fields are require");
        }



    }




    @Override
    public void onSetSpinnerAdapter() {

        deathView.setSpinnerAdapter();
    }

    @Override
    public void chooseGalleryClick() {

        deathView.chooseGallery();
    }

    @Override
    public void previewImage(List<Uri> uri)
    {
        if (uri.size() > 2)
        {
            deathView.showMessage("Please select front and back side image only");
        }
        else if(uri.size() < 2 )
        {
            deathView.showMessage("select front and back side image of cnic");
        }
        else
        {
            deathView.displayImagePreview(uri);
        }

    }
}
