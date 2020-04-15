package com.uzair.kptehsilmunicipaladministrationservices.AppModules.Certificates;

public class CertificateModel
{
    private String applicantName , certificateType , status, date ;

    public CertificateModel(String applicantName, String certificateType, String status, String date) {
        this.applicantName = applicantName;
        this.certificateType = certificateType;
        this.status = status;
        this.date = date;
    }

    public CertificateModel() {}

    public String getApplicantName() {
        return applicantName;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}
