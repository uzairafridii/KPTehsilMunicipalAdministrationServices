package com.uzair.kptehsilmunicipaladministrationservices.AppModules.FireFighting;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.uzair.kptehsilmunicipaladministrationservices.Models.FirebrigadePresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.FirebrigadeView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FireFighting extends BottomSheetDialogFragment implements FirebrigadeView {


    public static final int REQUEST_CODE = 12;
    private ImageButton callBtn , notificationBtn;
    private TextView phoneNumber , driverName;
    private FirebrigadePresenterImplementer implementer;

    public FireFighting() {
        // Required empty public constructor
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
        View myView = inflater.inflate(R.layout.fragment_fire_fighting, container, false);

         implementer = new FirebrigadePresenterImplementer(this, getContext());

        callBtn = myView.findViewById(R.id.call_image_btn);
        notificationBtn = myView.findViewById(R.id.notificaty_image_btn);

        phoneNumber = myView.findViewById(R.id.phone_number);
        driverName = myView.findViewById(R.id.nameOfDriver);



        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String number = phoneNumber.getText().toString();
               implementer.callDriver(number);

            }
        });




        return myView;
    }


    @Override
    public void onErrorMessage(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean checkPhonePermission() {


        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            return true;
        }

        return false;
    }

    @Override
    public void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
    }
}
