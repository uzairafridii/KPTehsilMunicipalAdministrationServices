package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.BuildingNoc.NocDataModel;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.NocPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.NocView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NocPresenterImplementer implements NocPresenter {
    private NocView view;
    private List<NocDataModel> nocList = new ArrayList<>();

    public NocPresenterImplementer(NocView view) {
        this.view = view;
    }


    @Override
    public void sendNocDataToFirebase(final DatabaseReference reference, final FirebaseAuth firebaseAuth,
                                      final StorageReference storageReference,
                                      Uri imageUri, final String title) {

        if (!title.isEmpty() && imageUri != null
                && reference != null && firebaseAuth != null
                && storageReference != null) {

            view.showProgressDialog();

            final StorageReference storageRef = storageReference.child("NocPhotos").child(imageUri.getLastPathSegment());

            storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                        @Override
                        public void onSuccess(Uri uri) {

                            String downloadUrl = String.valueOf(uri);
                            Log.d("uriImage", "onSuccess: " + uri);

                            DatabaseReference dbRef = reference.child("Noc").push();
                            String pushKey = dbRef.getRef().getKey();
                            String date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                            Map<String, String> nocData = new HashMap<>();
                            nocData.put("nocTitle", title);
                            nocData.put("imageUrl", downloadUrl);
                            nocData.put("status", "Unregistered");
                            nocData.put("pushKey", pushKey);
                            nocData.put("date", date);
                            nocData.put("uid", firebaseAuth.getCurrentUser().getUid());


                            dbRef.setValue(nocData)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                view.hideProgressDialog();
                                                view.showSuccessAlertDialog();
                                                view.clearAllFields();

                                            }
                                            else
                                                {
                                                view.showErrorMessage("Database task is incomplete ");
                                                view.hideProgressDialog();

                                            }

                                        }
                                    });

                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            view.hideProgressDialog();
                            view.showErrorMessage("Storage task is fail");
                        }
                    });

                }


            });


        } else {
            view.showErrorMessage("Please fill all fields");
        }


    }


    @Override
    public void readYourNoc(DatabaseReference reference, FirebaseAuth firebaseAuth) {

        if (reference != null && firebaseAuth != null) {

            Query query = reference.child("Noc").orderByChild("uid").equalTo(firebaseAuth.getCurrentUser().getUid());

            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    NocDataModel nocDataModel = dataSnapshot.getValue(NocDataModel.class);
                    nocList.add(nocDataModel);
                    view.readDataList(nocList);

                    if (nocList.size() >= 1) {
                        view.showTextYourNoc();
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }


    @Override
    public void chooseGalleryClick() {
        if (!view.checkPermission()) {
            view.showErrorMessage("Please enable the permission");
            view.requestPermission();
        } else {
            view.chooseGallery();
        }


    }

    @Override
    public void previewImage(Uri uri) {
        view.displayImagePreview(uri);

    }
}
