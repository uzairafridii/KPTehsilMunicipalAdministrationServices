package com.uzair.kptehsilmunicipaladministrationservices.Models;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uzair.kptehsilmunicipaladministrationservices.AppModules.UserComplaint.ComplaintsMain.MapActivity;
import com.uzair.kptehsilmunicipaladministrationservices.Presenters.MapPresenter;
import com.uzair.kptehsilmunicipaladministrationservices.R;
import com.uzair.kptehsilmunicipaladministrationservices.Views.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapPresenterImplementer implements MapPresenter
{
     private MapView mapView;
     private Context context;

    public MapPresenterImplementer(MapView mapView, Context context) {
        this.mapView = mapView;
        this.context = context;
    }

    @Override
    public void initMap() {

        if (mapView.onCheckService()) {

            if (mapView.onGPSEnabled()) {

                if (mapView.onCheckPermission()) {

                    mapView.onInflateMap();

                    mapView.onGetCurrentLocation();

                }
                else {

                   mapView.onRequestPermission();
                }
            }
        }


    }

    @Override
    public void searchLocation(String location)
    {

        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="
                + location + "Lachi Kohat&key=AIzaSyC25fz7R_AYrRD5v6spK89aW9yt2Oiafl4";


        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject json = new JSONObject(response);

                            JSONArray locationResult = json.getJSONArray("results");

                            JSONObject jsonObject = locationResult.getJSONObject(0);

                            // get longitude
                            double lng = jsonObject.getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lng");

                            //get latitude
                            double lat = jsonObject.getJSONObject("geometry").getJSONObject("location")
                                    .getDouble("lat");

                            Log.d("locationResult", "onResponse: "+lng +"\n"+lat);

                            mapView.getLatLng(lat , lng);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("locationError", "onResponse: " + e.getMessage());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onError", "onErrorResponse: " + error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);





    }
}
