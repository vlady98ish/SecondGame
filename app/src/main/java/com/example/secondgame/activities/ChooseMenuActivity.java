package com.example.secondgame.activities;



import static com.example.secondgame.config.Config.KEY_MODE;
import static com.example.secondgame.config.Config.START_IMAGE_LINK;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.secondgame.R;
import com.example.secondgame.utils.MyImageUtils;

public class ChooseMenuActivity extends AppCompatActivity {

    private Button sensorMode;
    private Button buttonMode;
    private ImageView background;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_choose_menu);

        findViews();
        initViews();

        MyImageUtils.getInstance().load(START_IMAGE_LINK,background);

    }

    private void findViews() {
        sensorMode = findViewById(R.id.chooseMenu_BTN_SensorMode);
        buttonMode = findViewById(R.id.chooseMenu_BTN_ButtonMode);
        background = findViewById(R.id.chooseMenu_IMG_background);
    }

    private void initViews() {
        sensorMode.setOnClickListener(view -> changeActivity(1));

        buttonMode.setOnClickListener(view -> changeActivity(0));
    }

    private void changeActivity(int mode) {
        finish();
        Intent intent;

        intent = new Intent(ChooseMenuActivity.this, MainActivity.class);
        if (mode == 0) {
            intent = new Intent(ChooseMenuActivity.this, ButtonModeActivity.class);
        }
        intent.putExtra(KEY_MODE, mode);
        startActivity(intent);
        }

    }


