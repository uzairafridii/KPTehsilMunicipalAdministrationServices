package com.uzair.kptehsilmunicipaladministrationservices.Main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uzair.kptehsilmunicipaladministrationservices.FireFighting.FireFighting;
import com.uzair.kptehsilmunicipaladministrationservices.LoginAndSignUp.Login;
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
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setHomeRecyclerItems();

        homeRecycler.setAdapter(homeRecyclerAdapter);
        homeRecyclerAdapter.notifyDataSetChanged();


    }

    private void initViews()
    {
        homeRecycler = (RecyclerView) findViewById(R.id.homeRecyclerView);
        mGridLayoutManager = new GridLayoutManager(this , 2);
        homeRecycler.setLayoutManager(mGridLayoutManager);

        mHomeModel = new ArrayList<>();
        homeRecyclerAdapter = new HomeRecyclerAdapter(this , mHomeModel);
        //  app tool bar
        mToolbar = findViewById(R.id.home_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("Home");

        // firebase
        mAuth = FirebaseAuth.getInstance();



    }

    // set items on recycler list
    private void setHomeRecyclerItems()
    {
        mHomeModel.add(new HomeModel("Complaints" , R.drawable.ic_launcher_background));
        mHomeModel.add(new HomeModel("Water Bills" , R.drawable.ic_add_black_24dp));
        mHomeModel.add(new HomeModel("Fire Brigade" , R.drawable.ic_launcher_background));
        mHomeModel.add(new HomeModel("Building Noc" , R.drawable.ic_launcher_background));
        mHomeModel.add(new HomeModel("Taxes" , R.drawable.ic_launcher_background));
        mHomeModel.add(new HomeModel("Certificates" , R.drawable.ic_launcher_background));
        mHomeModel.add(new HomeModel("Profile" , R.drawable.ic_launcher_background));

    }

    // on start to check user is logged in or not
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)
        {
            startActivity(new Intent(this , Login.class));
            this.finish();
        }
    }
}
