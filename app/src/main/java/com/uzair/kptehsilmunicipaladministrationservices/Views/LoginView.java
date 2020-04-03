package com.uzair.kptehsilmunicipaladministrationservices.Views;

public interface LoginView
{

    void showProgressDialog();
    void hideProgressDialog();
    void showMessage(String message);

    void showPasswordErrorDialog(String passwordError);
    void showEmailDialog(String error);

    void moveToMainPage();
    void showSavePassordDialog();

    void clearAllFields();

}
