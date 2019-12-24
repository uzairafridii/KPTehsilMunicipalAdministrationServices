package com.uzair.kptehsilmunicipaladministrationservices.FireFighting;


import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.uzair.kptehsilmunicipaladministrationservices.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FireFighting extends BottomSheetDialogFragment {


    public FireFighting() {
        // Required empty public constructor
    }

    public static FireFighting newInstance() {
        FireFighting fragment = new FireFighting();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_fire_fighting, container, false);

        return myView;
    }


}
