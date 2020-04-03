package com.uzair.kptehsilmunicipaladministrationservices.Views;

public interface ProfileView
{

    // firebase callback
    void getUserRecord(String userName , String userEmail , String userPhone , String userCnic);
    void onLogout();
    void showEditDialog();

    // progress dialog callbacks
    void showProgressDialog();
    void hideProgressDialog();

    //error callback
    void showErrorMessage(String message);


}
