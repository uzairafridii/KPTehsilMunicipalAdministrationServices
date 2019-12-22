package com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.AdapterOfComplaintRecycler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.ComplaintsMain.UserFeedBack;
import com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.ModelOfComplaintRecycler.ComplaintModel;

import java.util.List;

public class ComplaintRecyclerAdapter  extends RecyclerView.Adapter<ComplaintRecyclerAdapter.MyComplaintViewHolder>
{
     private Context ctx;
     private List<ComplaintModel> complaintModelList;

    public ComplaintRecyclerAdapter(Context ctx, List<ComplaintModel> complaintModelList) {
        this.ctx = ctx;
        this.complaintModelList = complaintModelList;
    }

    @NonNull
    @Override
    public MyComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(ctx).inflate(R.layout.design_for_complaints_recycler_items , null);
        MyComplaintViewHolder holder = new MyComplaintViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyComplaintViewHolder myComplaintViewHolder, int i)
    {
        final ComplaintModel model = complaintModelList.get(i);

        myComplaintViewHolder.setComplaintTitle(model.getTitle());
        myComplaintViewHolder.setComplaintDescription(model.getDescription());
        myComplaintViewHolder.setComplaintTime(model.getDateAndTime());
        myComplaintViewHolder.setComplaintStatus(model.getStatus());
        myComplaintViewHolder.setComplaintImage(model.getImage());

        myComplaintViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent feedBackActivity = new Intent(ctx, UserFeedBack.class);
                feedBackActivity.putExtra("title" , model.getTitle());
                feedBackActivity.putExtra("status" , model.getStatus());
                ctx.startActivity(feedBackActivity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return complaintModelList.size();
    }


    // view holder class for complaint adapter
    public class MyComplaintViewHolder extends RecyclerView.ViewHolder{

        private TextView complaintTitle , complaintDescription, complaintTime , complaintStatus;
        private ImageView complaintImage;
        private View mView;
        private CardView cardView;

        public MyComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            cardView = mView.findViewById(R.id.your_complaints_card_view);
        }

        private void setComplaintTitle(String title)
        {
            complaintTitle = mView.findViewById(R.id.title_of_complaint_recycler_view);
            complaintTitle.setText(title);
        }

        private void setComplaintDescription(String description)
        {
            complaintDescription = mView.findViewById(R.id.desc_of_complaint_recycler_view);
            complaintDescription.setText(description);
        }

        private void setComplaintTime(String time)
        {
            complaintTime = mView.findViewById(R.id.complaint_date_time);
            complaintTime.setText(time);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        private void setComplaintStatus(String status)
        {
            complaintStatus = mView.findViewById(R.id.complaint_status);
            if(status.equals(R.string.pending))
            {
                complaintStatus.setTextColor(ctx.getColor(R.color.pending));
                complaintStatus.setText(status);
            }
            else
            {
                complaintStatus.setTextColor(ctx.getColor(R.color.blue_color));
                complaintStatus.setText(status);
            }

        }

        private void setComplaintImage(int image)
        {
             complaintImage = mView.findViewById(R.id.complaint_recycler_image_view);
             complaintImage.setImageResource(image);
        }


    }
}
