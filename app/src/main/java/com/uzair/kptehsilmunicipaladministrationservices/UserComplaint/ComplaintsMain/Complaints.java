package com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.ComplaintsMain;

import android.content.Intent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import com.google.firebase.database.Query;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.ModelOfComplaintRecycler.ComplaintModel;

import java.util.ArrayList;
import java.util.List;

public class Complaints extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView complaintRecyclerList;
    private LinearLayoutManager layoutManager;

    //firebase
    private DatabaseReference mComplaintRef;
    private FirebaseAuth mUserAuth;
    private FirebaseRecyclerAdapter<ComplaintModel, MyComplaintHolder> adapter;
    private FirebaseRecyclerOptions<ComplaintModel> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        initViews();
        recyclerAdapter();

    }

    private void initViews() {

        //recycler view
        complaintRecyclerList = (RecyclerView) findViewById(R.id.complaints_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        complaintRecyclerList.setLayoutManager(layoutManager);
        // app tool bar
        mToolbar = findViewById(R.id.complaint_tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.backicon);
        setTitle("Your Complaints");

        // tool bar back arrow enabled
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // firebase instance
        mUserAuth = FirebaseAuth.getInstance();
        mComplaintRef = FirebaseDatabase.getInstance().getReference()
                .child("Complaints").child(mUserAuth.getCurrentUser().getUid());


    }

    // click listener on fab
    public void addNewComplaint(View view) {
        startActivity(new Intent(Complaints.this, AddComplaints.class));
    }

    // recycler adapter
    private void recyclerAdapter() {
        //firebase option take data from firebase and set in model
        options = new FirebaseRecyclerOptions.Builder<ComplaintModel>()
                .setQuery(mComplaintRef, ComplaintModel.class)
                .setLifecycleOwner(this)
                .build();

        // firebase adapter
        adapter = new FirebaseRecyclerAdapter<ComplaintModel, MyComplaintHolder>(options) {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onBindViewHolder(@NonNull MyComplaintHolder myComplaintHolder,
                                            int position, @NonNull ComplaintModel complaintModel) {
                // binding data in views
                myComplaintHolder.setComplaintTitle(complaintModel.getTitle());
                myComplaintHolder.setComplaintDescription(complaintModel.getDescription());
                myComplaintHolder.setComplaintTime(complaintModel.getDate());
                myComplaintHolder.setComplaintStatus(complaintModel.getStatus());
                myComplaintHolder.setComplaintImage(complaintModel.getImageUrl());

            }

            @NonNull
            @Override
            public MyComplaintHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // link the layout design
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.design_for_complaints_recycler_items, null);
                MyComplaintHolder holder = new MyComplaintHolder(view);

                return holder;
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                findViewById(R.id.progressData).setVisibility(View.INVISIBLE);

            }
        };

        //set adapter for recycler view


        complaintRecyclerList.setAdapter(adapter);
    }

    // view holder class
    public class MyComplaintHolder extends RecyclerView.ViewHolder {
        private TextView complaintTitle, complaintDescription, complaintTime, complaintStatus;
        private ImageView complaintImage;
        private View mView;
        private CardView cardView;

        public MyComplaintHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            cardView = mView.findViewById(R.id.your_complaints_card_view);
        }

        private void setComplaintTitle(String title) {
            complaintTitle = mView.findViewById(R.id.title_of_complaint_recycler_view);
            complaintTitle.setText(title);
        }

        private void setComplaintDescription(String description) {
            complaintDescription = mView.findViewById(R.id.desc_of_complaint_recycler_view);
            complaintDescription.setText(description);
        }

        private void setComplaintTime(String time) {
            complaintTime = mView.findViewById(R.id.complaint_date_time);
            complaintTime.setText(time);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        private void setComplaintStatus(String status) {
            complaintStatus = mView.findViewById(R.id.complaint_status);
            if (status.equals("Pending")) {
                complaintStatus.setTextColor(getColor(R.color.pending));
                complaintStatus.setText(status);
            } else {
                complaintStatus.setTextColor(getColor(R.color.blue_color));
                complaintStatus.setText(status);
            }

        }

        private void setComplaintImage(List<String> imageUrl) {

            complaintImage = mView.findViewById(R.id.complaint_recycler_image_view);

            for (int i = 0; i < imageUrl.size(); i++) {
                Glide.with(getApplicationContext())
                        .load(imageUrl.get(0))
                        .placeholder(R.drawable.giphyloading)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .into(complaintImage);

            }


        }

    }

}
