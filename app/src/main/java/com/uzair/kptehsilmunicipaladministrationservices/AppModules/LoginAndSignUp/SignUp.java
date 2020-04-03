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
import com.uzair.kptehsilmunicipaladministrationservices.Models.SignUpPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.SignUpView;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements SignUpView
{
    private TextInputLayout nameInput , emailInput , phoneInput , cnicInput , passwordInput , confirmPasswordInput;
    private Button alreadyHaveAnAccount;
    private FloatingActionButton signUpBtn;
    private String userName , userEmail , userCnic , userPhone , userPassword , userConfirmPassword;
    private ProgressDialog mProgressDialog;
    private SignUpPresenterImplementer signUpPresenterImplementer;

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

                userName = nameInput.getEditText().getText().toString().trim();
                userEmail = emailInput.getEditText().getText().toString().trim();
                userCnic = cnicInput.getEditText().getText().toString().trim();
                userPhone = phoneInput.getEditText().getText().toString().trim();
                userPassword = passwordInput.getEditText().getText().toString().trim();
                userConfirmPassword = confirmPasswordInput.getEditText().getText().toString().trim();

                // call register to app method of implementer
                signUpPresenterImplementer.registerToApp(mDatabase , mAuth,
                         userName , userEmail ,userCnic , userPassword , userConfirmPassword , userPhone);


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
        signUpPresenterImplementer = new SignUpPresenterImplementer(this);
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
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

    // move to login page
    private void moveToLoginPage()
    {
        Intent signUp = new Intent(SignUp.this , Login.class);
        signUp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signUp);
        finish();
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
    private void messageDialog(String message)
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


    @Override
    public void showProgressBar() {
        mProgressDialog.setMessage("Registering...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void hideProgressBar() {

        mProgressDialog.dismiss();
    }

    @Override
    public void clearAllFields() {
        nameInput.getEditText().setText("");
        emailInput.getEditText().setText("");
        phoneInput.getEditText().setText("");
        cnicInput.getEditText().setText("");
        passwordInput.getEditText().setText("");
        confirmPasswordInput.getEditText().setText("");
    }


    @Override
    public void showMessage(String message)
    {
       messageDialog(message);
    }

    @Override
    public void showEmailVerificationDialog(String verifyMessage)
    {
         showCheckEmailVerificationDiaglog();
    }
}
