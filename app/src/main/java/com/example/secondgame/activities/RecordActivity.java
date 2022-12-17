package com.example.secondgame.activities;

import static com.example.secondgame.config.Config.IMAGE_LINK;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.secondgame.DataManager;
import com.example.secondgame.callbacks.CallBack_List;
import com.example.secondgame.R;

import com.example.secondgame.fragments.Fragment_List;
import com.example.secondgame.model.ListOfResults;
import com.example.secondgame.model.Result;
import com.example.secondgame.utils.MyImageUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RecordActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Fragment_List fragment_list;


    private Button exit;
    private Button menu;

    private SupportMapFragment mapFragment;
    private ImageView background;

    CallBack_List callBack_userInfo = DataManager::getTopTenResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        fragment_list = new Fragment_List();


        findViews();


        initViews();

        fragment_list.setCallBack_userInfo(callBack_userInfo);

        MyImageUtils.getInstance().load(IMAGE_LINK,background);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.panel_LAY_list, fragment_list)
                .commit();

    }

    private void findViews() {
        menu = findViewById(R.id.record_BTN_menu);
        exit = findViewById(R.id.record_BTN_exit);
        background = findViewById(R.id.record_IMG_background);
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.clear();
        ListOfResults top10 = DataManager.getTopTenResults();
        if (top10 != null) {
            for (int i = 0; i < top10.size(); i++) {
                Result result = top10.get(i);

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(
                                result.getX(),
                                result.getY()))
                        .title("" + i));
            }
        }
    }


}
