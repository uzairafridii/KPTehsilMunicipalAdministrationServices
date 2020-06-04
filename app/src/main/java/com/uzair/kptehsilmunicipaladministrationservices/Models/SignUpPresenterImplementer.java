package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp.SignUp;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.SignUpPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.SignUpView;

import java.util.HashMap;
import java.util.Map;

public class SignUpPresenterImplementer implements SignUpPresenter
{
    private SignUpView signUpView;

    public SignUpPresenterImplementer(SignUpView signUpView) {
        this.signUpView = signUpView;
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

                    signUpView.showMessage("Password must be same");

                } else { // inner else body start

                    signUpView.showProgressBar();

                    mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) { // susscess if

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
                                    else { // esle success

                                        signUpView.hideProgressBar();
                                        signUpView.showMessage(task.getException().getMessage());
                                    }
                                }
                            });//outer on complete body end

                } // inner else body end
            }// outer if body end
            else {
                signUpView.showMessage("All fields are Required");
            }

        } catch (Exception e)
        {
            signUpView.showMessage(e.getMessage());
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

                          signUpView.showEmailVerificationDialog();
                        }
                        else
                        {
                            signUpView.showMessage("Not Register");
                        }

                    }
                });
            }
        });
    }





}
