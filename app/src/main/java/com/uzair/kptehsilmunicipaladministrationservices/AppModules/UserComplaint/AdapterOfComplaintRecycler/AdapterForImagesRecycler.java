package com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.AdapterOfComplaintRecycler;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uzair.kptehsilmunicipaladministrationservices.R;

import java.util.List;

public class AdapterForImagesRecycler extends RecyclerView.Adapter<AdapterForImagesRecycler.MyViewHolder>
{
    private List<Uri> imagesList;
    private Context context;

    public AdapterForImagesRecycler(List<Uri> imagesList, Context context) {
        this.imagesList = imagesList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterForImagesRecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(context).inflate(R.layout.design_for_images_recycler , null);
        MyViewHolder  holder = new MyViewHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterForImagesRecycler.MyViewHolder holder, final int position)
    {
        final Uri model = imagesList.get(position);

        holder.setImage(model);
        holder.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imagesList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,imagesList.size());

            }
        });

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView image;
        private ImageButton closeBtn;
        private View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            closeBtn = mView.findViewById(R.id.imageExitButton);
        }

        private void setImage(Uri imageUri)
        {
            image = mView.findViewById(R.id.selectedImages);
            image.setImageURI(imageUri);
        }
    }
}
