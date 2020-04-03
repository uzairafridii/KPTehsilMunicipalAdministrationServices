package com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.uzair.kptehsilmunicipaladministrationservices.Models.CertificateMainPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.CertificateMainView;

public class CertificatesMain extends AppCompatActivity implements CertificateMainView {

    private Toolbar mToolbar;
    private RecyclerView certificateRecyclerView;
    private LinearLayoutManager layoutManager;
    private CertificateMainPresenterImplementer mainPresenterImplementer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificates_main);

        initViews();

    }

    private void initViews()
    {

        //  app tool bar
        mToolbar = findViewById(R.id.certificateMainToolbar);
        setSupportActionBar(mToolbar);
        setTitle("Your Certificates");

        // tool bar back arrow enabled
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);


        certificateRecyclerView = findViewById(R.id.certificateRv);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        certificateRecyclerView.setLayoutManager(layoutManager);


        mainPresenterImplementer = new CertificateMainPresenterImplementer(this,this);
    }

    // fab button click to select certificate type
    public void selectCertificateType(View view)
    {
       mainPresenterImplementer.showBottomSheet();
    }
}
