package com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.AdapterOfComplaintRecycler;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain.Complaints;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain.UserFeedBack;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ModelOfComplaintRecycler.ComplaintModel;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.ComplaintHomeView;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.MyComplaintHolder>
{
    private Context context;
    private List<ComplaintModel> list;
    private ComplaintHomeView complaintHomeView;
    private  String isAddFeedback;

    public ComplaintAdapter(Context context, List<ComplaintModel> list , ComplaintHomeView complaintHomeView) {
        this.context = context;
        this.list = list;
        this.complaintHomeView = complaintHomeView;
    }

    @NonNull
    @Override
    public MyComplaintHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // link the layout design
        View view = LayoutInflater.from(context)
                .inflate(R.layout.design_for_complaints_recycler_items, null);


        return new MyComplaintHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyComplaintHolder myComplaintHolder, int position)
    {
        // binding data in views
         final ComplaintModel complaintModel = list.get(position);

        myComplaintHolder.setComplaintTitle(complaintModel.getTitle());
        myComplaintHolder.setComplaintDescription(complaintModel.getDescription());
        myComplaintHolder.setComplaintTime(complaintModel.getDate());
        myComplaintHolder.setComplaintStatus(complaintModel.getStatus());
        myComplaintHolder.setComplaintImage(complaintModel.getImageUrl());

        myComplaintHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(complaintModel.getStatus().equals("Pending"))
                {
                    successDailog("Ooops!! your complaint is in Pending");
                }
                else
                {
                    checkUserAddRatingOrNot(complaintModel.getPushKey(), complaintModel.getField() , complaintModel.getUid());
                }

            }

        });

    }

    @Override
    public int getItemCount() {
        if(list.size() > 0)
        {
             complaintHomeView.noItemTextHide();
            return list.size();
        }
        else
        {
            complaintHomeView.noItemTextShow();
            return 0;
        }

    }

    // complaintHomeView holder class
    public class MyComplaintHolder extends RecyclerView.ViewHolder {
        private TextView complaintTitle, complaintDescription, complaintTime, complaintStatus;
        private ImageView complaintImage;
        private View mView;
        private CardView cardView;

        public MyComplaintHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            cardView = mView.findViewById(R.id.your_complaints_card_view);
        }

        private void setComplaintTitle(String title) {
            complaintTitle = mView.findViewById(R.id.title_of_complaint_recycler_view);
            complaintTitle.setText(title);
        }

        private void setComplaintDescription(String description) {
            complaintDescription = mView.findViewById(R.id.desc_of_complaint_recycler_view);
            complaintDescription.setText(description);
        }

        private void setComplaintTime(String time) {
            complaintTime = mView.findViewById(R.id.complaint_date_time);
            complaintTime.setText(time);
        }

        @SuppressLint("ResourceAsColor")
        @RequiresApi(api = Build.VERSION_CODES.M)
        private void setComplaintStatus(String status)
        {
            complaintStatus = mView.findViewById(R.id.complaint_status);
            if (status.equals("Pending"))
            {
                complaintStatus.setTextColor(Color.RED);
                complaintStatus.setText(status);
            }
            else {
                complaintStatus.setTextColor(Color.BLUE);
                complaintStatus.setText(status);
            }

        }

        private void setComplaintImage(List<String> imageUrl) {

            complaintImage = mView.findViewById(R.id.complaint_recycler_image_view);

                Glide.with(context)
                        .load(imageUrl.get(0))
                        .placeholder(R.drawable.giphyloading)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .into(complaintImage);


        }

    }



    // check if user have already add feebfack then he will not add feedback again
    private void checkUserAddRatingOrNot(final String complaintKey, final String field, String currentUserId)
    {
        // check feedback is add or not
        // if added then user not able to add another feedback
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference()
                                  .child("RatingValidation").child(currentUserId).child(complaintKey);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // get the value whether its
                if(dataSnapshot.hasChildren()) {
                    isAddFeedback = dataSnapshot.child(complaintKey).getValue().toString();
                    Log.d("isAddFeedback", "onChildAdded: " + isAddFeedback);

                    if (isAddFeedback.equals("true")) {
                        Toast.makeText(context, "Already added feedback", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Intent intent = new Intent(context, UserFeedBack.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("pushKey", complaintKey);
                    intent.putExtra("complaintType", field);
                    context.startActivity(intent);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


    }


    private void successDailog(String message) {
        View myView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(myView);


        final Dialog dialog = alert.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        TextView textView = myView.findViewById(R.id.message);
        textView.setText(message);

        myView.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


}
