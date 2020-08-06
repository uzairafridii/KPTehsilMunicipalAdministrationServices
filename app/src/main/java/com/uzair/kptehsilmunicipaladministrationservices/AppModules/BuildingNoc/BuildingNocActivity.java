package com.uzair.kptehsilmunicipaladministrationservices.AppModules.BuildingNoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.uzair.kptehsilmunicipaladministrationservices.Models.NocPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.NocView;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class BuildingNocActivity extends AppCompatActivity implements NocView {

    public static final int REQUEST_GALLERY_PHOTO = 1;
    public static final int REQUEST_CODE = 22;
    private Uri imageUri = null;
    private Toolbar mToolbar;
    private RecyclerView nocRvList;
    private LinearLayoutManager layoutManager;
    private AdapterForNocRvList adapterForNocRvList;
    private ProgressDialog mProgressDialog;
    private TextInputLayout tvNocTitle;
    private TextView txtNoFileChoose, txtYourTotalNoc;
    private ImageView chooseNocImage;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private NocPresenterImplementer presenterImplementer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_noc);

        initViews();

        // load your noc
        presenterImplementer.readYourNoc(mDatabaseRef , mAuth);

    }


    private void initViews() {

        presenterImplementer = new NocPresenterImplementer(this, this);

        mProgressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);

        mToolbar = findViewById(R.id.noc_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("Building Noc");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtNoFileChoose = findViewById(R.id.txtNoFileChoose);
        tvNocTitle = findViewById(R.id.nocTitleTextInputLayout);
        txtYourTotalNoc = findViewById(R.id.txtYourTotalNoc);
        chooseNocImage = findViewById(R.id.chooseNocImage);

        nocRvList = findViewById(R.id.nocRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        nocRvList.setLayoutManager(layoutManager);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();


    }

    // upload image button click
    public void selectImageFromGalleryButtonClick(View view) {
        presenterImplementer.chooseGalleryClick();
    }

    // submit noc details button click
    public void submiNocDetailsButtonClick(View view) {

        String title = tvNocTitle.getEditText().getText().toString();
        presenterImplementer.sendNocDataToFirebase(mDatabaseRef, mAuth, mStorageRef, imageUri, title);
    }


    // firebase callbacks
    @Override
    public void showProgressDialog() {

        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {

        mProgressDialog.dismiss();
    }


    @Override
    public void readDataList(List<NocDataModel> listOfNoc)
    {
            adapterForNocRvList = new AdapterForNocRvList(BuildingNocActivity.this , listOfNoc);
            adapterForNocRvList.notifyDataSetChanged();
            nocRvList.setAdapter(adapterForNocRvList);

    }

    @Override
    public void showTextYourNoc() {
        txtYourTotalNoc.setVisibility(View.VISIBLE);
    }


    // gallery and select image call backs
    @Override
    public void chooseGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY_PHOTO && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            presenterImplementer.previewImage(selectedImageUri);
        }

    }

    @Override
    public void displayImagePreview(Uri mFileUri) {

        imageUri = mFileUri;
        chooseNocImage.setVisibility(View.VISIBLE);
        txtNoFileChoose.setVisibility(View.INVISIBLE);
        chooseNocImage.setImageURI(imageUri);
    }


    // storage permissions callbacks
    @Override
    public boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    @Override
    public void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                presenterImplementer.chooseGalleryClick();
            }
        }
    }


    // error message
    @Override
    public void showErrorMessage(String message , String type) {

        if(type.equals("error")) {
            Toasty.error(this, message, Toasty.LENGTH_SHORT).show();
        }
        else if(type.equals("info"))
        {
            Toasty.info(this, message, Toasty.LENGTH_LONG).show();
        }
    }

    // clear all fields
    @Override
    public void clearAllFields() {
        txtNoFileChoose.setVisibility(View.VISIBLE);
        chooseNocImage.setVisibility(View.INVISIBLE);
        imageUri = null;
        tvNocTitle.getEditText().setText("");
    }



}
