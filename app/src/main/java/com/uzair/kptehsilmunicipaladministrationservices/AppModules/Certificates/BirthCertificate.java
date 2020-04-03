package com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.uzair.kptehsilmunicipaladministrationservices.R;

import java.util.Calendar;

public class BirthCertificate extends AppCompatActivity {

    private Toolbar mToolbar;
    private Spinner childGender;
    private String[] gender = {"Male","Female"};
    private EditText applicantName , applicantCnic , childName , childRelation , religion , fatherName , fatherCnci,
                     motherName , motherCnic , grandFatherName , grandFatherCnic , districtOfBirth , dateOfBirth,
                     vaccinated , placeOfBirth , doctorOrMideWifeName , disability , address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_certificate);
        initViews();
        // spinner method call
        setSpinnerItems();


    }

       private void initViews()
       {
              dateOfBirth = findViewById(R.id.childDateOfBirthInBirthCertificate);
              childGender = findViewById(R.id.childGenderInBirthCertificate);

               //  app tool bar
               mToolbar = findViewById(R.id.child_birth_certificate_tool_bar);
               setSupportActionBar(mToolbar);
               setTitle("Birth Certificate Form");

           // tool bar back arrow enabled
           ActionBar actionBar = getSupportActionBar();
           actionBar.setHomeAsUpIndicator(R.drawable.backicon);
           actionBar.setDisplayHomeAsUpEnabled(true);
       }


     private void setSpinnerItems()
     {
         ArrayAdapter genderAdapter  = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,gender);
         childGender.setAdapter(genderAdapter);
     }


}
