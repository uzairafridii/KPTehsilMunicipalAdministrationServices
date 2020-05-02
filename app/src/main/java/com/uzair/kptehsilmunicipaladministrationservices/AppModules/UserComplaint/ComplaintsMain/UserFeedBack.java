package com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uzair.kptehsilmunicipaladministrationservices.Models.UserFeedBackPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.UserFeedBackPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.UserFeedBackView;

import java.util.List;

public class UserFeedBack extends AppCompatActivity implements UserFeedBackView
{
    public static final int REQUEST_CODE = 110;
    private Toolbar mToolbar;
    private TextView titleOfComplaint, workerFirstName, workerSecondName, dateAndTime;
    private ImageView feedBackImage , commentImageView;
    private RatingBar workerRating;
    private Uri imageUri;
    private EditText addCommentEdit;
    private String complaintPushKey;
    private ProgressDialog progressDialog;
    private UserFeedBackPresenter presenter;
    private DatabaseReference dbRef , ratingRef;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed_back);

        initViews();
        presenter.getCompletedWorkDetails( dbRef, complaintPushKey);


    }

    private void initViews()
    {
        presenter = new UserFeedBackPresenterImplementer(this);
        complaintPushKey = getIntent().getStringExtra("pushKey");
        Log.d("complaintKey", "initViews: "+complaintPushKey);

        titleOfComplaint = findViewById(R.id.feedback_title);
        workerFirstName = findViewById(R.id.first_worker_name);
        workerSecondName = findViewById(R.id.second_worker_name);
        dateAndTime = findViewById(R.id.feedback_time);
        feedBackImage = findViewById(R.id.feedback_image);
        workerRating = findViewById(R.id.worker_rating_bar);
        addCommentEdit = findViewById(R.id.comment_edit_text);
        commentImageView = findViewById(R.id.commentImage);

        //  app tool bar
        mToolbar = findViewById(R.id.feedback_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("User Feedback");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);

        userAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Feedback Work");
        ratingRef  = FirebaseDatabase.getInstance().getReference();


    }

    // add image button click
    public void addCommentImageButtonClick(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent , REQUEST_CODE);
    }

    // button submit rating click
    public void addRatingButtonClick(View view)
    {
        // add workers rating
        presenter.addFirstWorkerRating(ratingRef , String.valueOf(workerRating.getRating()), addCommentEdit.getText().toString(),
                workerFirstName.getText().toString() , userAuth.getCurrentUser().getUid(), imageUri , complaintPushKey);


        presenter.addSecondWorkerRating(ratingRef , String.valueOf(workerRating.getRating()), addCommentEdit.getText().toString(),
                workerSecondName.getText().toString() , userAuth.getCurrentUser().getUid(), imageUri , complaintPushKey);

    }

    // feedback view callbacks method
    @Override
    public void showProgressBar()
    {
         progressDialog.setMessage("Please wait");
         progressDialog.setCanceledOnTouchOutside(false);
         progressDialog.show();
    }

    @Override
    public void hideProgressBar() {
      progressDialog.dismiss();
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetDataInTextViews(String title, String firstWorker,
                                     String secondWorker, List<String>
                                                 imageUrl, String date)
    {
        titleOfComplaint.setText(title);
        workerFirstName.setText(firstWorker);
        Log.d("workerS", "onSetDataInTextViews: "+firstWorker +"\n"+secondWorker);
        workerSecondName.setText(secondWorker);
        dateAndTime.setText(date);
        Glide.with(this)
                .load(imageUrl.get(0))
                .placeholder(R.drawable.logo)
                .into(feedBackImage);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            imageUri = data.getData();
            commentImageView.setVisibility(View.VISIBLE);
            commentImageView.setImageURI(imageUri);
        }

    }
}
