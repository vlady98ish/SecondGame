package com.example.secondgame.activities;


import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.secondgame.callbacks.CallBack_List;
import com.example.secondgame.R;
import com.example.secondgame.callbacks.CallBack_Map;
import com.example.secondgame.fragments.Fragment_List;
import com.example.secondgame.fragments.Fragment_Map;
import com.example.secondgame.model.ListOfResults;
import com.example.secondgame.utils.MySPV3;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class RecordActivity extends AppCompatActivity {

    private Fragment_List fragment_list;
    private Fragment_Map fragment_map;

    private Button exit;
    private Button menu;


    CallBack_List callBack_userInfo = new CallBack_List() {
        @Override
        public void user(String name) {

        }

        @Override
        public ListOfResults getResults() {
            return new Gson().fromJson(MySPV3.getInstance().getString("records", ""), ListOfResults.class);
        }
//        @Override
//        public void ZoomOnMap(double l, double x){
//            callBack_map.mapClicked(l,x);
//        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        fragment_list = new Fragment_List();
        fragment_map = new Fragment_Map();

        findViews();


        initViews();

        fragment_list.setCallBack_userInfo(callBack_userInfo);
//        fragment_map.setCallBack_Map(callBack_map);

        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_list, fragment_list).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_map, fragment_map).commit();
    }

    private void findViews() {
        menu = findViewById(R.id.record_BTN_menu);
        exit = findViewById(R.id.record_BTN_exit);
    }

    private void initViews() {
        menu.setOnClickListener(view -> changeActivity());
        exit.setOnClickListener(view -> finish());
    }

    private void changeActivity() {

        finish();
        Intent intent = new Intent(RecordActivity.this, StartMenuActivity.class);
        startActivity(intent);
    }


//    CallBack_Map callBack_map = (lat, lon) -> {
//        //Zoom to place
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(googleMap -> {
//            LatLng latLng = new LatLng(lat,lon);
//            googleMap.clear();
//            googleMap.addMarker(new MarkerOptions().position(latLng).title("Played Here!"));
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 5000, null);
//        });
//    };
}
