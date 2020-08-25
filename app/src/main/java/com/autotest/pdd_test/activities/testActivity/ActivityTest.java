package com.autotest.pdd_test.activities.testActivity;


import android.annotation.TargetApi;
import android.app.Activity;
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
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.autotest.pdd_test.R;
import com.autotest.pdd_test.fragments.FragmentTest;
import com.autotest.pdd_test.listeners.QuizResultListener;
import com.autotest.pdd_test.utiles.CustomViewPager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public abstract class ActivityTest extends AppCompatActivity implements QuizResultListener {

    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private int currentPosition;
    private HashMap<Integer, Integer> tabColorMap = new HashMap<>();
    protected boolean[] answers = new boolean[NUM_OF_QUESTIONS];
    private int numOfIncorrectAnswers;
    public int numOfAnswers;
    public static final int NUM_OF_QUESTIONS = 20;
    Drawable drawable2;
    Drawable drawable3;
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    private int mSound;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private RewardedAd rewardedAd;
    public FloatingActionButton fab;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-9614128497933832/2261075601");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());



        rewardedAd = new RewardedAd(getApplicationContext(), "ca-app-pub-9614128497933832/7429641806");
        RewardedAdLoadCallback callback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdFailedToLoad(int i) {
                super.onRewardedAdFailedToLoad(i);
                Log.i("MainActivity", "Failed");
            }

            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
                Log.i("MainActivity", "Loaded");
            }
        };

        rewardedAd.loadAd(new AdRequest.Builder().build(), callback);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setScaleType(ImageView.ScaleType.CENTER);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                playSound(mSound);
                showAlert();
            }
        });
    }


   public void showAlert(){

       android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        alertDialog.setTitle("Активировать подсказки?");
        alertDialog.setMessage("Просмотрите рекламу и получите возможность смотреть подсказки");
        alertDialog.setPositiveButton("СМОТРЕТЬ РЕКЛАМУ", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which) {

              if (rewardedAd.isLoaded()) {
                    Activity activityContext = ActivityTest.this;
                    RewardedAdCallback callback = new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            super.onRewardedAdOpened();
                        }

                        @Override
                        public void onRewardedAdClosed() {
                            super.onRewardedAdClosed();
                            fab.setVisibility(View.GONE);
                        }

                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem reward) {
                            fab.setVisibility(View.GONE);
                            fab.setEnabled(false);
                        }

                        @Override
                        public void onRewardedAdFailedToShow(int errorCode) {
                            super.onRewardedAdFailedToShow(errorCode);
                        }
                    };
                    rewardedAd.show(activityContext, callback);


                } else {
                    Toast.makeText(ActivityTest.this, "Реклама еще не загрузилась. Проверьте подключение к сети Интернет.\n Попробуйте еще раз", Toast.LENGTH_LONG)
                            .show();
                    Log.d("TAG", "Реклама еще не загрузилась. Попробуйте еще раз");
                }
            }
        });

        alertDialog.setNegativeButton("ЗАКРЫТЬ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
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

            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    public void onResume() {
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
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    protected void initViews() {
        viewPager = (CustomViewPager) findViewById(R.id.test_container);
        viewPager.setPagingEnabled(true);
        viewPager.setOffscreenPageLimit(19);
        setupViewPager(viewPager);
        drawable2 = getDrawable(R.drawable.circle_small_vector_green);
        drawable3 = getDrawable(R.drawable.circle_small_vector_red);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab currentTab) {

                for (Integer index : tabColorMap.keySet()) {
                    View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(index);


                    if (tabColorMap.get(index) == R.color.correctAnswer) {
                        tab.setBackground(drawable2);

                    }
                    if (tabColorMap.get(index) == R.color.incorrectAnswer) {
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


    FragmentTest fragmentTest;

    protected abstract void setupViewPager(ViewPager viewPager);

    @Override
    public void resultListener(boolean isCorrect, int questionNumber) {
        fragmentTest = new FragmentTest();
        if (isCorrect) {

            setTabColor(R.color.correctAnswer);
            nextQuestion();
        } else {
            setTabColor(R.color.incorrectAnswer);
            numOfIncorrectAnswers++;
        }

        answers[questionNumber - 1] = isCorrect;
        numOfAnswers++;
        if (numOfAnswers == NUM_OF_QUESTIONS) {
            showInterstitial();
            showAlertDialog(this);
        }

    }

    AlertDialog alert;

    protected void showAlertDialog(AppCompatActivity context) {
        int alertTextColor = isQuizSuccessfulDone() ? R.color.correctAnswer : R.color.incorrectAnswer;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View alertDialogView = getAlertDialogView(getResultMessage(), alertTextColor);
        builder.setView(alertDialogView);
        builder.setCancelable(false);
        alert = builder.create();
        Button close = (Button) alertDialogView.findViewById(R.id.close);
        close.setOnClickListener(v -> alert.cancel());
        stopAllProcesses();
        alert.show();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (alert != null) {
            alert.dismiss();
            alert = null;
        }
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

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
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
        new Handler().postDelayed(() ->
                runOnUiThread(() -> viewPager.setCurrentItem(currentPosition + 1, true)), 100);
    }

    private void setTabColor(int color) {
        View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(currentPosition);
        int colorId = getColor(color);

        if (color == R.color.correctAnswer) {
            tab.setBackground(drawable2);
        } else {
            tab.setBackground(drawable3);
        }

        tabColorMap.put(currentPosition, colorId);
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