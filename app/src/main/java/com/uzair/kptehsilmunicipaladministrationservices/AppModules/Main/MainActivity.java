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
        mHomeModel.add(new HomeModel("Complaints" , R.drawable.complain));
        mHomeModel.add(new HomeModel("Water Bills" , R.drawable.bill));
        mHomeModel.add(new HomeModel("Fire Brigade" , R.drawable.fire));
        mHomeModel.add(new HomeModel("Building Noc" , R.drawable.noc));
        mHomeModel.add(new HomeModel("Taxes" , R.drawable.ic_launcher_background));
        mHomeModel.add(new HomeModel("Certificates" , R.drawable.ic_launcher_background));
        mHomeModel.add(new HomeModel("Profile" , R.drawable.avatar));

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
    public void onBackPressed() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Do you want to close the app?");
        alert.setTitle("TMA App");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                MainActivity.this.finish();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        alert.show();

    }
}
