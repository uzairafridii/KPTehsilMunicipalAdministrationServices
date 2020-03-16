package com.uzair.kptehsilmunicipaladministrationservices.AppModules.BuildingNoc;

public class NocDataModel
{
    private String nocTitle , imageUrl , status , date ;

    public NocDataModel(String nocTitle, String imageUrl, String status, String date) {
        this.nocTitle = nocTitle;
        this.imageUrl = imageUrl;
        this.status = status;
        this.date = date;
    }

    public NocDataModel(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNocTitle() {
        return nocTitle;
    }

    public void setNocTitle(String nocTitle) {
        this.nocTitle = nocTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
