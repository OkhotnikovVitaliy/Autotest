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

public class CustomListAdapterService extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] service_textSign;
    private final String[] service_article;
    private final int service_images;
    private String[] list;
    LayoutInflater inflater;
    TextView txtTitle;
    TextView txtArticle;
    ImageView imageView;

    public CustomListAdapterService(Activity context, String[] service_textSign, String[] service_article, int service_images) {
        super(context, R.layout.road_signs, service_textSign);
        this.context = context;
        this.service_textSign = service_textSign;
        this.service_article = service_article;
        this.service_images = service_images;
        inflater = context.getLayoutInflater();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)

        convertView = inflater.inflate(R.layout.road_signs, null, true);
        txtTitle = (TextView) convertView.findViewById(R.id.text);
        txtArticle = (TextView) convertView.findViewById(R.id.article);
        imageView = (ImageView) convertView.findViewById(R.id.image);
        txtTitle.setText(service_textSign[position]);
        txtArticle.setText(service_article[position]);

        try {
            list = context.getAssets().list("service_images");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            InputStream ims = context.getAssets().open("service_images/" + list[position]);
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
