package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

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
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.BirthCertificatePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.BirthCertificateView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BirthCertificatePresenterImplementer implements BirthCertificatePresenter
{

    private BirthCertificateView birthView;
    private Context context;
    private List<String> urls = new ArrayList<>();
    private  int counter = 0;

    public BirthCertificatePresenterImplementer(BirthCertificateView birthView, Context context) {
        this.birthView = birthView;
        this.context = context;
    }


    @Override
    public void onSubmitForm(final DatabaseReference dbRef, StorageReference storeRef, final FirebaseAuth mAuth,
                             final String childName, final String religion, final String relation, final String fName,
                             final String fCnic, final String mName, final String mCnic, final String gFatherName,
                             final String gFatherCnic, final String distOfBirth, final String dob, final String vaccinated,
                             final String placeOfBirth, final String mideWife, final String disability, final String address,
                             final String gender, final String uc ,final List<Uri> uriList)
    {


        if( dbRef != null && storeRef != null && mAuth != null && !uriList.isEmpty() &&
                !childName.isEmpty() && !religion.isEmpty() &&
                !relation.isEmpty() && !fName.isEmpty() &&
                !fCnic.isEmpty() && !mName.isEmpty() && !mCnic.isEmpty() &&
                !gFatherCnic.isEmpty() && !gFatherName.isEmpty() && !distOfBirth.isEmpty()
                && !dob.isEmpty() && !vaccinated.isEmpty() && !placeOfBirth.isEmpty() &&
                !mideWife.isEmpty() && !disability.isEmpty() && !address.isEmpty() &&
                !gender.isEmpty() )
        {


            birthView.onShowProgressDialog();

            for (int i = 0; i < uriList.size(); i++) {

                Uri Image = uriList.get(i);

                // storage reference to add images
                final StorageReference imagename = storeRef.child("BirthCertificateImages")
                        .child("image/" + Image.getLastPathSegment());

                imagename.putFile(uriList.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // to get download url
                        imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                                                    formData.put("certificateType" , "Birth");
                                                    formData.put("applicantName" , userName);
                                                    formData.put("applicantCnic" , userCnic);
                                                    formData.put("childName" , childName);
                                                    formData.put("religion" , religion);
                                                    formData.put("relation" , relation);
                                                    formData.put("fatherName" , fName);
                                                    formData.put("fatherCnic" , fCnic);
                                                    formData.put("motherName" , mName);
                                                    formData.put("motherCnic" , mCnic);
                                                    formData.put("grandFatherName" , gFatherName);
                                                    formData.put("grandFatherCnic" , gFatherCnic);
                                                    formData.put("districtOfBirth" , distOfBirth);
                                                    formData.put("dateOfBirth" , dob);
                                                    formData.put("disability" , disability);
                                                    formData.put("vaccinated" , vaccinated);
                                                    formData.put("doctorOrMideWife" , mideWife);
                                                    formData.put("address" , address);
                                                    formData.put("placeOfBirth" , placeOfBirth);
                                                    formData.put("gender" , gender);
                                                    formData.put("unionCouncil" , uc);
                                                    formData.put("date" , date);
                                                    formData.put("pushKey" , databaseRef.getRef().getKey());
                                                    formData.put("uid" , mAuth.getCurrentUser().getUid());
                                                    formData.put("cnicImages" , urls);
                                                    formData.put("status" , "Pending");


                                                    databaseRef.setValue(formData)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    if (task.isSuccessful()) {

                                                                        birthView.onDismissProgressDialog();
                                                                        birthView.clearAllFileds();
                                                                        birthView.showMessage("SuccessFully Apply");

                                                                    } else {

                                                                        birthView.showMessage("Error in uploading");
                                                                        birthView.onDismissProgressDialog();
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
           birthView.showMessage("Please cnic and all fields are required");
        }



    }

    @Override
    public void onSetSpinnerAdapter()
    {
       birthView.setSpinnerAdapter();
    }

    @Override
    public void chooseGalleryClick() {
            birthView.chooseGallery();
    }

    @Override
    public void previewImage(List<Uri> uri) {
        if (uri.size() > 2)
        {
            birthView.showMessage("Please select front and back side image only");
        }
        else if(uri.size() < 2 )
        {
            birthView.showMessage("select front and back side image of cnic");
        }
        else
            {
            birthView.displayImagePreview(uri);
        }
    }
}
