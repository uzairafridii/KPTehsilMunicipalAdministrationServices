package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import com.google.firebase.database.DatabaseReference;

public interface FirebrigadePresenter
{
    void callDriver(String phoneNumber);

    void sendNotification(DatabaseReference reference);
}
