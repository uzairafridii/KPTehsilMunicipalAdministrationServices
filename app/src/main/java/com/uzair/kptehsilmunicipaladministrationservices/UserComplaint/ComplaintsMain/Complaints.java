package com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.ComplaintsMain;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.uzair.kptehsilmunicipaladministrationservices.Main.HomeRecyclerAdapters.HomeRecyclerAdapter;
import com.uzair.kptehsilmunicipaladministrationservices.Main.ModelOfHomeRecycler.HomeModel;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.AdapterOfComplaintRecycler.ComplaintRecyclerAdapter;
import com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.ModelOfComplaintRecycler.ComplaintModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Complaints extends AppCompatActivity {

    private RecyclerView complaintList;
    private FloatingActionButton floatingActionButton;
    private ArrayList<ComplaintModel> modelArrayList;
    private ComplaintRecyclerAdapter complaintRecyclerAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        initViews();

        setHomeRecyclerItems();

        complaintRecyclerAdapter = new ComplaintRecyclerAdapter(this , modelArrayList);
        complaintList.setLayoutManager(layoutManager);
        complaintList.setAdapter(complaintRecyclerAdapter);

        complaintRecyclerAdapter.notifyDataSetChanged();



    }
    private void initViews()
    {
        floatingActionButton = findViewById(R.id.complain_fab);
        complaintList = (RecyclerView) findViewById(R.id.complaints_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        complaintList.setHasFixedSize(true);

        modelArrayList = new ArrayList<>();

        //  app tool bar
       // mToolbar = findViewById(R.id.home_tool_bar);
        //setSupportActionBar(mToolbar);

        // tool bar back arrow enabled
     //   ActionBar actionBar = getSupportActionBar();
       // actionBar.setDisplayHomeAsUpEnabled(true);


    }

    private void setHomeRecyclerItems()
    {
        String date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        modelArrayList.add(new ComplaintModel("First" ,getString(R.string.dummy) ,
                getString(R.string.pending),date , R.drawable.ic_launcher_background));
        modelArrayList.add(new ComplaintModel("Sirst" ,getString(R.string.dummy) ,
                getString(R.string.completed),date , R.drawable.ic_launcher_background));
    }


    // click listener on fab
    public void addNewComplaint(View view)
    {
        startActivity(new Intent(Complaints.this , AddComplaints.class));
    }
}
