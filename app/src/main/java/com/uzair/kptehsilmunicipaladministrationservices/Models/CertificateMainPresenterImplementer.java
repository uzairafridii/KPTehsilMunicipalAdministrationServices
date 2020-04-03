package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.uzair.kptehsilmunicipaladministrationservices.AppModules.BottomSheets.CertificateBottomSheetDialog;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.CertificateMainPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.CertificateMainView;

public class CertificateMainPresenterImplementer implements CertificateMainPresenter
{

    private Context context;
    private CertificateMainView mainView;

    public CertificateMainPresenterImplementer(Context context, CertificateMainView mainView) {
        this.context = context;
        this.mainView = mainView;
    }

    @Override
    public void showBottomSheet() {

        new CertificateBottomSheetDialog().show(((AppCompatActivity)context).getSupportFragmentManager(), "Types");
    }
}
