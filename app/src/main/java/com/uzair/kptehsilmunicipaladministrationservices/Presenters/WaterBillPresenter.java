package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import com.google.firebase.database.DatabaseReference;

public interface WaterBillPresenter
{
    // get bill by ref number
    void getUserBill(DatabaseReference databaseReference , String billRefNumber);

    // download bill image
    void downloadBill(String bilImage);



}
