package com.autotest.pdd_test.activities;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.autotest.pdd_test.R;
import com.autotest.pdd_test.activities.testActivity.SimpleActivityTest;
import com.autotest.pdd_test.databases.QuizesResultDao;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.io.IOException;



public class TicketsSheetActivity extends AppCompatActivity {

    private static final int NUMBER_OF_COLUMNS = 5;
    public static final int NUM_OF_QUIZS = 25;
    private static final int NUMBER_OF_ROWS = NUM_OF_QUIZS / NUMBER_OF_COLUMNS;
    public static final String QUIZ_NUMBER = "quiz_number";
    private QuizesResultDao quizResultDao = new QuizesResultDao(this);
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    private int mSound;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView title = (TextView) findViewById(R.id.toolbar_text);
        title.setText(getString(R.string.quizes));
        createQuizes();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        createQuizes();
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mSound = loadSound("sound.ogg");

    }

    private void createQuizes() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.quizes);
        tableLayout.removeAllViews();
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            tableRow.setGravity(Gravity.CENTER);
            for (int column = 1; column <= NUMBER_OF_COLUMNS; column++) {
                tableRow.addView(createButton(row * NUMBER_OF_COLUMNS + column));
            }
            tableLayout.addView(tableRow, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private Button createButton(int number) {
        Button button = new Button(this);
        button.setText(String.valueOf(number));
        button.setTextSize(20);
        button.setLayoutParams(new TableRow.LayoutParams(getWindowWidth() / NUMBER_OF_COLUMNS, ViewGroup.LayoutParams.MATCH_PARENT));
        button.setGravity(Gravity.CENTER);
        button.setTextColor(getColor(R.color.barColor));

        GradientDrawable drawable = (GradientDrawable) getDrawable(R.drawable.button);

        int resultByQuiz = quizResultDao.getResultByQuiz(number);
        if (resultByQuiz == 1) {
             drawable.setColor(ContextCompat.getColor(this,R.color.correctAnswer));

        } else if (resultByQuiz == 0) {
            drawable.setColor(ContextCompat.getColor(this,R.color.incorrectAnswer));

        } else {
             drawable.setColor(ContextCompat.getColor(this,R.color.app_theme_cyan));

        }
         button.setBackground(drawable);

        button.setOnClickListener(v -> v.animate().alpha(0.3f).withEndAction(() ->
                v.animate().alpha(1).withEndAction(() -> {
                    Intent intent = new Intent(TicketsSheetActivity.this, SimpleActivityTest.class);
                    intent.putExtra(TicketsSheetActivity.QUIZ_NUMBER, number);
                    playSound(mSound);
                    startActivity(intent);
                })
        ).start());
        return button;
    }

    private int getWindowWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    @Override
    public void finish() {
        super.finish();
    }
    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
    public void back(View view) {
        playSound(mSound);
        finish();
    }
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}