package com.uzair.kptehsilmunicipaladministrationservices.BuildingNoc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.uzair.kptehsilmunicipaladministrationservices.R;

import java.util.ArrayList;

public class BuildingNocActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_noc);
        initViews();

    }


    private void initViews()
    {

        //  app tool bar
        mToolbar = findViewById(R.id.noc_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("Building Noc");

        // tool bar back arrow enabled
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

}
