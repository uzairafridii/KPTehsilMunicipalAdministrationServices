package com.uzair.kptehsilmunicipaladministrationservices.Views;

public interface AddComplaintsView
{
    void showProgressBar();
    void hideProgressBar();

    void showSuccessDialog();
    void showMessage(String message);
    void clearAllFields();

}
