package com.uzair.kptehsilmunicipaladministrationservices.UserProfile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.uzair.kptehsilmunicipaladministrationservices.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private TextInputLayout userProfileName , userProfileEmail , userProfileCnic , userProfilePhone;
    private CircleImageView profileImage;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
         initView();

    }

    private void initView()
    {
        // text views
        userProfileName = findViewById(R.id.profileNameTextInputLayout);
        userProfileEmail = findViewById(R.id.profileEmailTextInputLayout);
        userProfilePhone = findViewById(R.id.profilePhoneTextInputLayout);
        userProfileCnic = findViewById(R.id.profileCnicTextInputLayout);

        // circle image view
        profileImage  = findViewById(R.id.profile_image);


        //  app tool bar
        mToolbar = findViewById(R.id.profile_tool_bar);
        mToolbar.inflateMenu(R.menu.user_profile_menu);
        mToolbar.setTitle("Your Profile");


    }
}
