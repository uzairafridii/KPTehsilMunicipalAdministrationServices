package com.uzair.kptehsilmunicipaladministrationservices.Models;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.MainActivity;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.ModelOfHomeRecycler.HomeModel;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.MainPagePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.MainPageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        alert.setIcon(R.drawable.logo);
        alert.setCancelable(false);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                mainPageView.closeActivity();


            }
        }).setNeutralButton("No", new DialogInterface.OnClickListener() {
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
        View myView  = LayoutInflater.from(context).inflate(R.layout.main_screen_dialog, null);
        alert.setView(myView);
        final Dialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        myView.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mainPageView.closeActivity();

            }
        });


        alert.show();
    }

    @Override
    public void updateToken(final FirebaseAuth mAuth)
    {

         if(mAuth != null) {
             // get the device token
             FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                 @Override
                 public void onSuccess(InstanceIdResult instanceIdResult) {

                     if (mAuth.getCurrentUser().getUid() != null) {
                         // update the device token whe user logged into app
                         DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference()
                                 .child("Users").child(mAuth.getCurrentUser().getUid());

                         Map<String, Object> updateToken = new HashMap<>();
                         updateToken.put("device_token", instanceIdResult.getToken());

                         dbRef.updateChildren(updateToken);
                     }

                 }
             });
         }
    }

    @Override
    public void getUserName(DatabaseReference dbRef, FirebaseAuth mAuth)
    {
        if(dbRef != null && mAuth != null)
        {
            dbRef.child("Users").child(mAuth.getCurrentUser().getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String name = dataSnapshot.child("user_name").getValue().toString();
                            mainPageView.setUserName(name);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
        }

    }
}
