package com.uzair.kptehsilmunicipaladministrationservices.Views;

import android.net.Uri;

import com.uzair.kptehsilmunicipaladministrationservices.AppModules.BuildingNoc.NocDataModel;

import java.util.List;

public interface NocView
{
    // firebase callbaacks
    void showProgressDialog();
    void hideProgressDialog();
    void showSuccessAlertDialog();
    void readDataList(List<NocDataModel> listOfNoc);
    void showTextYourNoc();

    //pick image callbacks
    void chooseGallery();
    void displayImagePreview(Uri mFileUri);

    // storage permission callbacks
    boolean checkPermission();
    void requestPermission();

    // set views empty
    void clearAllFields();

    // error message callback
    void showErrorMessage(String error);
}
