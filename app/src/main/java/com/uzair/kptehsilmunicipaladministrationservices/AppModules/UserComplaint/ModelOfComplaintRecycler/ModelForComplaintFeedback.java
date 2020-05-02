package com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ModelOfComplaintRecycler;

import java.util.List;

public class ModelForComplaintFeedback
{
    private String title , completedDate , firstWorker , secondWorker;
    private List<String> imageUrl;

    public ModelForComplaintFeedback() {}

    public ModelForComplaintFeedback(String title, String completedDate,
                                     String firstWorker, String secondWorker,
                                     List<String> imageUrl)
    {
        this.title = title;
        this.completedDate = completedDate;
        this.firstWorker = firstWorker;
        this.secondWorker = secondWorker;
        this.imageUrl = imageUrl;
    }


    public String getTitle() {
        return title;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public String getFirstWorker() {
        return firstWorker;
    }

    public String getSecondWorker() {
        return secondWorker;
    }

    public List<String> getImageUrl() { return imageUrl; }
}
