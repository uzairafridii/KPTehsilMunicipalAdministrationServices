package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import com.google.firebase.auth.FirebaseAuth;

public interface LoginPresenter
{

    void login(FirebaseAuth mAuth , String userEmail ,
               String userPassword);


}
