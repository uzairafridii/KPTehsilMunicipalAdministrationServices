package com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uzair.kptehsilmunicipaladministrationservices.Models.ProfilePresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.ProfileView;

import java.util.HashMap;
import java.util.Map;

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

         // overflow menu item click
         mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem item) {
                 if(item.getItemId() == R.id.logout)
                 {
                     profilePresenterImplementer.signOut(mAuth);
                 }

                 else if(item.getItemId() == R.id.editProfile)
                 {
                     profilePresenterImplementer.onUpdateChildren();

                 }

                 return true;
             }
         });



    }

    private void initView()
    {

        profilePresenterImplementer = new ProfilePresenterImplementer(this , getApplicationContext());
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
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");


    }

    // firebase callbacks
    @Override
    public void getUserRecord(String userName, String userEmail, String userPhone, String userCnic) {
        user_name.setText(userName);
        user_email.setText(userEmail);
        user_phone.setText(userPhone);
        user_cnic.setText(userCnic);
    }

    @Override
    public void showEditDialog() {

        // inflate the custom edit layout
        final View myView = LayoutInflater.from(this).inflate(R.layout.profile_edit_layout, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(Profile.this);
        alert.setView(myView);

        final AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        final EditText name = myView.findViewById(R.id.changeName);
        final EditText phone = myView.findViewById(R.id.changePhoneNo);

        // button save changes click
        myView.findViewById(R.id.saveChangesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = name.getText().toString();
                String userPhone = phone.getText().toString();

                if(!userName.isEmpty() && !userPhone.isEmpty())
                {
                    // store updated user name and phone number
                    final Map<String , Object> updateRecord = new HashMap<>();
                    updateRecord.put("user_name",userName);
                    updateRecord.put("user_phone", userPhone);

                    mDatabaseRef.child(mAuth.getCurrentUser().getUid()).updateChildren(updateRecord)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Profile.this, "SuccessFully updated", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(Profile.this,"updation fail ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(Profile.this, "Please fill the fields", Toast.LENGTH_SHORT).show();
                }



            }
        });



    }

    @Override
    public void showProgressDialog() {

        progressDialog.setMessage("Loading your profile..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void onLogout() {
        this.finish();
    }


    // error callbacks
    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }






}
