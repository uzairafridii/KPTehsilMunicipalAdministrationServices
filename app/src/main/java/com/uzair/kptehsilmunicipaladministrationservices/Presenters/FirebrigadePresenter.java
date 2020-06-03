package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public interface FirebrigadePresenter
{
    void callDriver(String phoneNumber);

    void sendNotification(DatabaseReference reference, FirebaseAuth auth , String uid);

    void getDriverNameAndPhoneNumber(DatabaseReference dbRef);

    void getLastLocation();

    void getInfraHeadUid(DatabaseReference dbRef);
}
