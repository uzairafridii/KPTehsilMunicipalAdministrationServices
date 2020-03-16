package com.uzair.kptehsilmunicipaladministrationservices.Models;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.ProfilePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.ProfileView;

public class ProfilePresenterImplementer implements ProfilePresenter
{
    private ProfileView view;

    public ProfilePresenterImplementer(ProfileView view) {
        this.view = view;
    }

    @Override
    public void getUserProfileData(DatabaseReference dbRef, FirebaseAuth userAuth) {

        view.showErrorMessage("GetUser profile method");
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
                        String userImage = dataSnapshot.child("user_image").getValue().toString();

                        view.getUserRecord(userName, userEmail, userPhone, userCnic, userImage);
                        view.showErrorMessage(userName);
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
}
