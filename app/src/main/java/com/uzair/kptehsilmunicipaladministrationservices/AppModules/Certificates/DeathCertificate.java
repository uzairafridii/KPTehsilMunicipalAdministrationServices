package com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.uzair.kptehsilmunicipaladministrationservices.Models.DeathCertificatePresenterImplmenter;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.DeathCertificatePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.DeathCertificateView;

import java.util.ArrayList;
import java.util.List;

public class DeathCertificate extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,
        DeathCertificateView {

    private Toolbar mToolbar;
    private static final int REQUEST_GALLERY_PHOTO = 1100;
    private List<Uri> cnicImages = new ArrayList<>();
    private Spinner unionCouncilSpinner;
    private String selectedUC;
    private ImageView frontSide, backSide;


    private EditText applicantName, applicantCnic, deceasedName, deceasedCnic, relation, religion, fatherName, fatherCnic,
            motherName, motherCnic, husbandName, husbandCnic, gravyardName, placeOfBirth, deceasedDateOfBirth;

    private String appli_name , appli_cnic , deceased_name, deceased_cnic,  deceased_relation , deceased_religion , deceased_father,
            deceased_father_cnic, deceased_mother_name , deceased_mother_cnic, husband_name , husband_cnic,
            gravyard,place_of_birth, deceased_date_of_birth;


    private DeathCertificatePresenter deathCertificatePresenter;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_certificate);
        initViews();

        deathCertificatePresenter.onSetSpinnerAdapter();

    }

    private void initViews()
    {

        applicantName = findViewById(R.id.applicantNameInDeathCertificate);
        applicantCnic = findViewById(R.id.applicantCnicInDeathCertificate);
        deceasedName  = findViewById(R.id.deceasedNameInDeathCertificate);
        deceasedCnic  = findViewById(R.id.deceasedCnicDeathCertificate);
        relation  =  findViewById(R.id.relationWithApplicantsInDeathCertificate);
        religion = findViewById(R.id.religionInDeathCertificate);
        fatherName = findViewById(R.id.deceasedFatherNameInDeathCertificate);
        fatherCnic = findViewById(R.id.deceasedFatherCnicInDeathCertificate);
        motherName = findViewById(R.id.deceasedMotherNameInDeathCertificate);
        motherCnic = findViewById(R.id.deceasedMotherCnicInDeathCertificate);
        husbandName = findViewById(R.id.husbandNameInDeathCertificate);
        husbandCnic = findViewById(R.id.husbandCnicInDeathCertifcate);
        placeOfBirth = findViewById(R.id.placeOfBirthInDeathCertificate);
        gravyardName  = findViewById(R.id.gravayedNameInDeathCertificate);
        deceasedDateOfBirth = findViewById(R.id.deceasedDateOfBirthInDeathCertificate);



        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);

        unionCouncilSpinner = findViewById(R.id.ucSpinnerInDeath);
        unionCouncilSpinner.setOnItemSelectedListener(this);


        mToolbar = findViewById(R.id.death_certificate_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("Death Certificate Form");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);


        deathCertificatePresenter = new DeathCertificatePresenterImplmenter(this, this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Certificates").push();
        storageReference = FirebaseStorage.getInstance().getReference().child("CertificatesImage");

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        selectedUC = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onShowProgressDialog()
    {
        // set message and show dialog
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public void onDismissProgressDialog() {
        progressDialog.dismiss();

    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setSpinnerAdapter() {

        // spinner adapter of union council
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                                                         getResources().getStringArray(R.array.UC));
        unionCouncilSpinner.setAdapter(adapter);
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

                deathCertificatePresenter.previewImage(cnicImages);
            }


        }

    }


    @Override
    public void displayImagePreview(List<Uri> mFileUri)
    {
        frontSide = findViewById(R.id.chooseCnicFrontImageInDeath);
        backSide = findViewById(R.id.chooseCnicBackImageInDeath);
        frontSide.setVisibility(View.VISIBLE);
        backSide.setVisibility(View.VISIBLE);

        frontSide.setImageURI(mFileUri.get(0));
        backSide.setImageURI(mFileUri.get(1));
        findViewById(R.id.txtFrontOfCnic).setVisibility(View.INVISIBLE);
        findViewById(R.id.txtBackOfCnic).setVisibility(View.INVISIBLE);
    }

    // open gallery to select cnic images
    public void openGalleryButtonClick(View view)
    {
        deathCertificatePresenter.chooseGalleryClick();
    }

    // submit form button click listener
    public void submitFormBtnClick(View view)
    {
        // first get values from form
        appli_name = applicantName.getText().toString();
        appli_cnic = applicantCnic.getText().toString();
        deceased_name = deceasedName.getText().toString();
        deceased_cnic = deceasedCnic.getText().toString();
        deceased_relation =  relation.getText().toString();
        deceased_religion = religion.getText().toString();
        deceased_father = fatherName.getText().toString();
        deceased_father_cnic = fatherCnic.getText().toString();
        deceased_mother_name = motherName.getText().toString();
        deceased_mother_cnic = motherCnic.getText().toString();
        husband_name = husbandName.getText().toString();
        husband_cnic = husbandCnic.getText().toString();
        place_of_birth =  placeOfBirth.getText().toString();
        gravyard  = gravyardName.getText().toString();
        deceased_date_of_birth  = deceasedDateOfBirth.getText().toString();



        deathCertificatePresenter.onSubmitForm(databaseReference , storageReference , mAuth,
                appli_name , appli_cnic , deceased_name, deceased_cnic, deceased_religion, deceased_relation,
                deceased_father, deceased_father_cnic, deceased_mother_name, deceased_mother_cnic, husband_name,
                husband_cnic, deceased_date_of_birth, gravyard, place_of_birth, selectedUC , cnicImages);

    }


    // clear all editext fields
    @Override
    public void clearAllFileds()
    {

        applicantName.setText("");
        applicantCnic.setText("");
        deceasedName.setText("");
        deceasedCnic.setText("");
        fatherName.setText("");
        fatherCnic.setText("");
        motherName.setText("");
        motherCnic.setText("");
        husbandName.setText("");
        husbandCnic.setText("");
        religion.setText("");
        relation.setText("");
        gravyardName.setText("");
        placeOfBirth.setText("");
        deceasedDateOfBirth.setText("");

        // clear images array
        cnicImages.clear();

    }
}
