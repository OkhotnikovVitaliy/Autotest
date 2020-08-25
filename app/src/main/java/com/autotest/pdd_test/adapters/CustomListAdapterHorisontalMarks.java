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

public class CustomListAdapterHorisontalMarks extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] horisontal_marks_article;
    private final int horisontal_marks_images;
    private String[] list;
    LayoutInflater inflater;
    TextView txtArticle;
    public ImageView imageView;
    public Drawable d;

    public CustomListAdapterHorisontalMarks(Activity context, String[] horisontal_marks_article, int horisontal_marks_images) {
        super(context, R.layout.road_marks, horisontal_marks_article);
        this.context = context;

        this.horisontal_marks_article = horisontal_marks_article;
        this.horisontal_marks_images = horisontal_marks_images;
        inflater = context.getLayoutInflater();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)

            convertView = inflater.inflate(R.layout.road_marks, null, true);

        txtArticle = (TextView) convertView.findViewById(R.id.article);
        imageView = (ImageView) convertView.findViewById(R.id.mark_image);

        txtArticle.setText(horisontal_marks_article[position]);

        try {
            list = context.getAssets().list("horisontal_marks_images");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            InputStream ims = context.getAssets().open("horisontal_marks_images/" + list[position]);
            d = Drawable.createFromStream(ims, null);
            ims.close();
            imageView.setImageDrawable(d);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}