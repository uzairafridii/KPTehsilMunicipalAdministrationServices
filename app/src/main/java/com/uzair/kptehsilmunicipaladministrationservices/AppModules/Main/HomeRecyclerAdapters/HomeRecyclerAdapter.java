package com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.HomeRecyclerAdapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uzair.kptehsilmunicipaladministrationservices.AppModules.BuildingNoc.BuildingNocActivity;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates.BirthCertificate;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates.CertificatesMain;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.FireFighting.FireFighting;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.MainActivity;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.ModelOfHomeRecycler.HomeModel;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.TaxesDetails.Taxes;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain.Complaints;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserProfile.Profile;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.WaterBills.WaterBills;

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
                    case "Water Bills": {
                        ctx.startActivity(new Intent(ctx , WaterBills.class));
                        break;
                    }
                    case "Profile": {
                        ctx.startActivity(new Intent(ctx , Profile.class));
                        break;
                    }
                    case "Building Noc": {
                        ctx.startActivity(new Intent(ctx , BuildingNocActivity.class));
                        break;
                    }
                    case "Taxes": {
                        ctx.startActivity(new Intent(ctx , Taxes.class));
                        break;
                    }
                    case "Certificates": {
                        ctx.startActivity(new Intent(ctx , CertificatesMain.class));
                        break;
                    }

                    case "Fire Brigade": {
                        FireFighting bottomSheetDialog = FireFighting.newInstance();
                        bottomSheetDialog.show(((AppCompatActivity) ctx).getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
                        break;
                    }

                    case "Exit" :
                    {
                        MainActivity.exitDialog();
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
