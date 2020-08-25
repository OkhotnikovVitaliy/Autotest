package com.autotest.pdd_test.fragments;

import android.annotation.TargetApi;
import android.content.Context;
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
import android.widget.ExpandableListView;
import androidx.fragment.app.Fragment;
import com.autotest.pdd_test.R;
import com.autotest.pdd_test.adapters.PhoneExpandableAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.util.ArrayList;


public class PhoneFragment extends Fragment {


    ExpandableListView expandableListView;
    PhoneExpandableAdapter adapter;
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_phone, container, false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expListView);
        setGroupParents();
        setChildData();

        adapter = new PhoneExpandableAdapter(parentItems,childItems, childItemsLower);

        adapter.setInflater(
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),this);

        expandableListView.setAdapter(adapter);
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return view;

    }


    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    private ArrayList<Object> childItemsLower = new ArrayList<Object>();
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    private int mSound;


    public void setGroupParents() {
        parentItems.add("Экстренные службы");
        parentItems.add("Телефоны ГУОБДД");
        parentItems.add("МВД КР");
        parentItems.add("Другое");
    }


    public void setChildData() {

        ArrayList<String> child = new ArrayList<String>();
        child.add("Пожарная служба");
        child.add("Милиция");
        child.add("Скорая медицинская помощь");
        child.add("Аварийная служба газа");
        child.add("Единая служба спасения");
        child.add("Служба спасения ГО и МЧС");

        childItems.add(child);

        ArrayList<String> childLower = new ArrayList<String>();
        childLower.add("101\n");
        childLower.add("102\n");
        childLower.add("103\n");
        childLower.add("104\n");
        childLower.add("112\n");
        childLower.add("161\n");

        childItemsLower.add(childLower);

        child = new ArrayList<String>();
        child.add("ГУОБДД МВД КР");
        child.add("УОБДД ГУВД г. Бишкек");
        child.add("УОБДД ГУВД Чуйской области");
        child.add("ООБДД УВД Иссык-Кульской области");
        child.add("ООБДД УВД Таласской области");
        child.add("ООБДД УВД Нарынской области");
        child.add("ООБДД УВД Жалал-Абадской области");
        child.add("ООБДД УВД г. Ош");
        child.add("УОБДД Ошской области");
        child.add("ООБДД УВД Баткенской области");

        childItems.add(child);

        childLower = new ArrayList<String>();
        childLower.add("+996(312)63-09-01\n");
        childLower.add("+996(312)53-01-81\n");
        childLower.add("+996(312)44-64-22\n");
        childLower.add("+996(3922)7-19-08\n");
        childLower.add("+996(3422)5-31-76\n");
        childLower.add("+996(3522)5-09-20\n");
        childLower.add("+996(3722)2-03-93\n");
        childLower.add("+996(3222)3-95-62\n");
        childLower.add("+996(3222)5-66-40\n");
        childLower.add("+996(3622)5-19-83\n");

        childItemsLower.add(childLower);

        child = new ArrayList<String>();
        child.add("Секретариат (общий отдел)");
        child.add("Телефон доверия");
        child.add("Общественная приемная");
        child.add("Управление по борьбе с коррупцией");
        child.add("Служба внутренней безопасности");

        childItems.add(child);

        childLower = new ArrayList<String>();
        childLower.add("+996(312)26-60-54\n");
        childLower.add("+996(312)26-60-27\n");
        childLower.add("+996(312)26-60-64\n");
        childLower.add("+996(312)62-01-71\n");
        childLower.add("+996(312)62-19-56\n");

        childItemsLower.add(childLower);

        child = new ArrayList<String>();
        child.add("Сообщить о неисправном светофоре");
        child.add("Отдел дорожного надзора");
        child.add("Телефон доверия Генеральной прокуратуры");

        childItems.add(child);

        childLower = new ArrayList<String>();
        childLower.add("+996(312)53-10-87\n");
        childLower.add("+996(312)53-01-90\n");
        childLower.add("+996(312)66-33-73\n");

        childItemsLower.add(childLower);
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