package com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uzair.kptehsilmunicipaladministrationservices.Models.CertificateMainPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.CertificateMainPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.CertificateMainView;

import java.util.List;

public class CertificatesMain extends AppCompatActivity implements CertificateMainView {

    private Toolbar mToolbar;
    private RecyclerView certificateRecyclerView;
    private LinearLayoutManager layoutManager;
    private CertificateMainPresenter mainPresenterImplementer;
    private LinearLayout noItemTv;
    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificates_main);

        initViews();
        mainPresenterImplementer.retrieveAllCertificates(dbRef , mAuth);

    }

    private void initViews()
    {

        mToolbar = findViewById(R.id.certificateMainToolbar);
        setSupportActionBar(mToolbar);
        setTitle("Your Certificates");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);


        noItemTv = findViewById(R.id.progressData);

        certificateRecyclerView = findViewById(R.id.certificateRv);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        certificateRecyclerView.setLayoutManager(layoutManager);


        mainPresenterImplementer = new CertificateMainPresenterImplementer(this,this);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Certificates");
        mAuth = FirebaseAuth.getInstance();
    }

    // fab button click to select certificate type
    public void selectCertificateType(View view)
    {
       mainPresenterImplementer.showBottomSheet();
    }

    @Override
    public void setAdapter(List<CertificateModel> modelList)
    {
        AdapterForCertificateRv adapter = new AdapterForCertificateRv(this, modelList ,this);
        certificateRecyclerView.setAdapter(adapter);
    }

    @Override
    public void noItemFound() {
        noItemTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoItemFoundLayout() {
        noItemTv.setVisibility(View.GONE);
    }
}
