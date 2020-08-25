package com.autotest.pdd_test.activities.trafficLaws;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.autotest.pdd_test.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;

public class SignsActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout warning_signs;
    RelativeLayout priority_signs;
    RelativeLayout prohibition_signs;
    RelativeLayout prescriptive_signs;
    RelativeLayout special_signs;
    RelativeLayout information_signs;
    RelativeLayout service_signs;
    RelativeLayout plates_signs;
    Animation animAlpha;
   private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    private int mSound;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signs_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView title = (TextView) findViewById(R.id.toolbar_text);
        title.setText(getString(R.string.signs));
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        warning_signs = (RelativeLayout) findViewById(R.id.warning_signs);
        priority_signs = (RelativeLayout) findViewById(R.id.priority_signs);
        prohibition_signs = (RelativeLayout) findViewById(R.id.prohibition_signs);
        prescriptive_signs = (RelativeLayout) findViewById(R.id.prescriptive_signs);
        special_signs = (RelativeLayout) findViewById(R.id.special_signs);
        information_signs = (RelativeLayout) findViewById(R.id.information_signs);
        service_signs = (RelativeLayout) findViewById(R.id.service_signs);
        plates_signs = (RelativeLayout) findViewById(R.id.plates_signs);

        warning_signs.setOnClickListener(this);
        priority_signs.setOnClickListener(this);
        prohibition_signs.setOnClickListener(this);
        prescriptive_signs.setOnClickListener(this);
        special_signs.setOnClickListener(this);
        information_signs.setOnClickListener(this);
        service_signs.setOnClickListener(this);
        plates_signs.setOnClickListener(this);

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

    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.warning_signs:
                playSound(mSound);
                intent = new Intent(this, RoadSigns.class);
                intent.putExtra("FROMACTIVITY", "WARNING");
                startActivity(intent);
               // v.startAnimation(animAlpha);
                break;
            case R.id.priority_signs:
                playSound(mSound);
                intent = new Intent(this, RoadSigns.class);
                intent.putExtra("FROMACTIVITY", "PRIORITY");
                startActivity(intent);
               // v.startAnimation(animAlpha);
                break;

            case R.id.prohibition_signs:
                playSound(mSound);
                intent = new Intent(this, RoadSigns.class);
                intent.putExtra("FROMACTIVITY", "PROHIBITION");
                startActivity(intent);
              //  v.startAnimation(animAlpha);
                break;

            case R.id.prescriptive_signs:
                playSound(mSound);
                intent = new Intent(this, RoadSigns.class);
                intent.putExtra("FROMACTIVITY", "PRESCRIPTIVE");
                startActivity(intent);
               // v.startAnimation(animAlpha);
                break;

            case R.id.special_signs:
                playSound(mSound);
                intent = new Intent(this, RoadSigns.class);
                intent.putExtra("FROMACTIVITY", "SPECIAL");
                startActivity(intent);
               // v.startAnimation(animAlpha);
                break;

            case R.id.information_signs:
                playSound(mSound);
                intent = new Intent(this, RoadSigns.class);
                intent.putExtra("FROMACTIVITY", "INFORMATION");
                startActivity(intent);
               // v.startAnimation(animAlpha);
                break;

            case R.id.service_signs:
                playSound(mSound);
                intent = new Intent(this, RoadSigns.class);
                intent.putExtra("FROMACTIVITY", "SERVICE");
                startActivity(intent);
               // v.startAnimation(animAlpha);
                break;

            case R.id.plates_signs:
                playSound(mSound);
                intent = new Intent(this, RoadSigns.class);
                intent.putExtra("FROMACTIVITY", "PLATES");
                startActivity(intent);
               // v.startAnimation(animAlpha);
                break;
        }
    }




    public void back(View view) {
        playSound(mSound);
        finish();
    }
}
