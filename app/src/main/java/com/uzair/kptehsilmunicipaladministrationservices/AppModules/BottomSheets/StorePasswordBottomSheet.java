package com.uzair.kptehsilmunicipaladministrationservices.AppModules.BottomSheets;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.LoginAndSignUp.Login;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.MainActivity;
import com.uzair.kptehsilmunicipaladministrationservices.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class StorePasswordBottomSheet extends BottomSheetDialogFragment {

    private Button savePassword  , notSavePassword;
    private TextView password , email;
    private View myView;
    private String receivedPassword, receivedEmail;


    public StorePasswordBottomSheet(String receivedEmail , String receivedPassword)
    {
        this.receivedEmail = receivedEmail;
        this.receivedPassword = receivedPassword;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_store_password_bottom_sheet, container, false);

        initViews();

        // not save password
        notSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startMainActivity();
            }
        });

        // save password in app
        savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = getContext().getSharedPreferences(Login.SAVE_PASSWORD, MODE_PRIVATE).edit();
                editor.putString("email", receivedEmail);
                editor.putString("password", receivedPassword);
                editor.apply();

                startMainActivity();
            }
        });


        return myView;
    }

    private void initViews()
    {


        savePassword = myView.findViewById(R.id.savePasswordBtn);
        notSavePassword = myView.findViewById(R.id.noThanksBtn);

        password = myView.findViewById(R.id.userPasswordValueInBottmoSheet);
        email = myView.findViewById(R.id.userGmail);

        password.setText(receivedPassword);
        email.setText(receivedEmail);


    }

    // move to main or home activity
    private void startMainActivity()
    {
        Intent intent = new Intent(getContext() , MainActivity.class);
        startActivity(intent);
        getActivity().getSupportFragmentManager().beginTransaction().remove(StorePasswordBottomSheet.this).commit();
    }

}
