package com.uzair.kptehsilmunicipaladministrationservices.Views;

import com.uzair.kptehsilmunicipaladministrationservices.AppModules.Main.ModelOfHomeRecycler.HomeModel;

import java.util.List;

public interface MainPageView
{

    void onSetHomeRecyclerAdapter(List<HomeModel> list);

    void closeActivity();


}
