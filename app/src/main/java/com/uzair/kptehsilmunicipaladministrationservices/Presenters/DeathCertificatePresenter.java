package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public interface DeathCertificatePresenter
{

    void onSubmitForm(DatabaseReference dbRef, StorageReference storeRef , FirebaseAuth mAuth,
                       String deceasedName, String deceasedCnic , String religion , String relation,
                      String fName , String fCnic , String mName , String mCnic , String husbandName ,
                      String husbandCnic ,String deceasedDateOfBirth , String gravyardName,
                      String placeOfBirth, String uc,String gender ,List<Uri> uriList);

    void onSetSpinnerAdapter();
    void chooseGalleryClick();
    void previewImage(List<Uri> uri);

}
