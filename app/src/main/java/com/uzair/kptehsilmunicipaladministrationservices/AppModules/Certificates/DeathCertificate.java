package com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates;

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

        mToolbar = findViewById(R.id.death_certificate_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("Death Certificate Form");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
}
