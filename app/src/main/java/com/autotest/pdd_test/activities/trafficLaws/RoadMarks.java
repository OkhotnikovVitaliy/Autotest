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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.autotest.pdd_test.R;
import com.autotest.pdd_test.adapters.CustomListAdapterHorisontalMarks;
import com.autotest.pdd_test.adapters.CustomListAdapterVerticalMarks;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;

public class RoadMarks extends AppCompatActivity {

    ListView list;
    Intent single_mark;
    CustomListAdapterHorisontalMarks customListAdapterMarks;
    CustomListAdapterVerticalMarks customListAdapterVerticalMarks;
    String[] horisontal_marks_description;
    String[] vertical_marks_description;
    int horisontal_marks_images;
    int vertical_marks_images;
    TextView header_field;
    TextView header_field_desc;
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    private int mSound;
    private AdView mAdView;
    String[] horisontal_marks_article = {"Разметка 1.1", "Разметка 1.2.1", "Разметка 1.2.2", "Разметка 1.3",
            "Разметка 1.4", "Разметка 1.5", "Разметка 1.6", "Разметка 1.7", "Разметка 1.8", "Разметка 1.9",
            "Разметка 1.10", "Разметка 1.11", "Разметка 1.12", "Разметка 1.13", "Разметка 1.14.1", "Разметка 1.14.2",
            "Разметка 1.15", "Разметка 1.16.1", "Разметка 1.16.2", "Разметка 1.16.3","Разметка 1.17","Разметка 1.18",
            "Разметка 1.19", "Разметка 1.20", "Разметка 1.21", "Разметка 1.22", "Разметка 1.23", "Разметка 1.24.1",
            "Разметка 1.24.2","Разметка 1.24.3", "Разметка 1.25", "Разметка 1.26"};

    String[] vertical_marks_article = {"Разметка 2.1.1", "Разметка 2.1.2", "Разметка 2.1.3", "Разметка 2.2",
            "Разметка 2.3", "Разметка 2.4", "Разметка 2.5", "Разметка 2.6", "Разметка 2.7"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_road_marks);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_text);
        title.setText(getString(R.string.marks));
        list = (ListView) findViewById(R.id.list_view_marks);
        View header = (View) getLayoutInflater().inflate(R.layout.list_road_marks_header, null);
        list.addHeaderView(header, null, false);
        header_field_desc = (TextView) findViewById(R.id.marks_header_desc);
        header_field_desc.setText(getString(R.string.horisontal_marks_field_desc));

        Intent intent = getIntent();
        horisontal_marks_description = getResources().getStringArray(R.array.horisontal_marks_description);
        vertical_marks_description = getResources().getStringArray(R.array.vertical_marks_description);

        if (intent.getStringExtra("FROMACTIVITY").equals("HORISONTAL")) {
            customListAdapterMarks = new CustomListAdapterHorisontalMarks(this, horisontal_marks_article, horisontal_marks_images);
            list.setAdapter(customListAdapterMarks);
            header_field = (TextView) findViewById(R.id.marks_header);
            header_field.setText(getString(R.string.horisontal_marks_field));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    playSound(mSound);
                    single_mark = new Intent(getApplicationContext(), RoadMarksSingle.class);
                    single_mark.putExtra("txtArticle", horisontal_marks_article[position - 1]);
                    single_mark.putExtra("description", horisontal_marks_description[position - 1]);
                    single_mark.putExtra("position_mark", position);
                    single_mark.putExtra("position", "horisontal_marks");

                    startActivity(single_mark);
                }
            });

        } else if (intent.getStringExtra("FROMACTIVITY").equals("VERTICAL")) {
            customListAdapterVerticalMarks = new CustomListAdapterVerticalMarks(this, vertical_marks_article, vertical_marks_images);
            list.setAdapter(customListAdapterVerticalMarks);
            header_field = (TextView) findViewById(R.id.marks_header);
            header_field.setText(getString(R.string.vertical_marks_field));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    playSound(mSound);
                    single_mark = new Intent(getApplicationContext(), RoadMarksSingle.class);
                    single_mark.putExtra("txtArticle", vertical_marks_article[position - 1]);
                    single_mark.putExtra("description", vertical_marks_description[position - 1]);
                    single_mark.putExtra("position_mark", position);
                    single_mark.putExtra("position", "vertical_marks");

                    startActivity(single_mark);
                }
            });


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