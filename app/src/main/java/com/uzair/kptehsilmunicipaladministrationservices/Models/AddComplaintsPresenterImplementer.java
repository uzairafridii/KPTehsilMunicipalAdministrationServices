package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain.AddComplaints;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.AddComplaintsPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.AddComplaintsView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddComplaintsPresenterImplementer implements AddComplaintsPresenter
{
     private AddComplaintsView addComplaintsView;
     private List<String> imageUrls = new ArrayList<>();
     private int counter = 0;

    public AddComplaintsPresenterImplementer(AddComplaintsView addComplaintsView) {
        this.addComplaintsView = addComplaintsView;
    }

    @Override
    public void storeComplaintDataToFirebase(DatabaseReference dbRef, FirebaseAuth mAuth,
                                             StorageReference mStorageReference, String title,
                                             String description, String field,
                                             List<Uri> imageUriList)
    {
        if (!title.isEmpty() && !description.isEmpty() && imageUriList.size() != 0)
        {
            addComplaintsView.showProgressBar();

            createUrlsForImagesAndStoreData(dbRef, mAuth, mStorageReference,
                    title, description, field,
                    imageUriList);
        }
        else
        {
            addComplaintsView.showMessage("Sorry! select image and fill the fields");
        }

    }



    // method to create download urls for images and store data to firebase
    private void createUrlsForImagesAndStoreData(final DatabaseReference dbRef, final FirebaseAuth mAuth,
                                                 StorageReference mStorageReference,
                                     final String title , final String description ,
                                     final String field , final List<Uri> imageUriList) {


        for (int uploads = 0; uploads < imageUriList.size(); uploads++) {

            Uri Image = imageUriList.get(uploads);

            // storage reference to add images
            final StorageReference imagename = mStorageReference.child("ComplaintsImages").child("image/" + Image.getLastPathSegment());

            imagename.putFile(imageUriList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // to get download url
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            counter = counter + 1;
                            String url = String.valueOf(uri);
                            imageUrls.add(url);

                            if(counter == imageUriList.size()) {
                                // add urls and data to firebase
                                addDataToFirebase(dbRef , mAuth ,imageUrls, title, description, field);
                            }

                        }
                    });
                }
            });
        }


    }

    // method to add data to firebase database
    private void addDataToFirebase(DatabaseReference mDatabaseReference, FirebaseAuth mAuth,
            List<String> urls, String titleOfComp , String desc , String fieldOfComplaint)
    {
        // if arraylist conatain all urls then upload the data to database
        DatabaseReference dbAddCompRef = mDatabaseReference.push();


        String date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Map dataOfComplaint = new HashMap<>();
        dataOfComplaint.put("imageUrl", urls);
        dataOfComplaint.put("date", date);
        dataOfComplaint.put("title", titleOfComp);
        dataOfComplaint.put("description", desc);
        dataOfComplaint.put("status","Pending");
        dataOfComplaint.put("field", fieldOfComplaint);
        dataOfComplaint.put("pushKey", dbAddCompRef.getRef().getKey());
        dataOfComplaint.put("uid", mAuth.getCurrentUser().getUid());

        dbAddCompRef.setValue(dataOfComplaint)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            addComplaintsView.hideProgressBar();
                            addComplaintsView.clearAllFields();
                            addComplaintsView.showSuccessDialog();

                        } else {

                            addComplaintsView.showMessage("Error in uploading");
                            addComplaintsView.hideProgressBar();
                        }
                    }
                });
    }


}