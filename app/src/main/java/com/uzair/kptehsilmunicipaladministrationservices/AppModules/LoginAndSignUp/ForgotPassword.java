package com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.uzair.kptehsilmunicipaladministrationservices.Models.ForgotPasswordPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.ForgotPasswordPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.ForgotPasswordView;

public class ForgotPassword extends AppCompatActivity implements ForgotPasswordView
{

    private EditText forgotEmail;
    private ProgressDialog mProgressDialog;
    private String email;
    private ForgotPasswordPresenter forgotPasswordPresenter;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();

    }

    private void initViews()
    {
        mAuth = FirebaseAuth.getInstance();
        forgotEmail = findViewById(R.id.emailForgotPassword);
        mProgressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);
        forgotPasswordPresenter = new ForgotPasswordPresenterImplementer(this);

    }


    public void sendForgotEmail(View view)
    {
        email = forgotEmail.getText().toString().trim();

        forgotPasswordPresenter.onSendEmail(mAuth , email);

    }


    @Override
    public void showMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar()
    {
       mProgressDialog.setMessage("Checking your email");
       mProgressDialog.setCanceledOnTouchOutside(false);
       mProgressDialog.setCancelable(false);
       mProgressDialog.show();
    }

    @Override
    public void hideProgressBar() {
      mProgressDialog.dismiss();
    }

}
