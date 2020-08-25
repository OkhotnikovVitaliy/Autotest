package com.autotest.pdd_test.activities.examActivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.autotest.pdd_test.R;
import com.autotest.pdd_test.listeners.QuizResultListener;
import com.autotest.pdd_test.utiles.CustomViewPager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.tabs.TabLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;



public abstract class ActivityTestExam extends AppCompatActivity implements QuizResultListener {

    private TabLayout tabLayout;
    public CustomViewPager viewPager;
    private int currentPosition;
    private HashMap<Integer, Integer> tabColorMap = new HashMap<>();
    protected boolean[] answers = new boolean[NUM_OF_QUESTIONS];
    private int numOfIncorrectAnswers;
    private int numOfAnswers;
    public static final int NUM_OF_QUESTIONS = 20;
    Drawable drawable2;
    Drawable drawable3;
    int color2 = R.color.correct;
    int color3 = R.color.correct_2;
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    private int mSound;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_exam);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-9614128497933832/2261075601");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    protected void initViews() {
        viewPager = findViewById(R.id.test_container);
        viewPager.setOffscreenPageLimit(19);
        setupViewPager(viewPager);
        viewPager.setPagingEnabled(false);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        drawable2 = getDrawable(R.drawable.circle_small_vector_green);
        drawable3 = getDrawable(R.drawable.circle_small_vector_red);
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        tabStrip.setEnabled(false);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab currentTab) {

                currentPosition = currentTab.getPosition();
                viewPager.setCurrentItem(currentPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab currentTab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab currentTab) {
            }
        });

    }

    protected abstract void setupViewPager(ViewPager viewPager);

    @Override
    public void resultListener(boolean isCorrect, int questionNumber) {

        if (isCorrect) {
            setTabColor(color2);

        } else {
            setTabColor(color3);
            numOfIncorrectAnswers++;
        }
        nextQuestion();
        answers[questionNumber - 1] = isCorrect;
        numOfAnswers++;
        if (numOfAnswers == NUM_OF_QUESTIONS) {
           showInterstitial();
            showAlertDialog(this);
        }
    }

    public void showAlertDialog(AppCompatActivity context) {
        int alertTextColor = isQuizSuccessfulDone() ? R.color.correctAnswer : R.color.incorrectAnswer;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View alertDialogView = getAlertDialogView(getResultMessage(), alertTextColor);
        builder.setView(alertDialogView);
        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        Button close = (Button) alertDialogView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alert.cancel();
                viewPager.setCurrentItem(0);
                viewPager.setPagingEnabled(true);
                LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
                tabStrip.setEnabled(true);
                for (int i = 0; i < tabStrip.getChildCount(); i++) {
                    tabStrip.getChildAt(i).setClickable(true);
                }

                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                    @Override
                    public void onTabSelected(TabLayout.Tab currentTab) {

                        for (Integer index : tabColorMap.keySet()) {
                            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(index);

                            if (tabColorMap.get(index) == color2) {
                                tab.setBackground(drawable2);

                            }
                            if (tabColorMap.get(index) == color3) {
                                tab.setBackground(drawable3);
                            }
                        }
                        currentPosition = currentTab.getPosition();
                        viewPager.setCurrentItem(currentPosition);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab currentTab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab currentTab) {
                    }
                });
            }
        });
        stopAllProcesses();
        alert.show();
    }

    protected boolean isQuizSuccessfulDone() {
        return numOfIncorrectAnswers <= 2;
    }

    protected abstract String getResultMessage();

    protected abstract int getLayoutAlertDialogId();

    protected abstract void stopAllProcesses();

    protected abstract String getTitleName();

    protected View getAlertDialogView(String message, int alertTextColor) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewRoot = inflater.inflate(getLayoutAlertDialogId(), null);
        TextView title = (TextView) viewRoot.findViewById(R.id.alertTitle);
        Button again = (Button) viewRoot.findViewById(R.id.again_quiz);
        title.setText(String.format(Locale.getDefault(), "%s\n%s", getTitleName(), message));
        title.setTextColor(getColor(alertTextColor));
        again.setOnClickListener(v -> {
            again();
            startActivity(getIntent());
        });
        return viewRoot;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void again() {
        super.finish();
    }

    public void nextQuestion() {
        playSound(mSound);
        viewPager.setCurrentItem(currentPosition + 1, false);
    }

    private void setTabColor(int color) {
        if (color == color2) {
            tabColorMap.put(currentPosition, color2);
        } else {
            tabColorMap.put(currentPosition, color3);

        }
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
        if (mAdView != null) {
            mAdView.pause();
        }
        mSoundPool.release();
        mSoundPool = null;
    }

    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    public void back(View view) {
        playSound(mSound);
        if (numOfAnswers == NUM_OF_QUESTIONS) {
            finish();
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Прогресс будет потерян!")
                    .setMessage("Вы действительно хотите выйти?")
                    .setNegativeButton("НЕТ", null)
                    .setPositiveButton("ВЫЙТИ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                           showInterstitial();
                            finish();
                        }
                    }).create().show();
        }
    }

    public void onBackPressed() {
        if (numOfAnswers == NUM_OF_QUESTIONS) {
            finish();
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Прогресс будет потерян!")
                    .setMessage("Вы действительно хотите выйти?")
                    .setNegativeButton("НЕТ", null)
                    .setPositiveButton("ВЫЙТИ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                           showInterstitial();
                            finish();
                        }
                    }).create().show();
        }
    }
}