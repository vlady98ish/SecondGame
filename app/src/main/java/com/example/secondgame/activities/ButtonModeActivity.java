package com.example.secondgame.activities;




import static com.example.secondgame.config.Config.KEY_DELAY;
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

public class ButtonModeActivity extends AppCompatActivity {
    private Button fastMode;
    private Button slowMode;
    private final int FAST_MODE = 500;
    private final int SLOW_MODE = 1000;


    private ImageView background;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_mode);

        findViews();
        initViews();

        MyImageUtils.getInstance().load(START_IMAGE_LINK,background);

    }

    private void findViews() {
        fastMode = findViewById(R.id.buttonMenu_BTN_FastMode);
        slowMode = findViewById(R.id.buttonMenu_BTN_SlowMode);
        background = findViewById(R.id.buttonMenu_IMG_background);
    }

    private void initViews() {
        fastMode.setOnClickListener(view -> changeActivity(FAST_MODE));

        slowMode.setOnClickListener(view -> changeActivity(SLOW_MODE));
    }

    private void changeActivity(int delay) {
        finish();
        Intent intent;

        intent = new Intent(ButtonModeActivity.this, MainActivity.class);
        int mode = getDataFromPrevIntent();
        intent.putExtra(KEY_MODE, mode);
        intent.putExtra(KEY_DELAY, delay);
        startActivity(intent);
    }

    private int getDataFromPrevIntent() {
        Intent prevIntent = getIntent();
        return prevIntent.getExtras().getInt(KEY_MODE);
    }
}
