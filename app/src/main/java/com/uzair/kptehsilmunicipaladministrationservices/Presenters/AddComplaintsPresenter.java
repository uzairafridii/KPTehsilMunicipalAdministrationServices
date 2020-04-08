package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public interface AddComplaintsPresenter
{

    void storeComplaintDataToFirebase(DatabaseReference dbRef , FirebaseAuth mAuth, StorageReference mStorageReference,
                                       String title ,  String description , String field ,
                                       List<Uri> imageUriList , double lat , double lng);

}
