package com.uzair.kptehsilmunicipaladministrationservices.Views;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ModelOfComplaintRecycler.ComplaintModel;

import java.util.List;

public interface ComplaintHomeView
{

    void noItemTextShow();
    void noItemTextHide();

    void setAdapter(List<ComplaintModel> modelList);

    void onShowStatusDialog(String message , int vector);



}
