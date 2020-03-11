package com.uzair.kptehsilmunicipaladministrationservices.TaxesDetails;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.uzair.kptehsilmunicipaladministrationservices.R;

public class Taxes extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxes);
        initViews();

    }
    private void initViews()
    {
        //  app tool bar
        mToolbar = findViewById(R.id.taxes_tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.backicon);
        setTitle("Taxes Detail");

        // tool bar back arrow enabled
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
}
