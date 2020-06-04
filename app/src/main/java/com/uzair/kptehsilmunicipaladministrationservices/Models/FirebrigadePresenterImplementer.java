package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.FirebrigadePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.FirebrigadeView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FirebrigadePresenterImplementer implements FirebrigadePresenter
{

    private FirebrigadeView view;
    private Context context;
    private FusedLocationProviderClient mFusedLocationClient;
    private double lat , lng;

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
                view.onErrorMessage("Please enable permission");
                view.requestPermission();
            }
            else {
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

    // get driver record details
    @Override
    public void getDriverNameAndPhoneNumber(DatabaseReference dbRef)
    {
        if ((dbRef != null))
        {
            dbRef.child("Fire Fighting").child("Driver Details")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            String name = dataSnapshot.child("driverName").getValue().toString();
                            String phone = dataSnapshot.child("driverPhone").getValue().toString();

                            view.onSetPhoneNumberAndDriverName(name , phone);

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
        }

    }

    // store data in firebase
    @Override
    public void sendNotification(final DatabaseReference reference , final FirebaseAuth auth , final String uid)
    {
        if(reference != null && auth != null && !uid.isEmpty()) {

            if(view.checkPhonePermission() && view.isLocationEnabled())
            {
                // if location is 0 then get the user current location
                if(lat == 0 && lng == 0)
                    {
                        getLastLocation();
                        view.onErrorMessage("Something went wrong click again");

                    }
                else
                {
                    // store data in firebase
                    String date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    Map<String, String> fireMap = new HashMap<>();
                    fireMap.put("lat", String.valueOf(lat));
                    fireMap.put("lng", String.valueOf(lng));
                    fireMap.put("uid", auth.getCurrentUser().getUid());
                    fireMap.put("date", date);

                    reference.child("Fire Fighting").child("Fire Brigade Request")
                            .push().setValue(fireMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                // store notification data in firebase
                                addNotificationData(auth , uid);

                            }
                        }
                    });
                }


                }else
                {
                    view.onErrorMessage("Turn on GPS");
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);
                }



        }

    }

    // notification method
    private void addNotificationData(FirebaseAuth auth, String uid)
    {
        // store receiver uid and current user uid for notification

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Notification")
                .child("fire_fighting");

        Map<String, String> fireNotification = new HashMap<>();
        fireNotification.put("from", auth.getCurrentUser().getUid());
        dbRef.child(uid).push().setValue(fireNotification).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    view.onErrorMessage("Notification send to TMA Staff");
                }

            }
        });
    }

    // get the UID of infra head
    @Override
    public void getInfraHeadUid(DatabaseReference dbRef)
    {
        if(dbRef != null) {
            dbRef.child("Workers Head").orderByChild("department").equalTo("Infrastructure")
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                        {
                            String infraHeadUid = dataSnapshot.child("uid").getValue().toString();
                            view.infraHeadUid(infraHeadUid);
                        }
                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
        }

    }


    // get the current location of user
    @Override
    public void getLastLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        if (view.checkPhonePermission()) {
            if (view.isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null)
                                {
                                    requestNewLocationData();
                                } else
                                {
                                    lat = location.getLatitude();
                                    lng = location.getLongitude();
                                }
                            }
                        }
                );
            } else {
                view.onErrorMessage("Turn on location");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        } else {
            view.requestPermission();
        }
    }


    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();

        }
    };

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }
}
