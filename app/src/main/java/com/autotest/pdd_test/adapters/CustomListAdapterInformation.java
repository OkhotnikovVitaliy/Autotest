package com.autotest.pdd_test.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autotest.pdd_test.R;

import java.io.IOException;
import java.io.InputStream;

public class CustomListAdapterInformation extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] information_textSign;
    private final String[] information_article;
    private final int information_images;
    private String[] list;
    LayoutInflater inflater;
    TextView txtTitle;
    TextView txtArticle;
    ImageView imageView;

    public CustomListAdapterInformation(Activity context, String[] information_textSign, String[] information_article, int information_images) {
        super(context, R.layout.road_signs, information_textSign);
        this.context = context;
        this.information_textSign = information_textSign;
        this.information_article = information_article;
        this.information_images = information_images;
        inflater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        convertView = inflater.inflate(R.layout.road_signs, null, true);
        txtTitle = (TextView) convertView.findViewById(R.id.text);
        txtArticle = (TextView) convertView.findViewById(R.id.article);
        imageView = (ImageView) convertView.findViewById(R.id.image);
        txtTitle.setText(information_textSign[position]);
        txtArticle.setText(information_article[position]);

        try {
            list = context.getAssets().list("information_images");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            InputStream ims = context.getAssets().open("information_images/" + list[position]);
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
