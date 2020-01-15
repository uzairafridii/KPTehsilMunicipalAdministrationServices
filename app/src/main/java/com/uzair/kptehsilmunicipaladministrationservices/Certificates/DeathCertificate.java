package com.uzair.kptehsilmunicipaladministrationservices.Certificates;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.uzair.kptehsilmunicipaladministrationservices.R;

public class DeathCertificate extends AppCompatActivity {

    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_certificate);
        initViews();

    }

    private void initViews()
    {

        //  app tool bar
        mToolbar = findViewById(R.id.death_certificate_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("Death Certificate Form");

        // tool bar back arrow enabled
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
}
