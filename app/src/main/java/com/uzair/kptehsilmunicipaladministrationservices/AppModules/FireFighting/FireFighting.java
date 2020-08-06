package com.uzair.kptehsilmunicipaladministrationservices.AppModules.FireFighting;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uzair.kptehsilmunicipaladministrationservices.Models.FirebrigadePresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.FirebrigadeView;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class FireFighting extends BottomSheetDialogFragment implements FirebrigadeView {


    public static final int REQUEST_CODE = 12;
    private View myView;
    private ImageButton callBtn, notificationBtn;
    private TextView phoneNumber, driverName;
    private FirebrigadePresenterImplementer implementer;
    private String infraHeadUid;
    private ProgressBar progressBar;
    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;

    public FireFighting() {
    }

    // create instance of fire fighting fragment dialog
    public static FireFighting newInstance() {
        FireFighting fragment = new FireFighting();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_fire_fighting, container, false);

        initViews();


        // phone button click listener
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = phoneNumber.getText().toString();
                implementer.callDriver(number);

            }
        });

        // notification button click
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                implementer.sendNotification(dbRef, mAuth, infraHeadUid);
            }
        });


        return myView;
    }

    private void initViews() {
        implementer = new FirebrigadePresenterImplementer(this, getContext());

        progressBar = myView.findViewById(R.id.progresBar);

        callBtn = myView.findViewById(R.id.call_image_btn);
        notificationBtn = myView.findViewById(R.id.notificaty_image_btn);

        phoneNumber = myView.findViewById(R.id.phone_number);
        driverName = myView.findViewById(R.id.nameOfDriver);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        implementer.getDriverNameAndPhoneNumber(dbRef);
        implementer.getInfraHeadUid(dbRef);
        implementer.getLastLocation();
    }


    @Override
    public void onErrorMessage(String message, String type) {

        if (type.equals("info")) {
            Toasty.info(getContext(), message, Toasty.LENGTH_LONG).show();
        } else if (type.equals("warning")) {
            Toasty.warning(getContext(), message, Toasty.LENGTH_SHORT).show();
        } else if (type.equals("success")) {
            Toasty.success(getContext(), message, Toasty.LENGTH_SHORT).show();
        } else if (type.equals("error")) {
            Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean checkPhonePermission() {

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }

    @Override
    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            return true;
        }

        return false;
    }

    @Override
    public void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
    }

    @Override
    public void requestLocationPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);

    }

    @Override
    public boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Unable to send notification ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSetPhoneNumberAndDriverName(String name, String phoneNum) {

        phoneNumber.setText(phoneNum);
        driverName.setText(name);
    }

    @Override
    public void infraHeadUid(String uid) {
        infraHeadUid = uid;
        Log.d("uidOfInfra", "infraHeadUid: " + uid);
    }

    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {

        progressBar.setVisibility(View.GONE);
    }

}
