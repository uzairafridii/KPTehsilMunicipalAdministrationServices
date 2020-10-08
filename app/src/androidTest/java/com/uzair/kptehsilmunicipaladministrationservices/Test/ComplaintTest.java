package com.uzair.kptehsilmunicipaladministrationservices.Test;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.uzair.kptehsilmunicipaladministrationservices.AppModules.BuildingNoc.BuildingNocActivity;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain.AddComplaints;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain.Complaints;
import com.uzair.kptehsilmunicipaladministrationservices.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class ComplaintTest
{
    @Rule
    public ActivityScenarioRule<AddComplaints> complain  = new ActivityScenarioRule<AddComplaints>(AddComplaints.class);

    @Test

    public void clickOnSubmitComplain() throws  Exception
    {
        onView(ViewMatchers.withId(R.id.submit_complain_btn)).perform(click());
    }

    @Test
    public void clickOnSelectImage() throws Exception
    {
        onView(withId(R.id.upload_image_btn)).perform(click());
    }

    @Test
    public void clickOnAddLocation() throws Exception
    {
        onView(withId(R.id.location)).perform(click());
    }
}
