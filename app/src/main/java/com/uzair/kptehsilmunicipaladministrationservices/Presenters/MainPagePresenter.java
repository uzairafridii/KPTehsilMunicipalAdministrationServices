package com.uzair.kptehsilmunicipaladministrationservices.Presenters;

public interface MainPagePresenter
{
    void closingDialog();

    void setHomeRecyclerAdapter();

    boolean isNetworkAvailable();

    void showNoNetworkDialog();

}
