package com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.uzair.kptehsilmunicipaladministrationservices.R;

public class UserFeedBack extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView titleOfComplaint , workerFirstName, workerSecondName, dateAndTime , rateUs;
    private ImageView complaintImage;
    private RatingBar workerRating;
    private EditText addCommentEdit;
    private ImageButton cameraButton;
    private Button submitFeedBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed_back);

        initViews();

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String status = intent.getStringExtra("status");

    }

    private void initViews()
    {
        titleOfComplaint = findViewById(R.id.feedback_title);
        rateUs = findViewById(R.id.rating_txt);
        workerFirstName = findViewById(R.id.first_worker_name);
        workerSecondName = findViewById(R.id.second_worker_name);
        dateAndTime = findViewById(R.id.feedback_time);
        complaintImage  = findViewById(R.id.feedback_image);
        workerRating = findViewById(R.id.worker_rating_bar);
        addCommentEdit = findViewById(R.id.comment_edit_text);
        cameraButton  = findViewById(R.id.add_image_btn);
        submitFeedBackBtn = findViewById(R.id.submit_feedback_btn);



            //  app tool bar
            mToolbar = findViewById(R.id.feedback_tool_bar);
            setSupportActionBar(mToolbar);
            setTitle("User Feedback");

            // tool bar back arrow enabled
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);


    }
}
