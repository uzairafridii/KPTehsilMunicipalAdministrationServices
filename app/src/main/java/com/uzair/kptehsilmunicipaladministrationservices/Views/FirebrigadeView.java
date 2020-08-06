package com.uzair.kptehsilmunicipaladministrationservices.Views;

public interface FirebrigadeView
{
    void onErrorMessage(String message, String type);

    boolean checkPhonePermission();

    boolean checkLocationPermission();

    void requestPermission();

    void requestLocationPermission();

    boolean isLocationEnabled();

    void onSetPhoneNumberAndDriverName(String name, String phoneNumber);

    void infraHeadUid(String uid);

    void showProgressDialog();

    void hideProgressDialog();
}
