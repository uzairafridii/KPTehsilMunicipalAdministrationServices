package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import android.net.Uri;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public interface NocPresenter
{

    // send and retrive data from firebase
    void sendNocDataToFirebase(DatabaseReference reference , FirebaseAuth firebaseAuth ,
                               StorageReference storageReference , Uri imageUri, String title);

    void readYourNoc(DatabaseReference reference , FirebaseAuth firebaseAuth);

    // select and preview image gallery methods
    void chooseGalleryClick();
    void previewImage(Uri uri);

}
