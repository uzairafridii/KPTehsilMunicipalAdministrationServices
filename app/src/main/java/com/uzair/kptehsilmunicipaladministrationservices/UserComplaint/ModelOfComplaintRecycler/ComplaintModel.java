package com.uzair.kptehsilmunicipaladministrationservices.UserComplaint.ModelOfComplaintRecycler;

import java.util.List;

public class ComplaintModel
{
    private String title , description , status, date;
    private List<String> imageUrl;

    public ComplaintModel() {
    }

    public ComplaintModel(String title, String description, String status, String dateAndTime, List<String> image) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = dateAndTime;
        this.imageUrl = image;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
