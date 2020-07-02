package com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.AdapterOfComplaintRecycler.AdapterForImagesRecycler;
import com.uzair.kptehsilmunicipaladministrationservices.Models.AddComplaintsPresenterImplementer;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.AddComplaintsView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class AddComplaints extends AppCompatActivity implements AddComplaintsView
{

    public static final int REQUEST_CODE = 1;
    private Toolbar mToolbar;
    private EditText editTextTitle, editTextDescription;
    private RadioButton sanitation, infrastructure;
    private RecyclerView mRecyclerView;
    private ProgressDialog progressDialog;

    private AdapterForImagesRecycler adapter;
    private List<Uri> imageUriList;
    private  double lat , lng;
    private AddComplaintsPresenterImplementer presenterImplementer;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaints);

        initViews();


    }

    private void initViews() {

        presenterImplementer = new AddComplaintsPresenterImplementer(this);

        //  app tool bar
        mToolbar = findViewById(R.id.addComplain_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("Add Complaint");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // edit text
        editTextTitle = findViewById(R.id.complaint_title);
        editTextDescription = findViewById(R.id.complaint_description_edit_text);

        // radio buttons
        sanitation = findViewById(R.id.rd_btn_sanitation);
        infrastructure = findViewById(R.id.rd_btn_infrastructure);

        // progress dialog
        progressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);

        // recycler view
        imageUriList = new ArrayList<>();


        //firebase
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Complaints");
        mStorageReference = FirebaseStorage.getInstance().getReference().child("Photo");
        mAuth = FirebaseAuth.getInstance();


    }

    // select image button click
    public void uploadComplaintImageBtn(View view) {

        imageUriList.clear();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);

    }

    // submit complaint button click
    public void submitComplaintBtn(View view) {

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();


                if(sanitation.isChecked())
                {
                    presenterImplementer.storeComplaintDataToFirebase(mDatabaseReference ,mAuth , mStorageReference
                    ,title , description , "Sanitation" , imageUriList , lat , lng);
                }
                else if(infrastructure.isChecked())
                {
                    presenterImplementer.storeComplaintDataToFirebase(mDatabaseReference ,mAuth , mStorageReference
                            ,title , description , "Infrastructure" , imageUriList ,lat , lng);
                }
                else {
                Toast.makeText(this, "Please select your department", Toast.LENGTH_SHORT).show();
            }


    }

    // add location click
    public void addLocationClick(View view)
    {
      Intent intent = new Intent(AddComplaints.this , MapActivity.class);
      startActivityForResult(intent , 110);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check condition for Gallery image picker request code
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    //add images to arraylist
                    imageUriList.add(clipData.getItemAt(i).getUri());

                    setRecyclerView(imageUriList);
                }
            }
            else
            {
                Uri uri = data.getData();
                imageUriList.add(uri);
                setRecyclerView(imageUriList);
            }
        }


        // Check location result with an OK result
        if (requestCode == 110 && resultCode == RESULT_OK && data != null) {

                // Get String data from Intent
                lat = data.getDoubleExtra("lat" , 0.0);
                lng = data.getDoubleExtra("lng" , 0.0);

                Log.d("ResultLocation", "onCreate: "+lat+"\n"+lng);
        }


    }

    // set select image in recycler view
    private void setRecyclerView(List<Uri> uris)
    {
        mRecyclerView = findViewById(R.id.selectImagesRecyclerList);
        adapter = new AdapterForImagesRecycler(uris , this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this , 3));
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


     private void successDailog() {
            View myView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_dialog_for_complaint , null);
            AlertDialog.Builder alert = new AlertDialog.Builder(AddComplaints.this);
            alert.setView(myView);

            final Dialog dialog = alert.create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            myView.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    startActivity(new Intent(AddComplaints.this , Complaints.class));
                    AddComplaints.this.finish();
                }
            });
        }

    @Override
    public void showProgressBar() {
        progressDialog.setMessage("Uploading please wait...");
        progressDialog.setTitle("Complaint Data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        progressDialog.dismiss();
    }

    @Override
    public void showSuccessDialog()
    {
       successDailog();
    }

    @Override
    public void showMessage(String message)
    { Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearAllFields() {
        editTextTitle.setText("");
        editTextDescription.setText("");
    }

}




