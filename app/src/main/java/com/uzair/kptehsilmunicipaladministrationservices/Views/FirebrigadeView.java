package com.uzair.kptehsilmunicipaladministrationservices.Views;

public interface FirebrigadeView
{
    void onErrorMessage(String message);

    boolean checkPhonePermission();
    void requestPermission();
}
