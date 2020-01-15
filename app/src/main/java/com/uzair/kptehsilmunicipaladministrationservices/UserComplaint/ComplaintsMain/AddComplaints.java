package com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.ComplaintsMain;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.uzair.kptehsilmunicipaladministrationservices.R;

public class AddComplaints extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView titleOfActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaints);
        initViews();

    }
    private void initViews()
    {
        //  app tool bar
        mToolbar = findViewById(R.id.addComplain_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("Add Complaint");
        // tool bar back arrow enabled
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);







    }
}
