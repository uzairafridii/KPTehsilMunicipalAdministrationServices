package com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.uzair.kptehsilmunicipaladministrationservices.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class AddComplaints extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    private static int counter  = 0;
    private Toolbar mToolbar;
    private EditText editTextTitle, editTextDescription;
    private RadioButton sanitation, infrastructure;
    private RecyclerView mRecyclerView;
    private ProgressDialog progressDialog;

    private AdapterForImagesRecycler adapter;

    private List<String> imageUrls;
    private List<Uri> imageUriList;
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
        //  app tool bar
        mToolbar = findViewById(R.id.addComplain_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("Add Complaint");

        // tool bar back arrow enabled
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
        imageUrls  = new ArrayList<>();


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
        progressDialog.setMessage("Uploading please wait...");
        progressDialog.setTitle("Complaint Data");
        progressDialog.setCanceledOnTouchOutside(false);


        if (!title.isEmpty() && !description.isEmpty() && imageUriList.size() != 0) {

                if(sanitation.isChecked())
                {
                    progressDialog.show();
                    Toast.makeText(this, "Sanitation is selected", Toast.LENGTH_SHORT).show();
                    createUrlsForImages(title , description, "sanitation");
                }
                else if(infrastructure.isChecked())
                {
                    progressDialog.show();
                    Toast.makeText(this, "Infrastructure one is selected", Toast.LENGTH_SHORT).show();
                    createUrlsForImages(title , description, "infrastructure");
                }
                else {
                Toast.makeText(this, "Please select your any department", Toast.LENGTH_SHORT).show();
            }

        } else {
            editTextTitle.setError("Required");
            editTextDescription.setError("Required");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    //add images to arralist
                    imageUriList.add(clipData.getItemAt(i).getUri());

                    setRecyclerView(imageUriList);
                }
            }
            else
            {
                Uri uri = data.getData();
                imageUriList.add(uri);
            }
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

    // method to create download urls for images
    private void createUrlsForImages(final String title , final String description , final String field) {
        int uploads;
        for (uploads = 0; uploads < imageUriList.size(); uploads++) {

            Uri Image = imageUriList.get(uploads);

            // storage reference to add images
            final StorageReference imagename = mStorageReference.child("ComplaintsImages").child("image/" + Image.getLastPathSegment());
            
            imagename.putFile(imageUriList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // to get download url
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            counter = counter + 1;
                            String url = String.valueOf(uri);
                            imageUrls.add(url);

                            if(counter == imageUriList.size()) {
                                // add urls and data to firebase
                                addDataToFirebase(imageUrls, title, description, field);
                            }

                        }
                    });
                }
            });
        }


    }


    // method to add data to firebase database
    private void addDataToFirebase(List<String> urls, String titleOfComp , String desc , String fieldOfComplaint /*, int count*/)
    {
        // if arraylist conatain all urls then upload the data to database
      //  if(count == imageUriList.size()) {
        String date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            Map dataOfComplaint = new HashMap<>();
            dataOfComplaint.put("imageUrl", urls);
            dataOfComplaint.put("date", date);
            dataOfComplaint.put("title", titleOfComp);
            dataOfComplaint.put("description", desc);
            dataOfComplaint.put("status","Pending");
            dataOfComplaint.put("field", fieldOfComplaint);
            mDatabaseReference.child(mAuth.getCurrentUser().getUid()).push().setValue(dataOfComplaint)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(AddComplaints.this, "SuccessFully Added", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();

                                View myView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_dialog_for_complaint , null);
                                AlertDialog.Builder alert = new AlertDialog.Builder(AddComplaints.this);
                                alert.setView(myView);

                                final AlertDialog dialog = alert.create();
                                dialog.setCancelable(false);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.show();

                                myView.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });


                            } else {
                                Toast.makeText(AddComplaints.this, "Error in uploading", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        }
                    });
        }
   // }







}




