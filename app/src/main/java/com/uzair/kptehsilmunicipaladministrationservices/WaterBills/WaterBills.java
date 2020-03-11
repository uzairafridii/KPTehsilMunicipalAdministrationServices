package com.uzair.kptehsilmunicipaladministrationservices.WaterBills;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.uzair.kptehsilmunicipaladministrationservices.R;

public class WaterBills extends AppCompatActivity {

    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_bills);
        initViews();

    }

    private void initViews()
    {

        //  app tool bar
        mToolbar = findViewById(R.id.water_bills_tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.backicon);
        setTitle("Water Bills");

        // tool bar back arrow enabled
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
}
