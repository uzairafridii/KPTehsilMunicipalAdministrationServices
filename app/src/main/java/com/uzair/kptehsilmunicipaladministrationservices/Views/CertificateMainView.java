package com.uzair.kptehsilmunicipaladministrationservices.Views;

import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates.CertificateModel;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ModelOfComplaintRecycler.ComplaintModel;

import java.util.List;

public interface CertificateMainView
{

    void setAdapter(List<CertificateModel> modelList);

}
