package com.uzair.kptehsilmunicipaladministrationservices.Main.HomeRecyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uzair.kptehsilmunicipaladministrationservices.Main.ModelOfHomeRecycler.HomeModel;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.ComplaintsMain.Complaints;

import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyHomeViewHolder>
{
    // declare variables
    private Context ctx;
    private List<HomeModel> mHomeServiceList;

    // constructor of homerecycleradapter class
    public HomeRecyclerAdapter(Context ctx, List<HomeModel> mHomeServiceList) {
        this.ctx = ctx;
        this.mHomeServiceList = mHomeServiceList;
    }

    @NonNull
    @Override
    public MyHomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(ctx).inflate(R.layout.home_recycler_design , null);

        MyHomeViewHolder homeViewHolder = new MyHomeViewHolder(view);

        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHomeViewHolder myHomeViewHolder, int i)
    {
        final HomeModel model = mHomeServiceList.get(i);

        myHomeViewHolder.setTitle(model.getNameOfService());
        myHomeViewHolder.setImage(model.getImageOfService());

        myHomeViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (model.getNameOfService())
                {
                    case "Complaints": {
                                   ctx.startActivity(new Intent(ctx , Complaints.class));
                                   break;
                    }
                    default:{
                        break;
                    }
                }


            }
        });





    }

    @Override
    public int getItemCount() {
        return mHomeServiceList.size();
    }



    // recycler view holder class
    public class MyHomeViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private ImageView image;
        private View mView;
        private CardView mCardView;

        public MyHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mCardView = mView.findViewById(R.id.home_card_view);
        }

        // set title of service in home recycler view
        private void setTitle(String serviceTitle)
        {
            title = mView.findViewById(R.id.home_recycler_txt_view);
            title.setText(serviceTitle);
        }

        // set image of service in home recycler view
        private void setImage(int imageResource)
        {
            image = mView.findViewById(R.id.home_recycler_image_view);
            image.setImageResource(imageResource);
        }
    }
}
