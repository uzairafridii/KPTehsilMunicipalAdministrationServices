package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.BottomSheets.CertificateBottomSheetDialog;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates.CertificateModel;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.CertificateMainPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.CertificateMainView;

import java.util.ArrayList;
import java.util.List;

public class CertificateMainPresenterImplementer implements CertificateMainPresenter
{

    private Context context;
    private CertificateMainView mainView;
    private List<CertificateModel> certificateModelList = new ArrayList<>();

    public CertificateMainPresenterImplementer(Context context, CertificateMainView mainView) {
        this.context = context;
        this.mainView = mainView;
    }

    @Override
    public void showBottomSheet() {

        new CertificateBottomSheetDialog().show(((AppCompatActivity)context).getSupportFragmentManager(), "Types");
    }

    @Override
    public void retrieveAllCertificates(DatabaseReference dbRef, FirebaseAuth fbAuth)
    {

        if(dbRef != null && fbAuth !=null)
        {

          Query query = dbRef.orderByChild("uid").equalTo(fbAuth.getCurrentUser().getUid());

          query.addChildEventListener(new ChildEventListener() {
              @Override
              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
              {
                 if(dataSnapshot.hasChildren())
                 {
                     CertificateModel data = dataSnapshot.getValue(CertificateModel.class);
                     certificateModelList.add(data);

                     // send list to recycler adapter
                     mainView.setAdapter(certificateModelList);
                 }
              }

              @Override
              public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

              }

              @Override
              public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

              }

              @Override
              public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });

        }

    }
}
