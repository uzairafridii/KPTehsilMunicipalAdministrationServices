package com.uzair.kptehsilmunicipaladministrationservices.AppModules.BottomSheets;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates.BirthCertificate;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates.DeathCertificate;
import com.uzair.kptehsilmunicipaladministrationservices.Models.CertificateTypePresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.CertificateTypesView;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class CertificateBottomSheetDialog extends BottomSheetDialogFragment implements CertificateTypesView {

    private View myView;
    private RadioButton  deathCertificate , birthCertificate;
    private Button next;
    private CertificateTypePresenterImplementer typePresenterImplementer;

    public CertificateBottomSheetDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_certificate_bottom_sheet_dialog, container, false);

        initViews();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                typePresenterImplementer.selectType(deathCertificate , birthCertificate);

            }
        });

        return myView;
    }


    private void initViews()
    {
        deathCertificate = myView.findViewById(R.id.deathRadioBtn);
        birthCertificate = myView.findViewById(R.id.birthRadioBtn);

        next = myView.findViewById(R.id.nextBtn);

        typePresenterImplementer = new CertificateTypePresenterImplementer(this , getContext());

    }


    @Override
    public void showErrorMessage(String message) {

        Toasty.warning(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
