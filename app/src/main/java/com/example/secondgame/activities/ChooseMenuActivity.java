package com.example.secondgame.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.secondgame.R;

public class ChooseMenuActivity extends AppCompatActivity {

    private Button sensorMode;
    private Button buttonMode;
    private final String KEY_MODE = "KEY_MODE";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_choose_menu);

        findViews();
        initViews();

    }

    private void findViews() {
        sensorMode = findViewById(R.id.chooseMenu_BTN_SensorMode);
        buttonMode = findViewById(R.id.chooseMenu_BTN_ButtonMode);
    }

    private void initViews() {
        sensorMode.setOnClickListener(view -> changeActivity(1));

        buttonMode.setOnClickListener(view -> changeActivity(0));
    }

    private void changeActivity(int mode) {
        finish();
        Intent intent = new Intent(ChooseMenuActivity.this, MainActivity.class);
        intent.putExtra(KEY_MODE, mode);
        startActivity(intent);
    }
}
