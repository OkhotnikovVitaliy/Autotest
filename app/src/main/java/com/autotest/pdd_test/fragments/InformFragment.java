package com.autotest.pdd_test.fragments;

import android.annotation.TargetApi;
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
import android.webkit.WebView;
import androidx.fragment.app.Fragment;
import com.autotest.pdd_test.R;
import java.io.IOException;



public class InformFragment extends Fragment {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    private int mSound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inform_layout, container, false);


        WebView webView = (WebView) view.findViewById(R.id.webViewInform);
        webView.setBackgroundResource(R.drawable.ic_background_info);
        webView.getBackground().setAlpha(128);
        webView.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/info.htm");
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
