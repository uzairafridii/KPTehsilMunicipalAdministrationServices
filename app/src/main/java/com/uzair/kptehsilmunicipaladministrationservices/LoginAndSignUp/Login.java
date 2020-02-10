package com.uzair.kptehsilmunicipaladministrationservices.LoginAndSignUp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.uzair.kptehsilmunicipaladministrationservices.Main.MainActivity;
import com.uzair.kptehsilmunicipaladministrationservices.R;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    private Button createAccountBtn;
    private FloatingActionButton button;
    private TextInputLayout userEmail , userPassword;
    private ProgressDialog mProgess;

    // firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();


        // click on login button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = userEmail.getEditText().getText().toString().trim();
                String password  = userPassword.getEditText().getText().toString().trim();

                if(!email.isEmpty() && !password.isEmpty())
                {
                    mProgess.setMessage("Wait..");
                   /// mProgess.setCancelable(false);
                    mProgess.setCanceledOnTouchOutside(false);
                    mProgess.show();

                    loginToApplication(email , password);
                }
                else
                {
                    userEmail.setError("Required");
                    userPassword.setError("Required");
                }


            }
        });

        // click on create new account button
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveToRegisterPage();
            }
        });
    }

    private void initViews()
    {
        button = findViewById(R.id.signInBtn);
        createAccountBtn = findViewById(R.id.txtCreateNewAccount);
        userEmail  = findViewById(R.id.emailTextInputLayoutLogin);
        userPassword  = findViewById(R.id.passwordTextInputLayoutLogin);

        mProgess = new ProgressDialog(this);

        // firebase
        mAuth  = FirebaseAuth.getInstance();

    }

    // move to signUp page
    private void moveToRegisterPage()
    {
        Intent signUp = new Intent(Login.this , SignUp.class);
        signUp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signUp);
        finish();
    }

    // login with email
    private void loginToApplication(String email , String password)
    {
        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    mProgess.dismiss();

                    if(mAuth.getCurrentUser().isEmailVerified())
                    {
                        // move to home page
                       startActivity(new Intent(Login.this , MainActivity.class));
                       clearAllFields();
                    }
                    else
                    {
                        // show dialog please verify email
                        mProgess.dismiss();

                        showCheckEmailVerificationDiaglog("Please Check and Verify Your Email");
                        clearAllFields();
                    }
                }
                else
                {
                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    showCheckEmailVerificationDiaglog(task.getResult().toString());
                }

            }

        });//  signIn om Completed body end
    }

    // show alert dialog
    private void showCheckEmailVerificationDiaglog(String message)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Verify Email");
        alert.setMessage(message);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                  dialogInterface.dismiss();
            }
        });
        alert.show();

    }

    // clear all input fields
    private void clearAllFields()
    {
        userEmail.getEditText().setText("");
        userPassword.getEditText().setText("");
    }

}
