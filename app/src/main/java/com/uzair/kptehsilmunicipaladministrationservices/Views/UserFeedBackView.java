package com.uzair.kptehsilmunicipaladministrationservices.Views;

import java.util.List;

public interface UserFeedBackView
{
    void showProgressBar();

    void hideProgressBar();

    void showMessage(String message, String type);

    void onSetDataInTextViews(String title, String firstWorker, String secondWorker, List<String> imageUrl, String date);

}
