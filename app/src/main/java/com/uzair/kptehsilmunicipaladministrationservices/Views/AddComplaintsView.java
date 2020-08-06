package com.uzair.kptehsilmunicipaladministrationservices.Views;

public interface AddComplaintsView
{
    void showProgressBar();
    void hideProgressBar();

    void closeActivity();
    void showMessage(String message, String type);
    void clearAllFields();

}
