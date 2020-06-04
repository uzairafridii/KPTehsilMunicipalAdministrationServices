package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.BuildingNoc.BuildingNocActivity;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.ProfilePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.ProfileView;

import java.util.HashMap;
import java.util.Map;

public class ProfilePresenterImplementer implements ProfilePresenter
{
    private ProfileView view;
    private Context context;

    public ProfilePresenterImplementer(ProfileView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void getUserProfileData(DatabaseReference dbRef, FirebaseAuth userAuth) {

        if(dbRef != null && userAuth != null)
        {
            view.showProgressDialog();

            Query query = dbRef.child(userAuth.getCurrentUser().getUid());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.hasChildren()) {

                        String userName = dataSnapshot.child("user_name").getValue().toString();
                        String userPhone = dataSnapshot.child("user_phone").getValue().toString();
                        String userEmail = dataSnapshot.child("user_email").getValue().toString();
                        String userCnic = dataSnapshot.child("user_cnic").getValue().toString();

                        view.getUserRecord(userName, userEmail, userPhone, userCnic);
                        view.hideProgressDialog();
                    }
                    else
                    {
                        view.hideProgressDialog();
                        view.showErrorMessage("No record found");
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else {
            view.showErrorMessage("Reference is null");
        }

    }

    @Override
    public void onUpdateChildren()
    {
        view.showEditDialog();
    }

    @Override
    public void signOut(FirebaseAuth userAuth)
    {

        userAuth.signOut();
        view.onLogout();
    }

}
