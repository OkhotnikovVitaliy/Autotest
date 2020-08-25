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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.autotest.pdd_test.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;

public class LawsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView general_provisions;
    TextView driver_duties;
    TextView special_signals;
    TextView pedestrian_responsibilities;
    TextView passenger_duties;
    TextView traffic_signals;
    TextView alarm;
    TextView start_of_movement;
    TextView location_on_the_road;
    TextView traveling_speed;
    TextView overtaking;
    TextView stop_and_parking;
    TextView crossroads;
    TextView pedestrian_crossings;
    TextView railroad_crossing;
    TextView highway;
    TextView residential_areas;
    TextView route_transport;
    TextView light_fixtures;
    TextView towing;
    TextView training_ride;
    TextView transportation_of_people;
    TextView freight_transportation;
    TextView additional_requirements;
    TextView attachment1;
    TextView attachment2;
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    private int mSound;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laws_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_text);
        title.setText(getString(R.string.law));

        general_provisions = (TextView) findViewById(R.id.general_provisions);
        driver_duties = (TextView) findViewById(R.id.driver_duties);
        special_signals = (TextView) findViewById(R.id.special_signals);
        pedestrian_responsibilities = (TextView) findViewById(R.id.pedestrian_responsibilities);
        passenger_duties = (TextView) findViewById(R.id.passenger_duties);
        traffic_signals = (TextView) findViewById(R.id.traffic_signals);
        alarm = (TextView) findViewById(R.id.alarm);
        start_of_movement = (TextView) findViewById(R.id.start_of_movement);
        location_on_the_road = (TextView) findViewById(R.id.location_on_the_road);
        traveling_speed = (TextView) findViewById(R.id.traveling_speed);
        overtaking = (TextView) findViewById(R.id.overtaking);
        stop_and_parking = (TextView) findViewById(R.id.stop_and_parking);
        crossroads = (TextView) findViewById(R.id.crossroads);
        pedestrian_crossings = (TextView) findViewById(R.id.pedestrian_crossings);
        railroad_crossing = (TextView) findViewById(R.id.railroad_crossing);
        highway = (TextView) findViewById(R.id.highway);
        residential_areas = (TextView) findViewById(R.id.residential_areas);
        route_transport = (TextView) findViewById(R.id.route_transport);
        light_fixtures = (TextView) findViewById(R.id.light_fixtures);
        towing = (TextView) findViewById(R.id.towing);
        training_ride = (TextView) findViewById(R.id.training_ride);
        transportation_of_people = (TextView) findViewById(R.id.transportation_of_people);
        freight_transportation = (TextView) findViewById(R.id.freight_transportation);
        additional_requirements = (TextView) findViewById(R.id.additional_requirements);
        attachment1 = (TextView) findViewById(R.id.attachment1);
        attachment2 = (TextView) findViewById(R.id.attachment2);

        general_provisions.setOnClickListener(this);
        driver_duties.setOnClickListener(this);
        special_signals.setOnClickListener(this);
        pedestrian_responsibilities.setOnClickListener(this);
        passenger_duties.setOnClickListener(this);
        traffic_signals.setOnClickListener(this);
        alarm.setOnClickListener(this);
        start_of_movement.setOnClickListener(this);
        location_on_the_road.setOnClickListener(this);
        traveling_speed.setOnClickListener(this);
        overtaking.setOnClickListener(this);
        stop_and_parking.setOnClickListener(this);
        crossroads.setOnClickListener(this);
        pedestrian_crossings.setOnClickListener(this);
        railroad_crossing.setOnClickListener(this);
        highway.setOnClickListener(this);
        residential_areas.setOnClickListener(this);
        route_transport.setOnClickListener(this);
        light_fixtures.setOnClickListener(this);
        towing.setOnClickListener(this);
        training_ride.setOnClickListener(this);
        transportation_of_people.setOnClickListener(this);
        freight_transportation.setOnClickListener(this);
        additional_requirements.setOnClickListener(this);
        attachment1.setOnClickListener(this);
        attachment2.setOnClickListener(this);


        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });*/
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    Intent intent;
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.general_provisions:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "general_provisions");
                startActivity(intent);
                break;
            case R.id.driver_duties:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "driver_duties");
                startActivity(intent);
                break;
            case R.id.special_signals:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "special_signals");
                startActivity(intent);
                break;
            case R.id.pedestrian_responsibilities:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "pedestrian_responsibilities");
                startActivity(intent);
                break;
            case R.id.passenger_duties:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "passenger_duties");
                startActivity(intent);
                break;
            case R.id.traffic_signals:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "traffic_signals");
                startActivity(intent);
                break;
            case R.id.alarm:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "alarm");
                startActivity(intent);
                break;
            case R.id.start_of_movement:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "start_of_movement");
                startActivity(intent);
                break;
            case R.id.location_on_the_road:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "location_on_the_road");
                startActivity(intent);
                break;
            case R.id.traveling_speed:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "traveling_speed");
                startActivity(intent);
                break;
            case R.id.overtaking:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "overtaking");
                startActivity(intent);
                break;
            case R.id.stop_and_parking:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "stop_and_parking");
                startActivity(intent);
                break;
            case R.id.crossroads:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "crossroads");
                startActivity(intent);
                break;
            case R.id.pedestrian_crossings:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "pedestrian_crossings");
                startActivity(intent);
                break;
            case R.id.railroad_crossing:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "railroad_crossing");
                startActivity(intent);
                break;
            case R.id.highway:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "highway");
                startActivity(intent);
                break;
            case R.id.residential_areas:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "residential_areas");
                startActivity(intent);
                break;
            case R.id.route_transport:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "route_transport");
                startActivity(intent);
                break;
            case R.id.light_fixtures:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "light_fixtures");
                startActivity(intent);
                break;
            case R.id.towing:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "towing");
                startActivity(intent);
                break;
            case R.id.training_ride:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "training_ride");
                startActivity(intent);
                break;
            case R.id.transportation_of_people:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "transportation_of_people");
                startActivity(intent);
                break;
            case R.id.freight_transportation:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "freight_transportation");
                startActivity(intent);
                break;
            case R.id.additional_requirements:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "additional_requirements");
                startActivity(intent);
                break;
            case R.id.attachment1:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "attachment1");
                startActivity(intent);
                break;
            case R.id.attachment2:
                playSound(mSound);
                intent = new Intent(this, LawsActivitySingle.class);
                intent.putExtra("FROMACTIVITY", "attachment2");
                startActivity(intent);
                break;


        }
    }


    public void back(View view) {
        playSound(mSound);
        finish();
    }
}