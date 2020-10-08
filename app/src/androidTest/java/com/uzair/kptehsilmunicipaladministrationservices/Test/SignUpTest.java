package com.uzair.kptehsilmunicipaladministrationservices.Test;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp.SignUp;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain.AddComplaints;
import com.uzair.kptehsilmunicipaladministrationservices.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class SignUpTest
{

    @Rule
    public ActivityScenarioRule<SignUp> complain  = new ActivityScenarioRule<SignUp>(SignUp.class);

    @Test
    public void clickToGoLoginActivity() throws  Exception
    {
        onView(ViewMatchers.withId(R.id.txtAlreadyHaveAccount)).perform(click());
    }

    @Test
    public void clickToRegister() throws  Exception
    {
        onView(withId(R.id.userSignUpBtn)).perform(click());
    }

}
