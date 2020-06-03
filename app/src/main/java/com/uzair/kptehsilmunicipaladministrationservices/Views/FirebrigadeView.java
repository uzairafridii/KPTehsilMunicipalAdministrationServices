package com.uzair.kptehsilmunicipaladministrationservices.Views;

public interface FirebrigadeView
{
    void onErrorMessage(String message);

    boolean checkPhonePermission();

    void requestPermission();

    boolean isLocationEnabled();

    void onSetPhoneNumberAndDriverName(String name, String phoneNumber);

    void infraHeadUid(String uid);
}
