package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import com.google.firebase.auth.FirebaseAuth;

public interface ForgotPasswordPresenter
{

    void onSendEmail(FirebaseAuth mAuth , String email);


}
