package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.content.Context;
import android.content.Intent;
import android.widget.RadioButton;

import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates.BirthCertificate;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates.DeathCertificate;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.CertificateTypesPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.CertificateTypesView;

public class CertificateTypePresenterImplementer implements CertificateTypesPresenter
{
     private CertificateTypesView view;
     private Context context;

    public CertificateTypePresenterImplementer(CertificateTypesView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void selectType(RadioButton death, RadioButton birth) {

        if(death.isChecked())
        {
            context.startActivity(new Intent(context , DeathCertificate.class));
        }
        else if(birth.isChecked())
        {
            context.startActivity(new Intent(context , BirthCertificate.class));
        }
        else
        {
            view.showErrorMessage("Please select certificate type");
        }
    }
}
