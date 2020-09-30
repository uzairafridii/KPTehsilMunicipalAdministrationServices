package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp.Login;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp.SignUp;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.SignUpPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.SignUpView;

import java.util.HashMap;
import java.util.Map;

public class SignUpPresenterImplementer implements SignUpPresenter
{
    private SignUpView signUpView;
    private Context context;

    public SignUpPresenterImplementer(SignUpView signUpView, Context context) {
        this.signUpView = signUpView;
        this.context = context;
    }

    @Override
    public void registerToApp(final DatabaseReference dbRef, final FirebaseAuth mAuth,
                              final String userName, final String userEmail, final String userCnic,
                              final String userPassword, final String confirmPassword, final String userPhone)
    {


        try {

            if (!userName.isEmpty() && !userEmail.isEmpty()
                    && !userCnic.isEmpty() && !userPhone.isEmpty()
                    && !userPassword.isEmpty() && !confirmPassword.isEmpty()) {
                if (!confirmPassword.equals(userPassword)) {

                    signUpView.showMessage("Password must be same", "warning");

                }
                else { // inner else body start

                    signUpView.showProgressBar();

                    mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) { // susscess if

                                        // send link to current user email for verification
                                        mAuth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {

                                                    //store use data in database
                                                    storeUserDataInDataBase(dbRef , mAuth,
                                                            userName, userEmail, userPhone,
                                                            userCnic, userPassword, confirmPassword);


                                                    signUpView.clearAllFields();
                                                    signUpView.hideProgressBar();

                                                }
                                            }
                                        }); // inner on complete body end
                                    }// if success  body end
                                    else { // else success

                                        signUpView.hideProgressBar();
                                        signUpView.showMessage(task.getException().getMessage(), "error");
                                    }
                                }
                            });//outer on complete body end

                } // inner else body end
            }// outer if body end
            else {
                signUpView.showMessage("All fields are Required", "info");
            }

        } catch (Exception e)
        {
            signUpView.showMessage(e.getMessage() ,"error");
        }




    }





    // insert user data in firebase database
    private void storeUserDataInDataBase(final DatabaseReference dbRef, final FirebaseAuth mAuth,
                                         final String name , final String email , final String cnic,
                                         final String phone , final String password , final String confPassword)
    {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
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
                userRecord.put("uid" , mAuth.getCurrentUser().getUid());
                userRecord.put("device_token" , deviceToken);

                dbRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(userRecord).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                          emailVerificationDialog();
                        }
                        else
                        {
                            messageDialog("Not Register");
                        }

                    }
                });
            }
        });
    }


    // show alert dialog for error
    private void messageDialog(String message)
    {
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


    // email verification dialog
    private void emailVerificationDialog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Check Email");
        alert.setMessage("Please Verify Your Email ");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

              signUpView.moveToLoginScreen();
               dialogInterface.dismiss();
            }
        }).setNeutralButton("Verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                signUpView.openEmailApp();
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }




}
