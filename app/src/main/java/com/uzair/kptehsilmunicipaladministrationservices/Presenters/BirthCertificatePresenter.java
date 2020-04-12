package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Map;

public interface BirthCertificatePresenter
{

    void onSubmitForm(DatabaseReference dbRef, StorageReference storeRef , FirebaseAuth mAuth,
                      String name , String cnic, String childName , String religion , String relation,
                      String fName , String fCnic , String mName , String mCnic , String gFatherName ,
                      String gFatherCnic , String distOfBirth , String dob , String vaccinated ,
                      String placeOfBirth , String mideWife , String disability , String address , String gender, List<Uri> uriList);

    void onSetSpinnerAdapter();
    void chooseGalleryClick();
    void previewImage(List<Uri> uri);
}
