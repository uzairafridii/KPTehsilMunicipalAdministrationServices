package com.uzair.kptehsilmunicipaladministrationservices;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.uzair.kptehsilmunicipaladministrationservices.Models.LoginPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.Views.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SigninPresenterTest
{

    @Mock
    LoginView loginInView;
    @Mock
    LoginPresenterImplementer presenterImplementer;
    FirebaseAuth firebaseAuth;
    Context context;

    @Before
    public void setUp() throws Exception{
        presenterImplementer = new LoginPresenterImplementer(loginInView, context);
    }


    @Test
    public void shouldFailedToSignInIfPassIncompleteData() throws Exception {

        presenterImplementer.login(firebaseAuth ,"","");

        verify(loginInView).showMessage("All fields are required", "info");

    }

    @Test
    public void shouldLoginIfPassAllData() throws Exception {

        presenterImplementer.login(firebaseAuth, "uzair@gmail.com","123456");

        verify(loginInView).showProgressDialog();

    }

}
