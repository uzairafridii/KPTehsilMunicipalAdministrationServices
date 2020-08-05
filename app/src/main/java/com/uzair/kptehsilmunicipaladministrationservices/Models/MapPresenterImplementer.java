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

    // get search location
    @Override
    public void searchLocation(String location)
    {
        // get the address of search location
        Geocoder geocoder  = new Geocoder(context);

        List<Address> addressList = null;

        try {
            // add the addresses in address list of search location
            addressList = geocoder.getFromLocationName(location+" Lachi Kohat Kpk"  ,1);

            if(addressList.size() > 0)
            {
                // get the lat and lng of search location from address
                Address address = addressList.get(0);
                mapView.getLatLng(address.getLatitude(), address.getLongitude());

            }
            else
            {
                Toast.makeText(context, "Please enter complete address with tehsil and district ", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("locationGeocoder", "searchLocation: "+e.getMessage());
        }
    }
}
