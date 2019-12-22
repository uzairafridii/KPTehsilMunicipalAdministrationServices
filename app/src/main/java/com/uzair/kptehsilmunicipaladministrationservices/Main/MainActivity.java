package com.uzair.kptehsilmunicipaladministrationservices.Main;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.uzair.kptehsilmunicipaladministrationservices.Main.HomeRecyclerAdapters.HomeRecyclerAdapter;
import com.uzair.kptehsilmunicipaladministrationservices.Main.ModelOfHomeRecycler.HomeModel;
import com.uzair.kptehsilmunicipaladministrationservices.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView homeRecycler;
    private Toolbar mToolbar;
    private HomeRecyclerAdapter homeRecyclerAdapter;
    private LinearLayoutManager mGridLayoutManager;
    private List<HomeModel> mHomeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setHomeRecyclerItems();

        homeRecyclerAdapter = new HomeRecyclerAdapter(this , mHomeModel);
        homeRecycler.setLayoutManager(mGridLayoutManager);
        homeRecycler.setAdapter(homeRecyclerAdapter);

        homeRecyclerAdapter.notifyDataSetChanged();


    }

    private void initViews()
    {
        homeRecycler = (RecyclerView) findViewById(R.id.homeRecyclerView);
        mGridLayoutManager = new GridLayoutManager(this , 2);

        mHomeModel = new ArrayList<>();

        //  app tool bar
        mToolbar = findViewById(R.id.home_tool_bar);
        setSupportActionBar(mToolbar);

        // tool bar back arrow enabled
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    private void setHomeRecyclerItems()
    {
        mHomeModel.add(new HomeModel("Complaints" , R.drawable.ic_launcher_background));
        mHomeModel.add(new HomeModel("Water Bills" , R.drawable.bill));
        mHomeModel.add(new HomeModel("Fire Brigade" , R.drawable.fire));
        mHomeModel.add(new HomeModel("Building Noc" , R.drawable.noc));
        mHomeModel.add(new HomeModel("Taxes" , R.drawable.taxes));
        mHomeModel.add(new HomeModel("Birth Certificate" , R.drawable.birth));
        mHomeModel.add(new HomeModel("Death Certificate" , R.drawable.death));
        mHomeModel.add(new HomeModel("Marriage Certificate" , R.drawable.certificate));
        mHomeModel.add(new HomeModel("Profile" , R.drawable.profile));

    }
}
