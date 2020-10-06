package com.uzair.kptehsilmunicipaladministrationservices.UnitTesting;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.uzair.kptehsilmunicipaladministrationservices.Models.SignUpPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.SignUpPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.SignUpView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SignUpTesting {

    private Context context;
    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;
    @Mock
    private SignUpPresenterImplementer presenter;
    @Mock
    private SignUpView signUpView;

    @Before
    public void setup() throws Exception
    {
       presenter = new SignUpPresenterImplementer(signUpView, context);
    }

    @Test
    public void failedIfAllDataNotPassed() throws Exception
    {
        presenter.registerToApp(dbRef , mAuth,"","","","","","");

        verify(signUpView).showMessage("All fields are Required", "info");

    }

    @Test
    public void failedIfConfirmPasswordNotSame() throws Exception
    {
        presenter.registerToApp(dbRef, mAuth, "d","f","44","32","32","df");

        verify(signUpView).showMessage("Password must be same and 6 digits", "warning");
    }

    @Test
    public void shouldBePassedIfAllDataPassed() throws Exception
    {
        presenter.registerToApp(dbRef,mAuth, "sd","fs","df","Df","Df","sdf");
        verify(signUpView).showProgressBar();
    }

    @Test
    public void passwordAndGmailInValid() throws Exception
    {
        presenter.registerToApp(dbRef,mAuth, "sd","fs","df","uzair10","uzair","sdf");

        verify(signUpView).showMessage("Password must be same and 6 digits", "warning");
    }

    @Test
    public void passwordAndGmailValid() throws Exception
    {
        presenter.registerToApp(dbRef,mAuth, "sd","fs","df","uzair10","uzair10","sdf");

        verify(signUpView).showProgressBar();
    }

}
