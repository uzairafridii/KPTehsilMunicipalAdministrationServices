package com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.ModelOfComplaintRecycler;

public class ComplaintModel
{
    private String title , description , status, dateAndTime;
    private int image;

    public ComplaintModel(String title, String description, String status, String dateAndTime, int image) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dateAndTime = dateAndTime;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
