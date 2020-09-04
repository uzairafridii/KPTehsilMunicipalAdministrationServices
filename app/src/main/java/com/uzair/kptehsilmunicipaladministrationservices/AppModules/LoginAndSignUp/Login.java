package com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.BottomSheets.StorePasswordBottomSheet;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.MainActivity;
import com.uzair.kptehsilmunicipaladministrationservices.Models.LoginPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.LoginView;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity implements LoginView {

    public final static String SAVE_PASSWORD = "user_name_password";
    private SharedPreferences prefs;
    private String email , password;
    private Button createAccountBtn;
    private FloatingActionButton button;
    private TextInputLayout userEmail, userPassword;
    private ProgressDialog mProgess;

    // firebase
    private FirebaseAuth mAuth;

    private LoginPresenterImplementer loginPresenterImplementer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        // click on login button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 email = userEmail.getEditText().getText().toString().trim();
                 password = userPassword.getEditText().getText().toString().trim();

                 // call presenterimplemneter login method
                loginPresenterImplementer.login(mAuth, email, password);

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

    private void initViews() {
        loginPresenterImplementer = new LoginPresenterImplementer(this, this);

        button = findViewById(R.id.signInBtn);
        createAccountBtn = findViewById(R.id.txtCreateNewAccount);
        userEmail = findViewById(R.id.emailTextInputLayoutLogin);
        userPassword = findViewById(R.id.passwordTextInputLayoutLogin);

        mProgess = new ProgressDialog(this, R.style.MyAlertDialogStyle);

        // firebase
        mAuth = FirebaseAuth.getInstance();

    }


    // move to signUp page
    private void moveToRegisterPage() {
        Intent signUp = new Intent(Login.this, SignUp.class);
        signUp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signUp);
        finish();
    }


    // callback methods
    @Override
    public void showProgressDialog() {

        mProgess.setMessage("Please wait");
        mProgess.setCanceledOnTouchOutside(false);
        mProgess.show();

    }

    @Override
    public void hideProgressDialog() {

        mProgess.dismiss();
    }

    @Override
    public void showMessage(String message, String type) {
        if(type.equals("error"))
        {
        Toasty.error(this, message, Toasty.LENGTH_SHORT).show();
       } else if(type.equals("info"))
        {
            Toasty.info(this, message, Toasty.LENGTH_LONG).show();
        }

    }



    @Override
    public void moveToHomePage() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
        startActivity(intent);
    }

    @Override
    public void showSavePassordDialog() {
        new StorePasswordBottomSheet(email, password).show(getSupportFragmentManager(), "Save Password Dialog");
    }

    @Override
    public void clearAllFields() {

        userEmail.getEditText().setText("");
        userPassword.getEditText().setText("");
    }


    //on start
    @Override
    protected void onStart() {
        super.onStart();

        prefs = getSharedPreferences(SAVE_PASSWORD, MODE_PRIVATE);
        email = prefs.getString("email", "");//"No name defined" is the default value.
        password = prefs.getString("password", "");

        userEmail.getEditText().setText(email);
        userPassword.getEditText().setText(password);


    }

    public void forogtPassword(View view)
    {
        startActivity(new Intent(Login.this , ForgotPassword.class));
    }
}
