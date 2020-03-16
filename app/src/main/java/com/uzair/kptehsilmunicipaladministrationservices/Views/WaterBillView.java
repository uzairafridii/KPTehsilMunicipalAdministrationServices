package com.uzair.kptehsilmunicipaladministrationservices.Views;

public interface WaterBillView
{

    // firebase call backs
    void showProgressDialog();
    void hideProgressDialog();
    void getUserRecord(String name , String date , String location , String image);

    // storage permission callbacks
    boolean checkStoragePermission();
    void requestStoragePermission();

    // set views empty
    void clearAllFields();

    // error message callback
    void showErrorMessage(String error);

}
