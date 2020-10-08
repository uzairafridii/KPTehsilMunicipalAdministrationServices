package com.uzair.kptehsilmunicipaladministrationservices.Test;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.uzair.kptehsilmunicipaladministrationservices.AppModules.BuildingNoc.BuildingNocActivity;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain.AddComplaints;
import com.uzair.kptehsilmunicipaladministrationservices.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class NocTest
{
    @Rule
    public ActivityScenarioRule<BuildingNocActivity> complain  = new ActivityScenarioRule<BuildingNocActivity>(BuildingNocActivity.class);

    @Test

    public void clickOnAddBtn() throws  Exception
    {
        onView(ViewMatchers.withId(R.id.btnSubmitNoc)).perform(click());
    }

    @Test
    public void clickOnAddImage() throws Exception
    {
        onView(withId(R.id.btnUploadNoc)).perform(click());
    }


}
