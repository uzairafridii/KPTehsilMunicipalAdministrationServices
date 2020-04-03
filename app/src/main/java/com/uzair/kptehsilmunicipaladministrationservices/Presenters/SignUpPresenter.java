package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public interface SignUpPresenter
{
    void registerToApp(DatabaseReference dbRef , FirebaseAuth mAuth,
                       String userName , String userEmail, String userCnic,
                       String userPassword , String confirmPassword , String userPhone);

}
