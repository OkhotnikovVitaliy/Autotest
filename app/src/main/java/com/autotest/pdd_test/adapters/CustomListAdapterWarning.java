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

public class CustomListAdapterWarning extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] warning_textSign;
    private final String[] warning_article;
    private final int warning_images;
    private String[] list;
    LayoutInflater inflater;
    TextView txtTitle;
    TextView txtArticle;
   public ImageView imageView;
    public Drawable d;

    public CustomListAdapterWarning(Activity context, String[] warning_textSign, String[] warning_article, int warning_images) {
        super(context, R.layout.road_signs, warning_textSign);
        this.context = context;
        this.warning_textSign = warning_textSign;
        this.warning_article = warning_article;
        this.warning_images = warning_images;
        inflater = context.getLayoutInflater();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)

        convertView = inflater.inflate(R.layout.road_signs, null, true);
        txtTitle = (TextView) convertView.findViewById(R.id.text);
        txtArticle = (TextView) convertView.findViewById(R.id.article);
        imageView = (ImageView) convertView.findViewById(R.id.image);
        txtTitle.setText(warning_textSign[position]);
        txtArticle.setText(warning_article[position]);

        try {
            list = context.getAssets().list("warning_signs");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            InputStream ims = context.getAssets().open("warning_signs/" + list[position]);
            d = Drawable.createFromStream(ims, null);
            ims.close();
            imageView.setImageDrawable(d);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}