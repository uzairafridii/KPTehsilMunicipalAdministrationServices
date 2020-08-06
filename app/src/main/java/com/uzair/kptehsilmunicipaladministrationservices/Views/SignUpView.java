package com.uzair.kptehsilmunicipaladministrationservices.Views;

public interface SignUpView {

    void showProgressBar();

    void hideProgressBar();

    void clearAllFields();

    void showMessage(String message, String type);

    void openEmailApp();

    void moveToLoginScreen();
}
