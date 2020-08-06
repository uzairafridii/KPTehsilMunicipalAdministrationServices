package com.uzair.kptehsilmunicipaladministrationservices.Views;

public interface LoginView
{

    void showProgressDialog();
    void hideProgressDialog();
    void showMessage(String message, String type);

    void moveToHomePage();

    void showSavePassordDialog();

    void clearAllFields();

}
