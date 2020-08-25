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

public class CustomListAdapterPriority extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] priority_textSign;
    private final String[] priority_article;
    private final int priority_images;
    private String[] list;
    LayoutInflater inflater;
    TextView txtTitle;
    TextView txtArticle;
    ImageView imageView;

    public CustomListAdapterPriority(Activity context, String[] priority_textSign, String[] priority_article, int priority_images) {
        super(context, R.layout.road_signs, priority_textSign);
        this.context = context;
        this.priority_textSign = priority_textSign;
        this.priority_article = priority_article;
        this.priority_images = priority_images;
        inflater = context.getLayoutInflater();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)

        convertView = inflater.inflate(R.layout.road_signs, null, true);
        txtTitle = (TextView) convertView.findViewById(R.id.text);
        txtArticle = (TextView) convertView.findViewById(R.id.article);
        imageView = (ImageView) convertView.findViewById(R.id.image);
        txtTitle.setText(priority_textSign[position]);
        txtArticle.setText(priority_article[position]);

        try {
            list = context.getAssets().list("priority_images");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            InputStream ims = context.getAssets().open("priority_images/" + list[position]);
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}