package com.autotest.pdd_test.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.autotest.pdd_test.R;
import com.autotest.pdd_test.activities.CarCheck;
import com.autotest.pdd_test.activities.FinesActivity;
import com.autotest.pdd_test.activities.examActivity.ExamActivity;
import com.autotest.pdd_test.activities.trafficLaws.TrafficLawsActivity;
import com.autotest.pdd_test.activities.TicketsSheetActivity;

import java.io.IOException;

public class MainFragment extends Fragment {

    Toolbar toolbar;
    RelativeLayout test_layout;
    RelativeLayout rules_layout;
    RelativeLayout exam_layout;
    RelativeLayout fines_layout;
    RelativeLayout check_layout;
    Intent intent;
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    private int mSound;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        test_layout = (RelativeLayout) view.findViewById(R.id.test_layout);
        rules_layout = (RelativeLayout) view.findViewById(R.id.rules_layout);
        exam_layout = (RelativeLayout) view.findViewById(R.id.layout);
        check_layout = (RelativeLayout) view.findViewById(R.id.check_layout);
        fines_layout = (RelativeLayout) view.findViewById(R.id.fines_layout);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        View.OnClickListener oclBtnTest = new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.test_layout:
                        playSound(mSound);
                         intent = new Intent(getActivity(), TicketsSheetActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.rules_layout:
                        playSound(mSound);
                       intent = new Intent(getActivity(), TrafficLawsActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.layout:
                        playSound(mSound);
                        intent = new Intent(getActivity(), ExamActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.check_layout:
                        playSound(mSound);
                       intent = new Intent(getActivity(), CarCheck.class);
                        startActivity(intent);
                        break;

                    case R.id.fines_layout:
                        playSound(mSound);
                        intent = new Intent(getActivity(), FinesActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };

        test_layout.setOnClickListener(oclBtnTest);
        rules_layout.setOnClickListener(oclBtnTest);
        exam_layout.setOnClickListener(oclBtnTest);
        check_layout.setOnClickListener(oclBtnTest);
        fines_layout.setOnClickListener(oclBtnTest);

        return view;

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
            Toast.makeText(getActivity().getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getActivity().getAssets();

        // получим идентификаторы
        mSound = loadSound("sound.ogg");

    }

    @Override
    public void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }

}