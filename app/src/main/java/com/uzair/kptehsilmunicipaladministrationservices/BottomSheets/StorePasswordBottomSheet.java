package com.uzair.kptehsilmunicipaladministrationservices.BottomSheets;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uzair.kptehsilmunicipaladministrationservices.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StorePasswordBottomSheet extends Fragment {


    public StorePasswordBottomSheet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_password_bottom_sheet, container, false);
    }

}
