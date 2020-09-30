package com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp.Login;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.HomeRecyclerAdapters.HomeRecyclerAdapter;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.ModelOfHomeRecycler.HomeModel;
import com.uzair.kptehsilmunicipaladministrationservices.Models.MainPagePresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.MainPagePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.MainPageView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPageView
{
    private RecyclerView homeRecycler;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private HomeRecyclerAdapter homeRecyclerAdapter;
    private LinearLayoutManager mGridLayoutManager;
    private static MainPagePresenter mainPagePresenter;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;

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
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);

//        setSupportActionBar(collapsingToolbarLayout);
//        setTitle("Home");

        // firebase
        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
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
            else
            {
                mainPagePresenter.getUserName(dbRef, mAuth);
                mainPagePresenter.updateToken(mAuth);

            }

    }


    // show exit dialog on exit card click
    public static void exitDialog()
    {
        mainPagePresenter.closingDialog();
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
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
    public void setUserName(String name) {
        collapsingToolbarLayout.setTitle(name+" Welcome Here!");
    }

    // check internet connection
    @Override
    protected void onResume() {
        super.onResume();
        if(!mainPagePresenter.isNetworkAvailable())
        {
            mainPagePresenter.showNoNetworkDialog();
        }
    }
}
