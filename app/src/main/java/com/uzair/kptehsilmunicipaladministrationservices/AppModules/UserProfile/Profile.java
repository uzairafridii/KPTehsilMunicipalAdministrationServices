package com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uzair.kptehsilmunicipaladministrationservices.Models.ProfilePresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.ProfileView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity implements ProfileView
{
    private TextView user_name , user_email , user_phone , user_cnic;
    private CircleImageView profileImage;
    private Toolbar mToolbar;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private ProfilePresenterImplementer profilePresenterImplementer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
         initView();

         profilePresenterImplementer.getUserProfileData(mDatabaseRef , mAuth);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                if(item.getItemId() == R.id.logout)
                {
                    mAuth.signOut();
                    finish();
                }

                return true;
            }
        });

    }

    private void initView()
    {

        profilePresenterImplementer = new ProfilePresenterImplementer(this);
        progressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);

        // text views
          user_name = findViewById(R.id.userNameInProfile);
          user_email = findViewById(R.id.emailValueTv);
          user_phone = findViewById(R.id.phoneNUmberValueTv);
          user_cnic = findViewById(R.id.cnicValueTv);

        // circle image view
        profileImage  = findViewById(R.id.profile_image);


        //  app tool bar
        mToolbar = findViewById(R.id.profile_tool_bar);
        mToolbar.inflateMenu(R.menu.user_profile_menu);
        mToolbar.setTitle("Your Profile");

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("TMA Lachi").child("Users");


    }


    @Override
    public void getUserRecord(String userName, String userEmail, String userPhone, String userCnic, String userProfile) {
        user_name.setText(userName);
        user_email.setText(userEmail);
        user_phone.setText(userPhone);
        user_cnic.setText(userCnic);
        Glide.with(this)
                .load(userProfile)
                .placeholder(R.drawable.avatar)
                .into(profileImage);

    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {

        progressDialog.setMessage("Loading your profile..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
          progressDialog.hide();
    }
}
