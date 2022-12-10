package com.example.secondgame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.secondgame.R;
import com.example.secondgame.callbacks.CallBack_Map;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

public class Fragment_Map extends Fragment {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    private CallBack_Map callBack_map;


    public void setCallBack_Map(CallBack_Map callBack_map) {
        this.callBack_map = callBack_map;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map,container,false);
        return mView;
    }


}
