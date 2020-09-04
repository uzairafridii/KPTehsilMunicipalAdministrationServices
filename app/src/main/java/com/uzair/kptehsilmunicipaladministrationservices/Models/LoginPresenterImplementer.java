package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.LoginPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.LoginView;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenterImplementer implements LoginPresenter {

    private LoginView loginView;
    private Context context;

    public LoginPresenterImplementer(LoginView loginView, Context context) {
        this.context = context;
        this.loginView = loginView;
    }

    @Override
    public void login(FirebaseAuth mAuth, String email, String password) {

        try {
            if (!email.isEmpty() && !password.isEmpty())
            {
                if(password.length() >= 6 && email.endsWith("gmail.com")) {

                    loginView.showProgressDialog();

                    loginToApplication(mAuth, email, password);
                }


            } else {
                loginView.showMessage("All fields are required", "info");
            }
        } catch (Exception e) {
            Log.d("TAG", "login: "+e.getMessage());
        }

    }


    // login with email
    private void loginToApplication(final FirebaseAuth mAuth, final String email, final String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                try {
                    if (task.isSuccessful()) {

                        final FirebaseUser currentUser = mAuth.getCurrentUser();

                        // check email is verified or not
                        if (currentUser.isEmailVerified()) {


                                // get the device token
                                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                                    @Override
                                    public void onSuccess(InstanceIdResult instanceIdResult) {

                                        // update the device token whe user logged into app
                                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference()
                                                .child("Users").child(currentUser.getUid());

                                        Map<String , Object> updateToken = new HashMap<>();
                                        updateToken.put("device_token",instanceIdResult.getToken());

                                        dbRef.updateChildren(updateToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if(task.isSuccessful())
                                                {
                                                    loginView.hideProgressDialog();
                                                    loginView.showSavePassordDialog();
                                                }
                                            }
                                        });

                                    }
                                });


                        } else {
                            // show dialog please verify email
                            loginView.hideProgressDialog();
                            showCheckEmailVerificationDiaglog("Please Verify Your Email");
                            loginView.clearAllFields();

                        }// end verified email if else


                    } else {
                        loginView.hideProgressDialog();
                        passwordErrorDialog(task.getException().getMessage());

                    } // end check success if else


                } catch (Exception e) {

                    loginView.showMessage(e.getMessage(), "error");
                    loginView.hideProgressDialog();

                }// end try catch


            }

        });//  signIn om Completed body end
    }


    // show error dialog
    private void passwordErrorDialog(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
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

    //  alert dialogs for email verification
    private void showCheckEmailVerificationDiaglog(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
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

                dialogInterface.dismiss();
                loginView.moveToHomePage();

            }
        });
        alert.show();

    }

}
