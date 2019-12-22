package com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.ComplaintsMain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

        if(status.equals("Completed")) {
            titleOfComplaint.setText(title);

            titleOfComplaint.setVisibility(View.VISIBLE);
            workerSecondName.setVisibility(View.VISIBLE);
            workerFirstName.setVisibility(View.VISIBLE);
            workerRating.setVisibility(View.VISIBLE);
            submitFeedBackBtn.setVisibility(View.VISIBLE);
            complaintImage.setVisibility(View.VISIBLE);
            cameraButton.setVisibility(View.VISIBLE);
            addCommentEdit.setVisibility(View.VISIBLE);
            dateAndTime.setVisibility(View.VISIBLE);
            rateUs.setVisibility(View.VISIBLE);

        }
        else {

            titleOfComplaint.setVisibility(View.GONE);
            workerSecondName.setVisibility(View.GONE);
            workerFirstName.setVisibility(View.GONE);
            workerRating.setVisibility(View.GONE);
            submitFeedBackBtn.setVisibility(View.GONE);
            complaintImage.setVisibility(View.GONE);
            cameraButton.setVisibility(View.GONE);
            addCommentEdit.setVisibility(View.GONE);
            dateAndTime.setVisibility(View.GONE);
            rateUs.setVisibility(View.GONE);
        }
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

    }
}
