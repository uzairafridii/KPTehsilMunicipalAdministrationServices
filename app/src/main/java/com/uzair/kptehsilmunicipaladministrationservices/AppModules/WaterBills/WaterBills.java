package com.uzair.kptehsilmunicipaladministrationservices.AppModules.WaterBills;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uzair.kptehsilmunicipaladministrationservices.Models.WaterBillPresenterImplmenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.WaterBillView;

public class WaterBills extends AppCompatActivity implements WaterBillView
{
    public static final int PERMISSION_CODE = 10 ;
    private String imageDownloadUrl = null;
    private Toolbar mToolbar;
    private TextView ownerName , connectionDate , connectionLocation;
    private ImageView billImage;
    private EditText billRefNumber;
    private Button downloadBillBtn;
    private DatabaseReference mDatabaseRef;
    private ProgressDialog progressDialog;
    private WaterBillPresenterImplmenter presenterImplmenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_bills);
        initViews();

    }

    private void initViews()
    {
        // implementer
        presenterImplmenter = new WaterBillPresenterImplmenter(WaterBills.this , this);

        //  app tool bar
        mToolbar = findViewById(R.id.water_bills_tool_bar);
        setSupportActionBar(mToolbar);
        setTitle("Water Bills");

        // tool bar back arrow enabled
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);


        // all Views
        ownerName = findViewById(R.id.ownerNameValue);
        connectionDate = findViewById(R.id.connectionDateValue);
        connectionLocation = findViewById(R.id.connectionLocationValue);

        billRefNumber = findViewById(R.id.searchText);
        billImage = findViewById(R.id.user_bill);

        downloadBillBtn = findViewById(R.id.download_btn);

        progressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);

        // firebase ref
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Water_Bills");


    }


    // search button click
    public void searchBtnClick(View view)
    {
        String refNum = billRefNumber.getText().toString();

        presenterImplmenter.getUserBill(mDatabaseRef , refNum);
    }

    // download button click
    public void downloadBillBtnClick(View view) {

        presenterImplmenter.downloadBill(imageDownloadUrl);
    }


    // load firebase data callbacks
    @Override
    public void showProgressDialog() {

        progressDialog.setMessage("Pleas wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {

        progressDialog.dismiss();
    }

    @Override
    public void getUserRecord(String name, String date, String location, String image) {

        imageDownloadUrl = image;
        ownerName.setVisibility(View.VISIBLE);
        connectionLocation.setVisibility(View.VISIBLE);
        connectionDate.setVisibility(View.VISIBLE);
        downloadBillBtn.setVisibility(View.VISIBLE);

        ownerName.setText(name);
        connectionDate.setText(date);
        connectionLocation.setText(location);
        billImage.getLayoutParams().height = 550;

        Glide.with(this)
                .load(imageDownloadUrl)
                .into(billImage);
    }


    // check permission
    @Override
    public boolean checkStoragePermission() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }

    @Override
    public void requestStoragePermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_CODE
        );
    }


    // clear  search field
    @Override
    public void clearAllFields() {

        billRefNumber.setText("");
    }

    // show error message
    @Override
    public void showErrorMessage(String error) {

        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

}
