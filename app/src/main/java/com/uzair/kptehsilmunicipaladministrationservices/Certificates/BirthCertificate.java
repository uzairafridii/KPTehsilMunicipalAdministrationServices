package com.uzair.kptehsilmunicipaladministrationservices.Certificates;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.uzair.kptehsilmunicipaladministrationservices.R;

import java.util.Calendar;

public class BirthCertificate extends AppCompatActivity {
    private EditText dateOfBirth;
    private Spinner childGender;
    private String[] gender = {"Male","Female"};


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
       }

    // set values in spinner
     private void setSpinnerItems()
     {
         ArrayAdapter genderAdapter  = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,gender);
         childGender.setAdapter(genderAdapter);
     }

       // edit text date of birth click listener
    public void datePickerDialog(View view)
    {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear+1)
                        + "/" + String.valueOf(year);
                dateOfBirth.setText(date);
            }
        } , year , month , day);
        dialog.show();
    }
}
