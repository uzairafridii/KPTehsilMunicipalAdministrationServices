package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;

import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.MainActivity;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.ModelOfHomeRecycler.HomeModel;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.MainPagePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.MainPageView;

import java.util.ArrayList;
import java.util.List;

public class MainPagePresenterImplementer implements MainPagePresenter
{
    private MainPageView mainPageView;
    private Context context;
    private List<HomeModel> mHomeModel = new ArrayList<>();

    public MainPagePresenterImplementer(MainPageView mainPageView, Context context) {
        this.mainPageView = mainPageView;
        this.context = context;
    }


    @Override
    public void closingDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("Do you want to close the app?");
        alert.setTitle("TMA App");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                mainPageView.closeActivity();


            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        alert.show();


    }

    @Override
    public void setHomeRecyclerAdapter() {
        mHomeModel.add(new HomeModel("Complaints", R.drawable.complain));
        mHomeModel.add(new HomeModel("Water Bills", R.drawable.bill));
        mHomeModel.add(new HomeModel("Fire Brigade", R.drawable.fire));
        mHomeModel.add(new HomeModel("Building Noc", R.drawable.noc));
        mHomeModel.add(new HomeModel("Taxes", R.drawable.ic_launcher_background));
        mHomeModel.add(new HomeModel("Certificates", R.drawable.ic_launcher_background));
        mHomeModel.add(new HomeModel("Profile", R.drawable.avatar));

        mainPageView.onSetHomeRecyclerAdapter(mHomeModel);
    }

    @Override
    public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void showNoNetworkDialog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("No network is available");
        alert.setTitle("Internet Detection");
        alert.setCancelable(false);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                mainPageView.closeActivity();
            }
        });

        alert.show();
    }
}
