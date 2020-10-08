package com.uzair.kptehsilmunicipaladministrationservices.Test;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp.Login;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp.SignUp;
import com.uzair.kptehsilmunicipaladministrationservices.Models.LoginPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.LoginPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.LoginView;

import org.hamcrest.JMock1Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class LoginPageIntegerationTesting
{

    private LoginPresenter presenter;
    private LoginView loginView;
    private FirebaseAuth auth;
    @Rule
    public ActivityScenarioRule<Login> complain  = new ActivityScenarioRule<Login>(Login.class);

    @Before
    public void setUp() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        presenter = new LoginPresenterImplementer(loginView, appContext);
    }


    @Test
    public void clickToGoRegisterActivity() throws  Exception
    {
        onView(ViewMatchers.withId(R.id.txtCreateNewAccount)).perform(click());
    }

    @Test
    public void clickToLogin() throws  Exception
    {
        onView(withId(R.id.signInBtn)).perform(click());
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
