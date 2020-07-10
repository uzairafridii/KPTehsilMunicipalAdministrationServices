package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.TaxesDetails.Taxes;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.TaxesDetails.TaxesModel;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.TaxesPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.Views.TaxesView;

import java.util.ArrayList;
import java.util.List;

public class TaxesPresenterImplmenter implements TaxesPresenter
{
    private TaxesView taxesView;
    private List<TaxesModel> list = new ArrayList<>();

    public TaxesPresenterImplmenter(TaxesView taxesView) {
        this.taxesView = taxesView;
    }

    @Override
    public void getTaxesList(DatabaseReference dbRef)
    {
        if(dbRef != null)
        {
            dbRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                {
                    TaxesModel taxesModel = dataSnapshot.getValue(TaxesModel.class);
                    list.add(taxesModel);
                    taxesView.setAdapter(list);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.d("callbackMethodOFCHild", "onChildChanged: ");
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("callbackMethodOFCHild", "onChildRemoved: ");
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }

    }
}
