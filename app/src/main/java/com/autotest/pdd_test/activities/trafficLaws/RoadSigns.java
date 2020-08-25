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
import com.autotest.pdd_test.adapters.CustomListAdapterInformation;
import com.autotest.pdd_test.adapters.CustomListAdapterPlates;
import com.autotest.pdd_test.adapters.CustomListAdapterPrescriptive;
import com.autotest.pdd_test.adapters.CustomListAdapterProhibition;
import com.autotest.pdd_test.adapters.CustomListAdapterService;
import com.autotest.pdd_test.adapters.CustomListAdapterSpecial;
import com.autotest.pdd_test.adapters.CustomListAdapterWarning;
import com.autotest.pdd_test.adapters.CustomListAdapterPriority;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;

public class RoadSigns extends AppCompatActivity {
    ListView list;
    String[] warning_textSign;
    String[] priority_textSign;
    String[] prohibition_textSign;
    String[] prescriptive_textSign;
    String[] special_textSign;
    String[] information_textSign;
    String[] service_textSign;
    String[] plates_textSign;
    String[] warning_description;
    String[] priority_description;
    String[]prohibition_description;
    String[]prescriptive_description;
    String[] special_description;
    String[] information_description;
    String[] service_description;
    String[] plates_description;


    String[] warning_article = {"1.1", "1.2", "1.3.1", "1.3.2", "1.4.1", "1.4.2", "1.4.3", "1.4.4",
            "1.4.5", "1.4.6", "1.5", "1.6", "1.7", "1.8", "1.9", "1.10", "1.11.1", "1.11.2", "1.12.1",
            "1.12.2", "1.13", "1.14", "1.15", "1.16", "1.17", "1.18", "1.19", "1.20.1", "1.20.2",
            "1.20.3", "1.21", "1.22", "1.23", "1.24", "1.25", "1.26", "1.27", "1.28", "1.29", "1.30",
            "1.31", "1.32", "1.33", "1.34.1", "1.34.2", "1.34.3"};
    String[] priority_article = {"2.1", "2.2", "2.3.1", "2.3.2", "2.3.3", "2.3.4",
            "2.3.5", "2.3.6", "2.3.7", "2.4", "2.5", "2.6", "2.7"};
    String[] prohibition_article = {"3.1", "3.2", "3.3", "3.4", "3.5", "3.6",
            "3.7", "3.8", "3.9", "3.10", "3.11", "3.12", "3.13", "3.14", "3.15", "3.16", "3.17.1", "3.17.2",
            "3.17.3", "3.18.1", "3.18.2", "3.19", "3.20", "3.21", "3.22", "3.23", "3.24", "3.25", "3.26", "3.27",
            "3.28", "3.29", "3.30", "3.31", "3.32", "3.33"};
    String[] prescriptive_article = {"4.1.1", "4.1.2", "4.1.3", "4.1.4", "4.1.5", "4.1.6",
            "4.2.1", "4.2.2", "4.2.3", "4.3", "4.4", "4.5", "4.6", "4.7", "4.8.1", "4.8.2", "4.8.3"};
    String[] special_article = {"5.1", "5.2", "5.3", "5.4", "5.5", "5.6",
            "5.7.1", "5.7.2", "5.8", "5.9", "5.10", "5.11", "5.12", "5.13.1", "5.13.2", "5.14", "5.14.1", "5.15.1", "5.15.2",
            "5.15.3", "5.15.4", "5.15.5", "5.15.6", "5.15.7", "5.15.8", "5.16", "5.17", "5.18", "5.19.1", "5.19.2", "5.20",
            "5.21", "5.22", "5.23.1", "5.23.2", "5.24.1", "5.24.2", "5.25", "5.26", "5.27", "5.28", "5.29", "5.30",
            "5.31", "5.32", "5.33", "5.34"};
    String[] information_article = {"6.1", "6.2", "6.3.1", "6.3.2", "6.4", "6.5",
            "6.6", "6.7", "6.8.1", "6.8.2", "6.8.3", "6.9.1", "6.9.1", "6.9.1", "6.9.1", "6.9.1", "6.9.2", "6.9.2", "6.9.2",
            "6.9.3", "6.10.1", "6.10.1", "6.10.1", "6.10.1", "6.10.1", "6.10.2", "6.10.2", "6.11", "6.11", "6.12", "6.13",
            "6.14.1", "6.14.2", "6.15.1", "6.15.2", "6.15.3", "6.16", "6.17", "6.18.1", "6.18.2", "6.18.3", "6.19.1", "6.19.2",
            "6.20.1", "6.20.2", "6.21.1", "6.21.2"};
    String[] service_article = {"7.1", "7.2", "7.3", "7.4", "7.5", "7.6",
            "7.7", "7.8", "7.9", "7.10", "7.11", "7.12", "7.13", "7.14", "7.15", "7.16", "7.17", "7.18", "7.19", "7.20"};
    String[] plates_article = {"8.1.1", "8.1.2", "8.1.3", "8.1.4", "8.2.1", "8.2.2",
            "8.2.3", "8.2.4", "8.2.5", "8.2.6", "8.3.1", "8.3.2", "8.3.3", "8.4.1", "8.4.2", "8.4.3", "8.4.3.1", "8.4.4",
            "8.4.5", "8.4.6", "8.4.7", "8.4.8", "8.4.9", "8.4.10", "8.4.11", "8.4.12", "8.4.13", "8.4.14", "8.4.15", "8.5.1",
            "8.5.2", "8.5.3", "8.5.4", "8.5.5", "8.5.6", "8.5.7", "8.6.1", "8.6.2", "8.6.3", "8.6.4", "8.6.5", "8.6.6",
            "8.6.7", "8.6.8", "8.6.9", "8.7", "8.8", "8.9", "8.9.1", "8.10", "8.11", "8.12", "8.13", "8.14", "8.15", "8.16",
            "8.17", "8.18", "8.19", "8.20.1", "8.20.2", "8.21.1", "8.21.2", "8.21.3", "8.22.1", "8.22.2", "8.22.3",
            "8.23", "8.24", "8.25"};


    int warning_images;
    int priority_images;
    int prohibition_images;
    int prescriptive_images;
    int special_images;
    int information_images;
    int service_images;
    int plates_images;

    CustomListAdapterWarning customListAdapterWarning;
    CustomListAdapterPriority customListAdapterPriority;
    CustomListAdapterProhibition customListAdapterProhibition;
    CustomListAdapterPrescriptive customListAdapterPrescriptive;
    CustomListAdapterSpecial customListAdapterSpecial;
    CustomListAdapterInformation customListAdapterInformation;
    CustomListAdapterService customListAdapterService;
    CustomListAdapterPlates customListAdapterPlates;
    TextView header_field;
    TextView header_desc;
    Intent single_sign;
   private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    private int mSound;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_road_signs);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_text);
        title.setText(getString(R.string.signs));
        list = (ListView) findViewById(R.id.list_view);
        warning_textSign = getResources().getStringArray(R.array.signs);
        priority_textSign = getResources().getStringArray(R.array.priority_signs);
        prohibition_textSign = getResources().getStringArray(R.array.prohibition_signs);
        prescriptive_textSign = getResources().getStringArray(R.array.prescriptive_signs);
        special_textSign = getResources().getStringArray(R.array.special_signs);
        information_textSign = getResources().getStringArray(R.array.information_signs);
        service_textSign = getResources().getStringArray(R.array.service_signs);
        plates_textSign = getResources().getStringArray(R.array.plates_signs);
        warning_description = getResources().getStringArray(R.array.warning_description);
        priority_description = getResources().getStringArray(R.array.priority_description);
        prohibition_description = getResources().getStringArray(R.array.prohibition_description);
        prescriptive_description = getResources().getStringArray(R.array.prescriptive_description);
        special_description = getResources().getStringArray(R.array.special_description);
        information_description = getResources().getStringArray(R.array.information_description);
        service_description = getResources().getStringArray(R.array.service_description);
        plates_description = getResources().getStringArray(R.array.plates_description);

        View header = (View) getLayoutInflater().inflate(R.layout.list_road_sign_header, null);
        list.addHeaderView (header, null, false);
        header_desc = (TextView) findViewById(R.id.header_desc);
        header_desc.setText(getString(R.string.signs_field));
        Intent intent = getIntent();



        if (intent.getStringExtra("FROMACTIVITY").equals("WARNING")) {
            customListAdapterWarning = new CustomListAdapterWarning(this, warning_textSign, warning_article, warning_images);
            list.setAdapter(customListAdapterWarning);
            header_field = (TextView) findViewById(R.id.header);

            header_field.setText(getString(R.string.warning_signs_field));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    playSound(mSound);
                    single_sign = new Intent(getApplicationContext(), RoadSignsSingle.class);
                    single_sign.putExtra("txtTitle", warning_textSign[position-1]);
                    single_sign.putExtra("txtArticle", warning_article[position-1]);
                    single_sign.putExtra("description", warning_description[position-1]);
                    single_sign.putExtra("position_sign", position);
                    single_sign.putExtra("position", "warning");

                    startActivity(single_sign);
                }
            });

        } else if (intent.getStringExtra("FROMACTIVITY").equals("PRIORITY")) {
            customListAdapterPriority = new CustomListAdapterPriority(this, priority_textSign, priority_article, priority_images);
            list.setAdapter(customListAdapterPriority);
            header_field = (TextView) findViewById(R.id.header);
            header_field.setText(getString(R.string.priority_signs_field));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    playSound(mSound);
                    single_sign = new Intent(getApplicationContext(), RoadSignsSingle.class);
                    single_sign.putExtra("txtTitle", priority_textSign[position-1]);
                    single_sign.putExtra("txtArticle", priority_article[position-1]);
                    single_sign.putExtra("description", priority_description[position-1]);
                    single_sign.putExtra("position_sign", position);
                    single_sign.putExtra("position", "priority");
                    startActivity(single_sign);
                }
            });
        } else if (intent.getStringExtra("FROMACTIVITY").equals("PROHIBITION")) {
            customListAdapterProhibition = new CustomListAdapterProhibition(this, prohibition_textSign, prohibition_article, prohibition_images);
            list.setAdapter(customListAdapterProhibition);
            header_field = (TextView) findViewById(R.id.header);
            header_field.setText(getString(R.string.prohibition_signs_field));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    playSound(mSound);
                    single_sign = new Intent(getApplicationContext(), RoadSignsSingle.class);
                    single_sign.putExtra("txtTitle", prohibition_textSign[position-1]);
                    single_sign.putExtra("txtArticle", prohibition_article[position-1]);
                    single_sign.putExtra("description", prohibition_description[position-1]);
                    single_sign.putExtra("position_sign", position);
                    single_sign.putExtra("position", "prohibition");
                    startActivity(single_sign);
                }
            });

        } else if (intent.getStringExtra("FROMACTIVITY").equals("PRESCRIPTIVE")) {
            customListAdapterPrescriptive = new CustomListAdapterPrescriptive(this, prescriptive_textSign, prescriptive_article, prescriptive_images);
            list.setAdapter(customListAdapterPrescriptive);
            header_field = (TextView) findViewById(R.id.header);
            header_field.setText(getString(R.string.prescriptive_signs_field));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    playSound(mSound);
                    single_sign = new Intent(getApplicationContext(), RoadSignsSingle.class);
                    single_sign.putExtra("txtTitle", prescriptive_textSign[position-1]);
                    single_sign.putExtra("txtArticle", prescriptive_article[position-1]);
                    single_sign.putExtra("description", prescriptive_description[position-1]);
                    single_sign.putExtra("position_sign", position);
                    single_sign.putExtra("position", "prescriptive");
                    startActivity(single_sign);
                }
            });
        } else if (intent.getStringExtra("FROMACTIVITY").equals("SPECIAL")) {
            customListAdapterSpecial = new CustomListAdapterSpecial(this, special_textSign, special_article, special_images);
            list.setAdapter(customListAdapterSpecial);
            header_field = (TextView) findViewById(R.id.header);
            header_field.setText(getString(R.string.special_signs_field));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    playSound(mSound);
                    single_sign = new Intent(getApplicationContext(), RoadSignsSingle.class);
                    single_sign.putExtra("txtTitle", special_textSign[position-1]);
                    single_sign.putExtra("txtArticle", special_article[position-1]);
                    single_sign.putExtra("description", special_description[position-1]);
                    single_sign.putExtra("position_sign", position);
                    single_sign.putExtra("position", "special");
                    startActivity(single_sign);
                }
            });
        } else if (intent.getStringExtra("FROMACTIVITY").equals("INFORMATION")) {
            customListAdapterInformation = new CustomListAdapterInformation(this, information_textSign, information_article, information_images);
            list.setAdapter(customListAdapterInformation);
            header_field = (TextView) findViewById(R.id.header);
            header_field.setText(getString(R.string.information_signs_field));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    playSound(mSound);
                    single_sign = new Intent(getApplicationContext(), RoadSignsSingle.class);
                    single_sign.putExtra("txtTitle", information_textSign[position-1]);
                    single_sign.putExtra("txtArticle", information_article[position-1]);
                    single_sign.putExtra("description", information_description[position-1]);
                    single_sign.putExtra("position_sign", position);
                    single_sign.putExtra("position", "information");
                    startActivity(single_sign);
                }
            });
        } else if (intent.getStringExtra("FROMACTIVITY").equals("SERVICE")) {
            customListAdapterService = new CustomListAdapterService(this, service_textSign, service_article, service_images);
            list.setAdapter(customListAdapterService);
            header_field = (TextView) findViewById(R.id.header);
            header_field.setText(getString(R.string.service_signs_field));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    playSound(mSound);
                    single_sign = new Intent(getApplicationContext(), RoadSignsSingle.class);
                    single_sign.putExtra("txtTitle", service_textSign[position-1]);
                    single_sign.putExtra("txtArticle", service_article[position-1]);
                    single_sign.putExtra("description", service_description[position-1]);
                    single_sign.putExtra("position_sign", position);
                    single_sign.putExtra("position", "service");
                    startActivity(single_sign);
                }
            });
        } else if (intent.getStringExtra("FROMACTIVITY").equals("PLATES")) {
            customListAdapterPlates = new CustomListAdapterPlates(this, plates_textSign, plates_article, plates_images);
            list.setAdapter(customListAdapterPlates);
            header_field = (TextView) findViewById(R.id.header);
            header_field.setText(getString(R.string.plates_field));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    playSound(mSound);
                    single_sign = new Intent(getApplicationContext(), RoadSignsSingle.class);
                    single_sign.putExtra("txtTitle", plates_textSign[position-1]);
                    single_sign.putExtra("txtArticle", plates_article[position-1]);
                    single_sign.putExtra("description", plates_description[position-1]);
                    single_sign.putExtra("position_sign", position);
                    single_sign.putExtra("position", "plates");
                    startActivity(single_sign);
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