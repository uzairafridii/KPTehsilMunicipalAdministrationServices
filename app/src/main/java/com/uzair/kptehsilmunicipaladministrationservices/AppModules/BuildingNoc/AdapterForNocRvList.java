package com.uzair.kptehsilmunicipaladministrationservices.AppModules.BuildingNoc;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.uzair.kptehsilmunicipaladministrationservices.R;

import java.util.List;

public class AdapterForNocRvList extends RecyclerView.Adapter<AdapterForNocRvList.MyNocHolder>
{
     private Context context;
     private List<NocDataModel> list;

    public AdapterForNocRvList(Context context, List<NocDataModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyNocHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(context).inflate(R.layout.design_for_noc_rv, null);
        MyNocHolder holder = new MyNocHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyNocHolder holder, int position)
    {
       NocDataModel model = list.get(position);
       holder.setTitle(model.getNocTitle());
       holder.setDate(model.getDate());
       holder.setStatus(model.getStatus());
       holder.setImage(model.getImageUrl());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyNocHolder extends RecyclerView.ViewHolder
    {
         private View myView;
         private TextView title , status , date;
         private ImageView nocImage;

        public MyNocHolder(@NonNull View itemView) {
            super(itemView);
            myView = itemView;
        }

        private void setTitle(String titleOfNoc)
        {
            title = myView.findViewById(R.id.title_of_noc_recycler_view);
            title.setText(titleOfNoc);
        }

        private void setStatus(String statusOfNoc)
        {
            status = myView.findViewById(R.id.noc_status);

            if(statusOfNoc.equals("Unregistered"))
            {
                status.setTextColor(Color.RED);
                status.setText(statusOfNoc);
            }
            else
            {
                status.setTextColor(Color.BLUE);
                status.setText(statusOfNoc);
            }


        }

        private void setDate(String dateOfApply)
        {
            date = myView.findViewById(R.id.noc_date_time);
            date.setText(dateOfApply);
        }

        private void setImage(String imageUrl)
        {
           nocImage =  myView.findViewById(R.id.noc_recycler_image_view);
            Glide.with(context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(R.drawable.giphyloading)
                    .into(nocImage);
        }
    }
}
