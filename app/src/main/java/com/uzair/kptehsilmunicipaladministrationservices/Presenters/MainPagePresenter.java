package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public interface MainPagePresenter
{
    void closingDialog();

    void setHomeRecyclerAdapter();

    boolean isNetworkAvailable();

    void showNoNetworkDialog();

    void updateToken(FirebaseAuth mAuth);

    void getUserName(DatabaseReference dbRef, FirebaseAuth mAuth);

}
