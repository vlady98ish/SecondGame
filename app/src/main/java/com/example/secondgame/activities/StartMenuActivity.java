package com.example.secondgame.activities;


import static com.example.secondgame.config.Config.START_IMAGE_LINK;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.secondgame.R;
import com.example.secondgame.utils.GPS;
import com.example.secondgame.utils.MyImageUtils;

public class StartMenuActivity extends AppCompatActivity {



    private ImageView background;
    private Button startGame_BTN;

    private Button top10_BTN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startmenu);


        findViews();
        initViews();
        MyImageUtils.getInstance().load(START_IMAGE_LINK,background);
        GPS.getInstance().checkPreferences(this);
    }


    private void initViews() {
        startGame_BTN.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(StartMenuActivity.this, ChooseMenuActivity.class));

        });

        top10_BTN.setOnClickListener(view -> {
            changeActivity();
        });


    }

    private void findViews() {
        startGame_BTN = findViewById(R.id.startMenu_BTN_startGame);
        top10_BTN = findViewById(R.id.startMenu_BTN_top10);
        background = findViewById(R.id.startMenu_IMG_background);

    }

    private void changeActivity() {

        finish();
        Intent intent = new Intent(StartMenuActivity.this, RecordActivity.class);
        startActivity(intent);
    }

}
