package com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.AdapterOfComplaintRecycler.AdapterForComplaintsRV;
import com.uzair.kptehsilmunicipaladministrationservices.Models.ComplaintHomePresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ModelOfComplaintRecycler.ComplaintModel;
import com.uzair.kptehsilmunicipaladministrationservices.Views.ComplaintHomeView;

import java.util.List;

public class Complaints extends AppCompatActivity implements ComplaintHomeView
{

    private Toolbar mToolbar;
    private RecyclerView complaintRecyclerList;
    private LinearLayoutManager layoutManager;
    private AdapterForComplaintsRV adapterForComplaintsRV;
    private TextView noItemTv;
    private ComplaintHomePresenterImplementer presenterImplementer;
    //firebase
    private DatabaseReference mComplaintRef;
    private FirebaseAuth mUserAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        initViews();

        presenterImplementer.getAllComplaints(mComplaintRef , mUserAuth);

    }

    private void initViews() {

        presenterImplementer = new ComplaintHomePresenterImplementer(this);

        noItemTv = findViewById(R.id.progressData);
        //recycler view
        complaintRecyclerList = (RecyclerView) findViewById(R.id.complaints_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        complaintRecyclerList.setLayoutManager(layoutManager);

        // app tool bar
        mToolbar = findViewById(R.id.complaint_tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.backicon);
        setTitle("Your Complaints");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // firebase instance
        mUserAuth = FirebaseAuth.getInstance();
        mComplaintRef = FirebaseDatabase.getInstance().getReference()
                .child("Complaints");


    }

    // click listener on fab
    public void addNewComplaint(View view) {
        startActivity(new Intent(Complaints.this, AddComplaints.class));
    }

    @Override
    public void noItemTextShow()
    {
         noItemTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void noItemTextHide() {
        noItemTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setAdapter(List<ComplaintModel> list)
    {
         adapterForComplaintsRV = new AdapterForComplaintsRV(getApplicationContext() , list , this);
         complaintRecyclerList.setAdapter(adapterForComplaintsRV);
    }

    @Override
    public void onShowStatusDialog(String message , int vector)
    {
      successDailog(message , vector);
    }


    private void successDailog(String message , int vector)
    {
        View myView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_dialog , null);
        AlertDialog.Builder alert = new AlertDialog.Builder(Complaints.this);
        alert.setView(myView);

        final AlertDialog dialog = alert.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        TextView textView = myView.findViewById(R.id.message);
        ImageView imageView = myView.findViewById(R.id.imageView);

        imageView.setImageResource(vector);

        textView.setText(message);

        myView.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }



}
