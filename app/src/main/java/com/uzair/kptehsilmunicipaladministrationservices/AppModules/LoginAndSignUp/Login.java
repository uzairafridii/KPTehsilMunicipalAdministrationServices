package com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
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

public class Login extends AppCompatActivity implements LoginView {

    public final static String SAVE_PASSWORD = "user_name_password";
    private SharedPreferences prefs;
    private String saveEmail, savedPassword , email , password;
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
                loginPresenterImplementer.login(mAuth, email, password, saveEmail, savedPassword);

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
        loginPresenterImplementer = new LoginPresenterImplementer(this);

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
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPasswordErrorDialog(String passwordError) {
        passwordErrorDialog(passwordError);
    }

    @Override
    public void showEmailDialog(String error) {
        showCheckEmailVerificationDiaglog(error);
    }

    @Override
    public void moveToMainPage() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(new Intent(intent));
        Login.this.finish();
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


    //  alert dialogs
    private void showCheckEmailVerificationDiaglog(String message) {
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

    private void passwordErrorDialog(String message) {
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
