package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import com.google.firebase.database.DatabaseReference;

public interface TaxesPresenter
{
    void getTaxesList(DatabaseReference dbRef);
}
