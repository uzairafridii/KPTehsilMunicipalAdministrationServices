package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public interface ProfilePresenter
{

    void getUserProfileData(DatabaseReference dbRef , FirebaseAuth userAuth);

    void onUpdateChildren();

    void signOut(FirebaseAuth userAuth);
}
