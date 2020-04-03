package com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.uzair.kptehsilmunicipaladministrationservices.R;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity
{
    private TextInputLayout nameInput , emailInput , phoneInput , cnicInput , passwordInput , confirmPasswordInput;
    private Button alreadyHaveAnAccount;
    private FloatingActionButton signUpBtn;
    private String userName , userEmail , userCnic , userPhone , userPassword , userConfirmPassword;

    private ProgressDialog mProgressDialog;

    // firebase auth
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();


        // click on signup button
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUsers();
            }
        });


        // click on already account button
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 moveToLoginPage();
            }
        });

    }



    // inflate views
    private void initViews()
    {
        // input fields
        confirmPasswordInput = findViewById(R.id.userConfirmPasswordTextInputLayout);
        passwordInput        = findViewById(R.id.userPasswordTextInputLayout);
        nameInput            = findViewById(R.id.userNameTextInputLayout);
        emailInput           = findViewById(R.id.emailTextInputLayout);
        phoneInput           = findViewById(R.id.userPhoneTextInputLayout);
        cnicInput            = findViewById(R.id.userCnicTextInputLayout);

        mProgressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);

        // buttons
        alreadyHaveAnAccount = findViewById(R.id.txtAlreadyHaveAccount);
        signUpBtn = findViewById(R.id.userSignUpBtn);

        // firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("TMA Lachi");


    }

    // move to login page
    private void moveToLoginPage()
    {
        Intent signUp = new Intent(SignUp.this , Login.class);
        signUp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signUp);
        finish();
    }

    // user registration
    private void registerUsers()
    {
        userName = nameInput.getEditText().getText().toString().trim();
        userEmail = emailInput.getEditText().getText().toString().trim();
        userCnic = cnicInput.getEditText().getText().toString().trim();
        userPhone = phoneInput.getEditText().getText().toString().trim();
        userPassword = passwordInput.getEditText().getText().toString().trim();
        userConfirmPassword = confirmPasswordInput.getEditText().getText().toString().trim();

        try {

            if (!userName.isEmpty() && !userEmail.isEmpty()
                    && !userCnic.isEmpty() && !userPhone.isEmpty()
                    && !userPassword.isEmpty() && !userConfirmPassword.isEmpty()) {
                if (!userConfirmPassword.equals(userPassword)) {
                    confirmPasswordInput.setError("Password must be same");
                } else { // inner else body start

                    // progress dialog properties
                    mProgressDialog.setMessage("Registering...");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();


                    mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) { // susscess if
                                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //store use data in database
                                                    storeUserDataInDataBase(userName, userEmail, userPhone, userCnic, userPassword, userConfirmPassword);
                                                    clearAllFields();
                                                    mProgressDialog.dismiss();
                                                }
                                            }
                                        }); // inner on complete body end
                                    }// if success  body end
                                    else { // esle success
                                        mProgressDialog.dismiss();
                                        errorDialog(task.getException().getMessage());
                                    }
                                }
                            });//outer on complete body end

                } // inner else body end
            }// outer if body end
            else {
                nameInput.setError("Required");
                emailInput.setError("Required");
                cnicInput.setError("Required");
                phoneInput.setError("Required");
                passwordInput.setError("Required");
                confirmPasswordInput.setError("Required");
            }

    } catch (Exception e)
    {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    }

    // show alert dialog
    private void showCheckEmailVerificationDiaglog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Check Email");
        alert.setMessage("Please Verify Your Email ");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                startActivity(new Intent(SignUp.this , Login.class));
                SignUp.this.finish();

            }
        }).setNeutralButton("Verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                startActivity(intent);
            }
        });
        alert.show();

    }
    
    // show alert dialog for error
    private void errorDialog(String message)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Error");
        alert.setMessage(message);
        alert.setCancelable(false);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }


    // insert user data in firebase database
    private void storeUserDataInDataBase(final String name , final String email , final String cnic,
                                         final String phone , final String password , final String confPassword)
    {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {

                String deviceToken = instanceIdResult.getToken();


                Map<String , String > userRecord = new HashMap<>();
                userRecord.put("user_name" , name);
                userRecord.put("user_email" , email);
                userRecord.put("user_phone" , cnic);
                userRecord.put("user_cnic" , phone);
                userRecord.put("user_password" , password);
                userRecord.put("user_confirm_password" , confPassword);
                userRecord.put("device_token" , deviceToken);

                mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(userRecord).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            showCheckEmailVerificationDiaglog();

                        }
                        else
                        {
                            Toast.makeText(SignUp.this, "Not Register", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }


    // clear all input fields
    private void clearAllFields()
    {
        nameInput.getEditText().setText("");
        emailInput.getEditText().setText("");
        phoneInput.getEditText().setText("");
        cnicInput.getEditText().setText("");
        passwordInput.getEditText().setText("");
        confirmPasswordInput.getEditText().setText("");
    }
}
