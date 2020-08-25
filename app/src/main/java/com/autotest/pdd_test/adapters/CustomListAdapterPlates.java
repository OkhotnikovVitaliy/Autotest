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

public class CustomListAdapterPlates extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] plates_textSign;
    private final String[] plates_article;
    private final int plates_images;
    private String[] list;
    LayoutInflater inflater;
    TextView txtTitle;
    TextView txtArticle;
    ImageView imageView;

    public CustomListAdapterPlates(Activity context, String[] plates_textSign, String[] plates_article, int plates_images) {
        super(context, R.layout.road_signs, plates_textSign);
        this.context = context;
        this.plates_textSign = plates_textSign;
        this.plates_article = plates_article;
        this.plates_images = plates_images;
        inflater = context.getLayoutInflater();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)

        convertView = inflater.inflate(R.layout.road_signs, null, true);
        txtTitle = (TextView) convertView.findViewById(R.id.text);
        txtArticle = (TextView) convertView.findViewById(R.id.article);
        imageView = (ImageView) convertView.findViewById(R.id.image);
        txtTitle.setText(plates_textSign[position]);
        txtArticle.setText(plates_article[position]);

        try {
            list = context.getAssets().list("plates_images");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            InputStream ims = context.getAssets().open("plates_images/" + list[position]);
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}

