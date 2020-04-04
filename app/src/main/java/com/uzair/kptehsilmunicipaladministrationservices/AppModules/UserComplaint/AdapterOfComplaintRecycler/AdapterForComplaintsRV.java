package com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.AdapterOfComplaintRecycler;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain.Complaints;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ModelOfComplaintRecycler.ComplaintModel;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.ComplaintHomeView;

import java.util.List;

public class AdapterForComplaintsRV extends RecyclerView.Adapter<AdapterForComplaintsRV.MyComplaintHolder>
{
    private Context context;
    private List<ComplaintModel> list;
    private ComplaintHomeView view;

    public AdapterForComplaintsRV(Context context, List<ComplaintModel> list , ComplaintHomeView view) {
        this.context = context;
        this.list = list;
        this.view = view;
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
    public void onBindViewHolder(@NonNull MyComplaintHolder myComplaintHolder, int position)
    {
        // binding data in views
         ComplaintModel complaintModel = list.get(position);

        myComplaintHolder.setComplaintTitle(complaintModel.getTitle());
        myComplaintHolder.setComplaintDescription(complaintModel.getDescription());
        myComplaintHolder.setComplaintTime(complaintModel.getDate());
        myComplaintHolder.setComplaintStatus(complaintModel.getStatus());
        myComplaintHolder.setComplaintImage(complaintModel.getImageUrl());
    }

    @Override
    public int getItemCount() {
        if(list.size() > 0)
        {
             view.noItemTextHide();
            return list.size();
        }
        else
        {
            view.noItemTextShow();
            return 0;
        }

    }

    // view holder class
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

        @RequiresApi(api = Build.VERSION_CODES.M)
        private void setComplaintStatus(String status) {
            complaintStatus = mView.findViewById(R.id.complaint_status);
            if (status.equals("Pending")) {
                complaintStatus.setTextColor(Color.RED);
                complaintStatus.setText(status);
            } else if(status.equals("In Progress")) {
                complaintStatus.setTextColor(Color.GREEN);
                complaintStatus.setText(status);
            }
            else {
                complaintStatus.setTextColor(Color.BLUE);
                complaintStatus.setText(status);
            }

        }

        private void setComplaintImage(List<String> imageUrl) {

            complaintImage = mView.findViewById(R.id.complaint_recycler_image_view);

            for (int i = 0; i < imageUrl.size(); i++) {
                Glide.with(context)
                        .load(imageUrl.get(0))
                        .placeholder(R.drawable.giphyloading)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .into(complaintImage);

            }


        }

    }

}
