package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.FirebrigadePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.FirebrigadeView;

public class FirebrigadePresenterImplementer implements FirebrigadePresenter
{

    private FirebrigadeView view;
    private Context context;

    public FirebrigadePresenterImplementer(FirebrigadeView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void callDriver(String phoneNumber)
    {

        if(!phoneNumber.isEmpty()) {

            if(!view.checkPhonePermission())
            {
                view.onErrorMessage("Please enale permission");
                view.requestPermission();
            }
            else
            {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                context.startActivity(callIntent);
            }


        }
        else
        {
            view.onErrorMessage("phone number is empty");
        }


    }

    @Override
    public void sendNotification(DatabaseReference reference) {

    }
}
