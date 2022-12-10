package com.example.secondgame.activities;



import android.content.Intent;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;



import com.example.secondgame.GameManager;
import com.example.secondgame.R;
import com.example.secondgame.model.Type;
import com.example.secondgame.callbacks.SensorDetector;
import com.example.secondgame.model.Item;
import com.example.secondgame.utils.GPS;
import com.example.secondgame.utils.MySignal;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    final int ROW_COUNT = 10;
    final int COLUMN_COUNT = 5;
    private int mode = 1;
    private AppCompatImageView[] main_IMG_hearts;


    private MediaPlayer backgroundMusic;
    private MediaPlayer crashSound;

    private AppCompatImageView[][] main_MATRIX_bombs;
    private AppCompatImageView[] main_IMG_planes;


    private ExtendedFloatingActionButton main_BTN_left;
    private ExtendedFloatingActionButton main_BTN_right;


    public static final String KEY_LEFT = "KEY_LEFT";
    public static final String KEY_RIGHT = "KEY_RIGHT";
    public final String GAME_OVER = "GAME OVER";
    public final String LOST_LIFE = "You lost 1 life";
    public static final String KEY_MODE = "KEY_MODE";


    final int DELAY = 1000;

    GameManager gameManager;

    SensorDetector sensorDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        gameManager = new GameManager(main_IMG_hearts.length, ROW_COUNT, COLUMN_COUNT);

        sensorDetector = new SensorDetector(this, callBack_move);
        initViews();
        initBackgroundMusic();
        getDataFromPrevIntent();

        GPS.init(MainActivity.this);

    }

    private void getDataFromPrevIntent() {
        Intent prevIntent = getIntent();
        this.mode = prevIntent.getExtras().getInt(KEY_MODE);
    }

    private SensorDetector.CallBack_move callBack_move = new SensorDetector.CallBack_move() {
        @Override
        public void move(int direction) {

            if (direction == 1 && gameManager.move(KEY_LEFT)) {
                visibility(direction);
            } else if (direction == -1 && gameManager.move(KEY_RIGHT)) {
                visibility(direction);
            }


        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();

        GPS.getInstance().start();

        backgroundMusic.start();
        if (mode == 1) {
            sensorDetector.start();
            main_BTN_left.setVisibility(View.INVISIBLE);
            main_BTN_right.setVisibility(View.INVISIBLE);

        } else {
            main_BTN_left.setVisibility(View.VISIBLE);
            main_BTN_right.setVisibility(View.VISIBLE);

        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();

        GPS.getInstance().stop();

        backgroundMusic.pause();
        if (mode == 1) {
            sensorDetector.stop();
        }


    }


    private Timer timer = new Timer();

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> updateTimeUI());
            }
        }, DELAY, DELAY);
    }

    private void stopTimer() {
        timer.cancel();
    }


    private void updateTimeUI() {
        if (gameManager.isEndGame()) {
            gameOver();
        } else {
            updateUI();
        }
    }

    private void updateUI() {
        gameManager.updateTable();
        if (gameManager.checkIsWrong()) {
            renderHearts();
            toast(LOST_LIFE);
            crashSound = MediaPlayer.create(MainActivity.this, R.raw.crash_sound);
            crashSound.start();
            MySignal.getInstance().vibrate();
        }

        renderMatrix(gameManager.getMatrix());
    }

    private void gameOver() {
        toast(GAME_OVER);
        changeActivity();
        saveResults();

    }

    private void saveResults() {
        gameManager.saveResults();
    }


    private void changeActivity() {

        finish();
        Intent intent = new Intent(MainActivity.this, RecordActivity.class);
        startActivity(intent);
    }

    private void toast(String text) {
        MySignal.getInstance().toast(text);
    }


    /*RENDERING FUNCTIONS */
    private void renderMatrix(Item[][] items) {
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                Type type = items[i][j].getType();
                if (type == Type.VISIBLE) {
                    items[i][j].getImage().setVisibility(View.VISIBLE);
                } else {
                    items[i][j].getImage().setVisibility(View.INVISIBLE);
                }

            }
        }
    }

    private void renderHearts() {
        int index = gameManager.getWrong() - 1;
        main_IMG_hearts[index].setVisibility(View.INVISIBLE);

    }
    /*RENDERING FUNCTIONS */

    /*INIT FUNCTIONS*/
    private void initMatrixBombs() {
        main_MATRIX_bombs = new AppCompatImageView[ROW_COUNT][COLUMN_COUNT];
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                String name = "main_IMG_bomb_" + (i + 1) + "_" + (j + 1);
                System.out.println(name);
                int id = getResources().getIdentifier(name, "id", getPackageName());
                AppCompatImageView bomb = findViewById(id);
                main_MATRIX_bombs[i][j] = bomb;
            }
        }
    }

    private void initViews() {
        main_BTN_left.setOnClickListener(view -> {
            if (gameManager.move(KEY_LEFT)) {
                visibility(1);
            }


        });

        main_BTN_right.setOnClickListener(view -> {
            if (gameManager.move(KEY_RIGHT)) {
                visibility(-1);
            }
        });

        gameManager.initMatrix(main_MATRIX_bombs);
    }
    /*INIT FUNCTIONS*/

    private void visibility(int direction) {
        int place = gameManager.getCurrentPlace();
        main_IMG_planes[place].setVisibility(View.VISIBLE);
        System.out.println(place + "," + direction);
        main_IMG_planes[place + direction].setVisibility(View.INVISIBLE);
    }


    /*All FIND FUNCTIONS */
    private void findHearts() {
        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3),
        };
    }

    private void findPlanes() {
        main_IMG_planes = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_plane1),
                findViewById(R.id.main_IMG_plane2),
                findViewById(R.id.main_IMG_plane3),
                findViewById(R.id.main_IMG_plane4),
                findViewById(R.id.main_IMG_plane5)
        };
    }

    private void findButtons() {
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
    }

    private void findViews() {
        findHearts();
        findPlanes();
        findButtons();


        initMatrixBombs();


    }
    /*All FIND FUNCTIONS */

    private void initBackgroundMusic() {
        backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.star_wars_imperial_march);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.4f, 0.4f);
    }


    /*----------------------------------------------------------------------LOCATION ----------------------------------------------------------------------*/


}