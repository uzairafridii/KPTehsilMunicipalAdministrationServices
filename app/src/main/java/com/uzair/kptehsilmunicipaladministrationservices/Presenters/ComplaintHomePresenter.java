package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public interface ComplaintHomePresenter
{

    void getAllComplaints(DatabaseReference dbRef , FirebaseAuth mAuth);



}
