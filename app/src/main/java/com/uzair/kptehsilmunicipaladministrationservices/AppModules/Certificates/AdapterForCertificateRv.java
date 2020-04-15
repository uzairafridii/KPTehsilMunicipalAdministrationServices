package com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uzair.kptehsilmunicipaladministrationservices.R;

import java.util.List;

public class AdapterForCertificateRv extends RecyclerView.Adapter<AdapterForCertificateRv.MyCertiicateViewHolder>
{

    private Context context;
    private List<CertificateModel> certificateModelList;

    public AdapterForCertificateRv(Context context, List<CertificateModel> certificateModelList) {
        this.context = context;
        this.certificateModelList = certificateModelList;
    }

    @NonNull
    @Override
    public MyCertiicateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.design_for_certificate_rv, null);

        return new MyCertiicateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCertiicateViewHolder holder, int position)
    {

        CertificateModel model = certificateModelList.get(position);

        holder.setApplicantName(model.getApplicantName());
        holder.setType(model.getCertificateType());
        holder.setStatus(model.getStatus());
        holder.setDate(model.getDate());

    }

    @Override
    public int getItemCount() {
        return certificateModelList.size();
    }

    public class MyCertiicateViewHolder extends RecyclerView.ViewHolder
    {
        private TextView applicantName , certificateType, status , date;
        private View mView;

        public MyCertiicateViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        private void setApplicantName(String name)
        {
            applicantName = mView.findViewById(R.id.appliNameValue);
            applicantName.setText(name);
        }

        private void setType(String type)
        {
            certificateType = mView.findViewById(R.id.certificateTypeValue);
            certificateType.setText(type);
        }

        private void setStatus(String certificateStatus)
        {
            status = mView.findViewById(R.id.statusValue);

            if(certificateStatus.equals("Pending"))
            {
                status.setText(certificateStatus);
                status.setTextColor(Color.RED);
            }
            else {
                status.setText(certificateStatus);
                status.setTextColor(Color.BLUE);
            }
        }


        private void setDate(String dateOfApply)
        {
            date = mView.findViewById(R.id.dateValue);
            date.setText(dateOfApply);
        }

    }
}
