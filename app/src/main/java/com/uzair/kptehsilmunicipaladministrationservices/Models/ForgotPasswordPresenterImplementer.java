package com.uzair.kptehsilmunicipaladministrationservices.Models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.ForgotPasswordPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.ForgotPasswordView;

public class ForgotPasswordPresenterImplementer implements ForgotPasswordPresenter
{
    private ForgotPasswordView forgotPasswordView;

    public ForgotPasswordPresenterImplementer(ForgotPasswordView forgotPasswordView) {
        this.forgotPasswordView = forgotPasswordView;
    }

    @Override
    public void onSendEmail(FirebaseAuth mAuth , String email) {

        if(mAuth != null && !email.isEmpty()) {

            forgotPasswordView.showProgressBar();

            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                forgotPasswordView.showMessage("Check Your Email");
                                forgotPasswordView.hideProgressBar();

                            } else {
                                forgotPasswordView.showMessage("Something went wrong");
                                forgotPasswordView.hideProgressBar();
                            }

                        }
                    });

        }
        else
        {
            forgotPasswordView.showMessage("Please enter your email");
        }

    }
}
