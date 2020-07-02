package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain.Complaints;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ModelOfComplaintRecycler.ComplaintModel;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.ComplaintHomePresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.ComplaintHomeView;

import java.util.ArrayList;
import java.util.List;

public class ComplaintHomePresenterImplementer implements ComplaintHomePresenter
{
         private ComplaintHomeView complaintHomeView;
         private List<ComplaintModel> list = new ArrayList<>();
         private int completedComplaints , pendingComplaints , totalComplaints;
         private Context context;

    public ComplaintHomePresenterImplementer(ComplaintHomeView complaintHomeView, Context context) {
        this.complaintHomeView = complaintHomeView;
        this.context = context;
        completedComplaints =0;
        pendingComplaints = 0;
        totalComplaints = 0;
    }


    @Override
    public void getAllComplaints(DatabaseReference dbRef, FirebaseAuth mAuth)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("complaintDetails", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if(dbRef != null && mAuth != null)
        {
           Query query = dbRef.orderByChild("uid").equalTo(mAuth.getCurrentUser().getUid());

           query.addChildEventListener(new ChildEventListener() {
               @Override
               public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
               {

                   ComplaintModel complaints = dataSnapshot.getValue(ComplaintModel.class);

                   if(complaints.getStatus().equals("Pending"))
                   {
                       pendingComplaints = pendingComplaints + 1;
                       totalComplaints = totalComplaints + 1;


                   }else if(complaints.getStatus().equals("Completed"))
                   {
                       completedComplaints = completedComplaints  + 1;
                       totalComplaints = totalComplaints + 1;
                   }

                   editor.putInt("completedComplaints", completedComplaints);
                   editor.putInt("pendingComplaints", pendingComplaints);
                   editor.putInt("totalComplaints", totalComplaints);
                   editor.apply();
                   editor.commit();

                   list.add(complaints);

                   complaintHomeView.setAdapter(list);
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
