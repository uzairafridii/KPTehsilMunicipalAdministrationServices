package com.uzair.kptehsilmunicipaladministrationservices.LoginAndSignUp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uzair.kptehsilmunicipaladministrationservices.BottomSheets.StorePasswordBottomSheet;
import com.uzair.kptehsilmunicipaladministrationservices.Main.MainActivity;
import com.uzair.kptehsilmunicipaladministrationservices.R;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    public final static String SAVE_PASSWORD = "user_name_password";
    private SharedPreferences prefs;
    private String saveEmail , savedPassword;
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

                try
                {
                    if(!email.isEmpty() && !password.isEmpty())
                    {
                        mProgess.setMessage("Wait..");
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
                catch (Exception e)
                {
                    Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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

        mProgess = new ProgressDialog(this , R.style.MyAlertDialogStyle);

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
    private void loginToApplication(final String email , final String password)
    {
        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                try
                {
                    if(task.isSuccessful())
                    {
                        mProgess.dismiss();

                        FirebaseUser currentUser = mAuth.getCurrentUser();

                        if(currentUser.isEmailVerified())
                        {
                            //send data to bottom sheet in constructor

                            if(!saveEmail.isEmpty() && !savedPassword.isEmpty())
                            {
                                Intent intent = new Intent(Login.this , MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(new Intent(intent));
                                Login.this.finish();
                            }
                            else {
                                new StorePasswordBottomSheet(email, password).show(getSupportFragmentManager(), "Save Password Dialog");
                                clearAllFields();
                           }
                        }
                        else
                        {
                            // show dialog please verify email
                            mProgess.dismiss();

                            showCheckEmailVerificationDiaglog("Please Verify Your Email");
                            clearAllFields();
                        }
                    }
                    else
                    {
                        mProgess.dismiss();
                    //    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        passwordDialog(task.getException().getMessage());
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgess.dismiss();
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
        alert.setCancelable(false);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                  dialogInterface.dismiss();

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

    private void passwordDialog(String message)
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



    // clear all input fields
    private void clearAllFields()
    {
        userEmail.getEditText().setText("");
        userPassword.getEditText().setText("");
    }


    //on start
    @Override
    protected void onStart() {
        super.onStart();

         prefs = getSharedPreferences(SAVE_PASSWORD, MODE_PRIVATE);
         saveEmail = prefs.getString("email", "");//"No name defined" is the default value.
         savedPassword = prefs.getString("password", "");

        userEmail.getEditText().setText(saveEmail);
        userPassword.getEditText().setText(savedPassword);


    }
}
