package com.uzair.kptehsilmunicipaladministrationservices.Views;

import android.net.Uri;

import java.util.List;

public interface BirthCertificateView
{

    void onShowProgressDialog();
    void onDismissProgressDialog();
    void showMessage(String message);
    void setSpinnerAdapter();
    void chooseGallery();
    void displayImagePreview(List<Uri> mFileUri);
    void clearAllFileds();


}
