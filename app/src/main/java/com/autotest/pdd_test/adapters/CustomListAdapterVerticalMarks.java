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

public class CustomListAdapterVerticalMarks extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] vertical_marks_article;
    private final int vertical_marks_images;
    private String[] list;
    LayoutInflater inflater;
    TextView txtArticle;
    public ImageView imageView;
    public Drawable d;

    public CustomListAdapterVerticalMarks(Activity context, String[] vertical_marks_article, int vertical_marks_images) {
        super(context, R.layout.road_marks, vertical_marks_article);
        this.context = context;

        this.vertical_marks_article = vertical_marks_article;
        this.vertical_marks_images = vertical_marks_images;
        inflater = context.getLayoutInflater();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)

            convertView = inflater.inflate(R.layout.road_marks, null, true);

        txtArticle = (TextView) convertView.findViewById(R.id.article);
        imageView = (ImageView) convertView.findViewById(R.id.mark_image);

        txtArticle.setText(vertical_marks_article[position]);

        try {
            list = context.getAssets().list("vertical_marks_images");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            InputStream ims = context.getAssets().open("vertical_marks_images/" + list[position]);
            d = Drawable.createFromStream(ims, null);
            ims.close();
            imageView.setImageDrawable(d);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}