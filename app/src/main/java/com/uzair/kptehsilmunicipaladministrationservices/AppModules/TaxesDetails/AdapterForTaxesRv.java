package com.uzair.kptehsilmunicipaladministrationservices.AppModules.TaxesDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.TaxesView;

import java.util.List;

public class AdapterForTaxesRv extends RecyclerView.Adapter<AdapterForTaxesRv.MyTaxesViewHolder> {
    private List<TaxesModel> taxesModelList;
    private Context context;
    private TaxesView taxesView;

    public AdapterForTaxesRv(List<TaxesModel> taxesModelList, Context context, TaxesView taxesView) {
        this.taxesModelList = taxesModelList;
        this.context = context;
        this.taxesView = taxesView;
    }

    @NonNull
    @Override
    public MyTaxesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taxView = LayoutInflater.from(context).inflate(R.layout.design_for_taxes_rv, null);
        return new MyTaxesViewHolder(taxView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTaxesViewHolder holder, int position)
    {
        TaxesModel  model = taxesModelList.get(position);
        holder.title.setText(model.getTaxTitle());
        holder.amount.setText("PKR "+model.getTaxAmount());
        holder.setTaxLogo(model.getTaxImage());


    }

    @Override
    public int getItemCount() {
        if(taxesModelList.size() > 0)
        {
            taxesView.hideNotFoundLayout();
            return taxesModelList.size();
        }
        else
        {
            taxesView.showNotFoundLayout();
            return taxesModelList.size();
        }

    }

    public class MyTaxesViewHolder extends RecyclerView.ViewHolder {
        private TextView title, amount;
        private ImageView logo;
        private View mView;

        public MyTaxesViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            title = mView.findViewById(R.id.taxTitle);
            amount = mView.findViewById(R.id.taxAmount);
        }

        private void setTaxLogo(String logoUrl) {
            logo = mView.findViewById(R.id.taxLogo);
            Glide.with(context)
                    .load(logoUrl)
                    .into(logo);
        }
    }
}
