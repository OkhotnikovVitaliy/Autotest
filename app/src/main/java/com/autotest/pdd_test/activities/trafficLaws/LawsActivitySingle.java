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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.autotest.pdd_test.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;

public class LawsActivitySingle extends AppCompatActivity {

    ListView list;
    TextView header_field;
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    private int mSound;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_laws);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_text);
        title.setText(getString(R.string.law));
        list = (ListView) findViewById(R.id.list_view_law);
        View header = (View) getLayoutInflater().inflate(R.layout.list_law_header, null);
        list.addHeaderView(header);
        Intent intent = getIntent();
        String[] general_provisions = getResources().getStringArray(R.array.general_provisions);
        String[] driver_duties = getResources().getStringArray(R.array.driver_duties);
        String[] special_signals = getResources().getStringArray(R.array.special_signals);
        String[] pedestrian_responsibilities = getResources().getStringArray(R.array.pedestrian_responsibilities);
        String[] passenger_duties = getResources().getStringArray(R.array.passenger_duties);
        String[] traffic_signals = getResources().getStringArray(R.array.traffic_signals);
        String[] alarm = getResources().getStringArray(R.array.alarm);
        String[] start_of_movement = getResources().getStringArray(R.array.start_of_movement);
        String[] location_on_the_road = getResources().getStringArray(R.array.location_on_the_road);
        String[] traveling_speed = getResources().getStringArray(R.array.traveling_speed);
        String[] overtaking = getResources().getStringArray(R.array.overtaking);
        String[] stop_and_parking = getResources().getStringArray(R.array.stop_and_parking);
        String[] crossroads = getResources().getStringArray(R.array.crossroads);
        String[] pedestrian_crossings = getResources().getStringArray(R.array.pedestrian_crossings);
        String[] railroad_crossing = getResources().getStringArray(R.array.railroad_crossing);
        String[] highway = getResources().getStringArray(R.array.highway);
        String[] residential_areas = getResources().getStringArray(R.array.residential_areas);
        String[] route_transport = getResources().getStringArray(R.array.route_transport);
        String[] light_fixtures = getResources().getStringArray(R.array.light_fixtures);
        String[] towing = getResources().getStringArray(R.array.towing);
        String[] training_ride = getResources().getStringArray(R.array.training_ride);
        String[] transportation_of_people = getResources().getStringArray(R.array.transportation_of_people);
        String[] freight_transportation = getResources().getStringArray(R.array.freight_transportation);
        String[] additional_requirements = getResources().getStringArray(R.array.additional_requirements);
        String[] attachment1 = getResources().getStringArray(R.array.attachment1);
        String[] attachment2 = getResources().getStringArray(R.array.attachment2);


        if (intent.getStringExtra("FROMACTIVITY").equals("general_provisions")) {

            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.general_provisions));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, general_provisions);
            list.setAdapter(adapter);

        } else if (intent.getStringExtra("FROMACTIVITY").equals("driver_duties")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.driver_duties));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, driver_duties);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("special_signals")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.special_signals));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, special_signals);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("pedestrian_responsibilities")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.pedestrian_responsibilities));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, pedestrian_responsibilities);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("passenger_duties")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.passenger_duties));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, passenger_duties);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("traffic_signals")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.traffic_signals));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, traffic_signals);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("alarm")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.alarm));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, alarm);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("start_of_movement")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.start_of_movement));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, start_of_movement);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("location_on_the_road")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.location_on_the_road));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, location_on_the_road);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("traveling_speed")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.traveling_speed));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, traveling_speed);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("overtaking")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.overtaking));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, overtaking);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("stop_and_parking")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.stop_and_parking));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, stop_and_parking);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("crossroads")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.crossroads));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, crossroads);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("pedestrian_crossings")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.pedestrian_crossings));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, pedestrian_crossings);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("railroad_crossing")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.railroad_crossing));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, railroad_crossing);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("highway")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.highway));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, highway);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("residential_areas")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.residential_areas));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, residential_areas);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("route_transport")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.route_transport));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, route_transport);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("light_fixtures")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.light_fixtures));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, light_fixtures);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("towing")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.towing));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, towing);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("training_ride")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.training_ride));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, training_ride);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("transportation_of_people")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.transportation_of_people));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, transportation_of_people);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("freight_transportation")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.freight_transportation));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, freight_transportation);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("additional_requirements")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.additional_requirements));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, additional_requirements);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("attachment1")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.attachment1));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, attachment1);
            list.setAdapter(adapter);
        } else if (intent.getStringExtra("FROMACTIVITY").equals("attachment2")) {
            header_field = (TextView) findViewById(R.id.law_header);
            header_field.setText(getString(R.string.attachment2));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.laws_layout_single, attachment2);
            list.setAdapter(adapter);

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