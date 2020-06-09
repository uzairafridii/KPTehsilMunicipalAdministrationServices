package com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp.Login;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.HomeRecyclerAdapters.HomeRecyclerAdapter;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.ModelOfHomeRecycler.HomeModel;
import com.uzair.kptehsilmunicipaladministrationservices.Models.MainPagePresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.MainPagePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.MainPageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPageView
{
    private RecyclerView homeRecycler;
    private Toolbar mToolbar;
    private HomeRecyclerAdapter homeRecyclerAdapter;
    private LinearLayoutManager mGridLayoutManager;
    private MainPagePresenter mainPagePresenter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        mainPagePresenter.setHomeRecyclerAdapter();
    }

    private void initViews()
    {
        mainPagePresenter = new MainPagePresenterImplementer(this , this);
        homeRecycler = (RecyclerView) findViewById(R.id.homeRecyclerView);
        mGridLayoutManager = new GridLayoutManager(this , 2);
        homeRecycler.setLayoutManager(mGridLayoutManager);

        //  app tool bar
        mToolbar = findViewById(R.id.home_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("Home");

        // firebase
        mAuth = FirebaseAuth.getInstance();
    }
    // on start to check user is logged in  and verify email or not
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser == null || !currentUser.isEmailVerified()) {
                startActivity(new Intent(this, Login.class));
                this.finish();
            }

    }

    @Override
    public void onBackPressed()
    {
        mainPagePresenter.closingDialog();
    }

    @Override
    public void onSetHomeRecyclerAdapter(List<HomeModel> list)
    {
        homeRecyclerAdapter = new HomeRecyclerAdapter(this , list);
        homeRecycler.setAdapter(homeRecyclerAdapter);
    }

    @Override
    public void closeActivity()
    {
        MainActivity.this.finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mainPagePresenter.isNetworkAvailable())
        {
            mainPagePresenter.showNoNetworkDialog();
        }
    }
}
