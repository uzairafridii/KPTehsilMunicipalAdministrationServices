package com.uzair.kptehsilmunicipaladministrationservices.Views;

public interface MapView
{

    boolean onCheckPermission();
    void onRequestPermission();
    boolean onGPSEnabled();
    void onGetCurrentLocation();

    void onInflateMap();
    void getLatLng(double lat , double lng);



}
