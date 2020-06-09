package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

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
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.BottomSheets.StorePasswordBottomSheet;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp.Login;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.MainActivity;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.LoginPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.LoginView;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenterImplementer implements LoginPresenter {

    private LoginView loginView;

    public LoginPresenterImplementer(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(FirebaseAuth mAuth, String email, String password, String saveEmail, String savePassword) {

        try {
            if (!email.isEmpty() && !password.isEmpty()) {

                loginView.showProgressDialog();

                loginToApplication(mAuth, email, password, saveEmail, savePassword);

            } else {
                loginView.showMessage("All fields are required");
            }
        } catch (Exception e) {
        }

    }


    // login with email
    private void loginToApplication(final FirebaseAuth mAuth, final String email, final String password,
                                    final String savedEmail, final String savedPassword) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                try {
                    if (task.isSuccessful()) {

                        final FirebaseUser currentUser = mAuth.getCurrentUser();

                        // check email is verified or not
                        if (currentUser.isEmailVerified()) {

                            if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {

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
                            }
                            else {

                                loginView.hideProgressDialog();
                                loginView.showSavePassordDialog();
                                loginView.clearAllFields();

                            }// end save password if else


                        } else {
                            // show dialog please verify email
                            loginView.hideProgressDialog();
                            ;
                            loginView.showEmailDialog("Please Verify Your Email");
                            loginView.clearAllFields();

                        }// end verified email if else


                    } else {
                        loginView.hideProgressDialog();
                        loginView.showPasswordErrorDialog(task.getException().getMessage());

                    } // end check success if else


                } catch (Exception e) {

                    loginView.showMessage(e.getMessage());
                    loginView.hideProgressDialog();

                }// end try catch


            }

        });//  signIn om Completed body end
    }


}
