package com.uzair.kptehsilmunicipaladministrationservices.Views;

public interface ProfileView
{

    void getUserRecord(String userName , String userEmail , String userPhone , String userCnic , String userProfile);

    void showErrorMessage(String message);

    void showProgressDialog();
    void hideProgressDialog();


}
