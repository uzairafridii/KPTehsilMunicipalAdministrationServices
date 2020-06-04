package com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.uzair.kptehsilmunicipaladministrationservices.Models.BirthCertificatePresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.BirthCertificatePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.BirthCertificateView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BirthCertificate extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        BirthCertificateView
{

    private static final int REQUEST_GALLERY_PHOTO = 1220;
    private List<Uri> cnicImages = new ArrayList<>();
    private Toolbar mToolbar;
    private Spinner childGender , unionCouncilSpinner;
    private String selectedGender , selectedUC;
    private ImageView frontSide, backSide;
    private TextView dateOfBirth;
    private EditText  childName, childRelation, religion, fatherName, fatherCnic,
            motherName, motherCnic, grandFatherName, grandFatherCnic, districtOfBirth,
            vaccinated, placeOfBirth, doctorOrMideWifeName, disability, address;
    private String  child_name , child_relation , child_religion , child_father,
            child_father_cnic, child_mother_name , child_mother_cnic, child_grand_father_name , child_grand_father_cnic,
            child_district_of_birth, child_date_of_birth ,child_vaccinated ,child_place_of_birth,
            doctor_or_mide_wife_name , child_disability , child_address;


    private BirthCertificatePresenter birthCertificatePresenter;
    private ProgressDialog progressDialog;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_certificate);

        initViews();

        birthCertificatePresenter.onSetSpinnerAdapter();

    }

    // initiate all views
    private void initViews() {

        childName = findViewById(R.id.childNameInBirthCertificate);
        childRelation = findViewById(R.id.childNameInBirthCertificate);
        religion = findViewById(R.id.childReligionInBirthCertificate);
        fatherName = findViewById(R.id.childFatherNameInBirthCertificate);
        fatherCnic = findViewById(R.id.childFatherCnicInBirthCertificate);
        motherName = findViewById(R.id.childMotherNameInBirthCertificate);
        motherCnic = findViewById(R.id.childMotherCnicInBirthCertificate);
        grandFatherName = findViewById(R.id.childGrandFatherCnicInBirthCertificate);
        grandFatherCnic = findViewById(R.id.childGrandFatherCnicInBirthCertificate);
        districtOfBirth = findViewById(R.id.childDistrictOfBirthInBirthCertificate);
        dateOfBirth = findViewById(R.id.childDateOfBirthInBirthCertificate);
        vaccinated = findViewById(R.id.childVaccinatedInBirthCertificate);
        placeOfBirth = findViewById(R.id.childPlaceOfBirth);
        doctorOrMideWifeName = findViewById(R.id.doctorOrMidWifeNameInBirthCertificate);
        disability = findViewById(R.id.disabilityInBirthCertificate);
        address = findViewById(R.id.addressInBirthCertificate);


        birthCertificatePresenter = new BirthCertificatePresenterImplementer(this, getApplicationContext());

        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        childGender = findViewById(R.id.childGenderInBirthCertificate);
        childGender.setOnItemSelectedListener(this);

        unionCouncilSpinner = findViewById(R.id.ucSpinner);
        unionCouncilSpinner.setOnItemSelectedListener(this);

        //  app tool bar
        mToolbar = findViewById(R.id.child_birth_certificate_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("Birth Certificate Form");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("CertificatesImage");

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        // if spinner equal union council then this will if will be call
        if (adapterView.getId() == R.id.ucSpinner)
        {
            selectedUC = adapterView.getItemAtPosition(i).toString();
        }

        // if select gender than this if will be call
        if (adapterView.getId() == R.id.childGenderInBirthCertificate)
        {

            selectedGender = adapterView.getItemAtPosition(i).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    // select date from date picker on date of birth filed click
    public void clickOnDateOfBirthField(View view) {

        // get the current date here
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        Log.d("DateOfBirth", "clickOnDateOfBirthField: " + mYear + "\n" + mMonth + "\n" + mDay);

        // time picker dialog to pick date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateOfBirth.setText(dayOfMonth + "-" + (monthOfYear) + "-" + year);


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onShowProgressDialog() {

        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void onDismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setSpinnerAdapter() {
        //set spinner adapter for both  gender and uc
        setSpinnerAdapter(childGender , getResources().getStringArray(R.array.gender));
       setSpinnerAdapter(unionCouncilSpinner , getResources().getStringArray(R.array.UC));
    }


    @Override
    public void chooseGallery() {
        cnicImages.clear();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY_PHOTO);
    }

    // preview the cnic images
    @Override
    public void displayImagePreview(List<Uri> mFileUri)
    {
        frontSide = findViewById(R.id.chooseCnicFrontImage);
        backSide = findViewById(R.id.chooseCnicBackImage);
        frontSide.setVisibility(View.VISIBLE);
        backSide.setVisibility(View.VISIBLE);

        frontSide.setImageURI(mFileUri.get(0));
        backSide.setImageURI(mFileUri.get(1));
        findViewById(R.id.txtBackOfCnic).setVisibility(View.INVISIBLE);
        findViewById(R.id.txtFrontOfCnic).setVisibility(View.INVISIBLE);

    }

    // button click to upload cnic images
    public void uploadCnicImages(View view) {
        birthCertificatePresenter.chooseGalleryClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY_PHOTO && resultCode == RESULT_OK) {

            ClipData clipData = data.getClipData();

            if (clipData == null)
            {
                Toast.makeText(this, "Please select front and back image of cnic", Toast.LENGTH_LONG).show();
            }
            else
                {

                for (int i = 0; i < clipData.getItemCount(); i++) {

                    cnicImages.add(clipData.getItemAt(i).getUri());
                }

                birthCertificatePresenter.previewImage(cnicImages);
            }


        }

    }

    // submit form button click
    public void submitCertificateForm(View view)
    {
          // first get values from form
        child_name = childName.getText().toString();
        child_relation =  childRelation.getText().toString();
        child_religion = religion.getText().toString();
        child_father = fatherName.getText().toString();
        child_father_cnic = fatherCnic.getText().toString();
        child_mother_name = motherName.getText().toString();
        child_mother_cnic = motherCnic.getText().toString();
        child_grand_father_name = grandFatherName.getText().toString();
        child_grand_father_cnic = grandFatherCnic.getText().toString();
        child_district_of_birth = districtOfBirth.getText().toString();
        child_date_of_birth = dateOfBirth.getText().toString();
        child_vaccinated = vaccinated.getText().toString();
        child_place_of_birth =  placeOfBirth.getText().toString();
        doctor_or_mide_wife_name =  doctorOrMideWifeName.getText().toString();
        child_disability = disability.getText().toString();
        child_address =  address.getText().toString();

        // then call onSubmit method presenter
        birthCertificatePresenter.onSubmitForm(databaseReference, storageReference, mAuth,
                 child_name , child_religion , child_relation ,
                child_father, child_father_cnic, child_mother_name , child_mother_cnic ,
                child_grand_father_name ,child_grand_father_cnic, child_district_of_birth ,
                child_date_of_birth , child_vaccinated , child_place_of_birth ,
                doctor_or_mide_wife_name, child_disability, child_address , selectedGender,selectedUC ,cnicImages);

    }

    // clear all editext fields
    @Override
    public void clearAllFileds()
    {
      childName.setText("");
      fatherName.setText("");
      fatherCnic.setText("");
      motherName.setText("");
      motherCnic.setText("");
      grandFatherName.setText("");
      grandFatherCnic.setText("");
      religion.setText("");
      childRelation.setText("");
      disability.setText("");
      doctorOrMideWifeName.setText("");
      dateOfBirth.setText("");
      districtOfBirth.setText("");
      address.setText("");
      placeOfBirth.setText("");
      vaccinated.setText("");

      // clear images array
        cnicImages.clear();


    }

    private void setSpinnerAdapter(Spinner spinner , String[] list)
    {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
    }
}
