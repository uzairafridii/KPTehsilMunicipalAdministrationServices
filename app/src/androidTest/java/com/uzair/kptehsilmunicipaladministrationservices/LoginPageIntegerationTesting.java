package com.uzair.kptehsilmunicipaladministrationservices;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.uzair.kptehsilmunicipaladministrationservices.Models.LoginPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.LoginPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.LoginView;

import org.hamcrest.JMock1Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class LoginPageIntegerationTesting
{

    private LoginPresenter presenter;
    private LoginView loginView;
    private FirebaseAuth auth;

    @Before
    public void setUp() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        presenter = new LoginPresenterImplementer(loginView, appContext);
    }

    @Test
    public  void shouldFail() throws  Exception {

        presenter.login(auth , "","");

        assertEquals("Error","All fields are required");

    }


    @Test
    public void shouldPass() throws Exception
    {
        presenter.login(auth , "uzair@gmail.com","124224");

        assertEquals("Successfully login", "Successfully login");
    }




}
