package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;

public interface UserFeedBackPresenter
{
    void getCompletedWorkDetails(DatabaseReference dbRef , String complaintKey);

    void addFirstWorkerRating(DatabaseReference databaseRef, String rating,String comment,String firstWorkerName,
                              String uid, Uri imageUrl , String complaintKey , String complaintType);

    void addSecondWorkerRating(DatabaseReference databaseRef, String rating, String comment , String secondWorkerName,
                               String uid, Uri imageUrl, String complaintKey,String complaintType);

}
