package com.autotest.pdd_test.activities.trafficLaws;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.autotest.pdd_test.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.io.InputStream;

public class RoadMarksSingle extends AppCompatActivity {


    TextView titl_mark;
    TextView description_mark;
    ImageView image_mark;
    private String[] horisontal_marks_list;
    private String[] vertical_marks_list;
    Drawable d;
    Intent intent;
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    private int mSound;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.road_marks_single);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView title = (TextView) findViewById(R.id.toolbar_text);
        title.setText(getString(R.string.marks));

        titl_mark = (TextView) findViewById(R.id.title);
        description_mark = (TextView) findViewById(R.id.description);
        image_mark = (ImageView) findViewById(R.id.image);
        intent = getIntent();
        String number_sign = intent.getStringExtra("txtArticle");
        String description = intent.getStringExtra("description");
        int position = intent.getIntExtra("position_mark", 0);
        titl_mark.setText(number_sign);
        description_mark.setText(description);

        if (intent.getStringExtra("position").equals("horisontal_marks")) {
            try {
                horisontal_marks_list = getApplicationContext().getAssets().list("horisontal_marks_images");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream ims = getApplicationContext().getAssets().open("horisontal_marks_images/" + horisontal_marks_list[position - 1]);
                d = Drawable.createFromStream(ims, null);
                image_mark.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (intent.getStringExtra("position").equals("vertical_marks")) {
            try {
                vertical_marks_list = getApplicationContext().getAssets().list("vertical_marks_images");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream ims = getApplicationContext().getAssets().open("vertical_marks_images/" + vertical_marks_list[position - 1]);
                d = Drawable.createFromStream(ims, null);
                image_mark.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      /*  MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });*/
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

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
    public void back(View view) {
        playSound(mSound);
        finish();
    }
}