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


public class RoadSignsSingle extends AppCompatActivity {

    TextView num_sign;
    TextView titl_sign;
    TextView description_sign;
    ImageView image_sign;
    private String[] warning_list;
    private String[] priority_list;
    private String[] prohibition_list;
    private String[]prescriptive_list;
    private  String[] special_list;
    private String[] information_list;
    private  String[] service_list;
    private String[] plates_list;
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
        setContentView(R.layout.road_signs_single);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView title = (TextView) findViewById(R.id.toolbar_text);
        title.setText(getString(R.string.signs));
        num_sign = (TextView) findViewById(R.id.num);
        titl_sign = (TextView) findViewById(R.id.title);
        description_sign = (TextView) findViewById(R.id.description);
        image_sign = (ImageView) findViewById(R.id.image);
        intent = getIntent();
        String number_sign = intent.getStringExtra("txtArticle");
        String title_sign = intent.getStringExtra("txtTitle");
        String description = intent.getStringExtra("description");
        int position = intent.getIntExtra("position_sign", 0);
        num_sign.setText(number_sign);
        titl_sign.setText(title_sign);
        description_sign.setText(description);

        if (intent.getStringExtra("position").equals("warning")) {
            try {
                warning_list = getApplicationContext().getAssets().list("warning_signs");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream ims = getApplicationContext().getAssets().open("warning_signs/" + warning_list[position - 1]);
                d = Drawable.createFromStream(ims, null);
                image_sign.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (intent.getStringExtra("position").equals("priority")) {
            try {
                priority_list = getApplicationContext().getAssets().list("priority_images");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream ims = getApplicationContext().getAssets().open("priority_images/" + priority_list[position - 1]);
                d = Drawable.createFromStream(ims, null);
                image_sign.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (intent.getStringExtra("position").equals("prohibition")) {
            try {
                prohibition_list = getApplicationContext().getAssets().list("prohibition_images");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream ims = getApplicationContext().getAssets().open("prohibition_images/" + prohibition_list[position - 1]);
                d = Drawable.createFromStream(ims, null);
                image_sign.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (intent.getStringExtra("position").equals("prescriptive")) {
            try {
                prescriptive_list = getApplicationContext().getAssets().list("prescriptive_images");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream ims = getApplicationContext().getAssets().open("prescriptive_images/" + prescriptive_list[position - 1]);
                d = Drawable.createFromStream(ims, null);
                image_sign.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (intent.getStringExtra("position").equals("special")) {
        try {
            special_list = getApplicationContext().getAssets().list("special_images");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream ims = getApplicationContext().getAssets().open("special_images/" + special_list[position - 1]);
            d = Drawable.createFromStream(ims, null);
            image_sign.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }else if (intent.getStringExtra("position").equals("information")) {
        try {
            information_list = getApplicationContext().getAssets().list("information_images");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream ims = getApplicationContext().getAssets().open("information_images/" + information_list[position - 1]);
            d = Drawable.createFromStream(ims, null);
            image_sign.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

    } else if (intent.getStringExtra("position").equals("service")) {
        try {
            service_list = getApplicationContext().getAssets().list("service_images");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream ims = getApplicationContext().getAssets().open("service_images/" + service_list[position - 1]);
            d = Drawable.createFromStream(ims, null);
            image_sign.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

    } else if (intent.getStringExtra("position").equals("plates")) {
        try {
            plates_list = getApplicationContext().getAssets().list("plates_images");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream ims = getApplicationContext().getAssets().open("plates_images/" + plates_list[position - 1]);
            d = Drawable.createFromStream(ims, null);
            image_sign.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
       /* MobileAds.initialize(this, new OnInitializationCompleteListener() {
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