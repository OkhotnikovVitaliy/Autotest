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

public class CustomListAdapterProhibition extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] prohibition_textSign;
    private final String[] prohibition_article;
    private final int prohibition_images;
    private String[] list;
    LayoutInflater inflater;
    TextView txtTitle;
    TextView txtArticle;
    ImageView imageView;

    public CustomListAdapterProhibition(Activity context, String[] prohibition_textSign, String[] prohibition_article, int prohibition_images) {
        super(context, R.layout.road_signs, prohibition_textSign);
        this.context = context;
        this.prohibition_textSign = prohibition_textSign;
        this.prohibition_article = prohibition_article;
        this.prohibition_images = prohibition_images;
        inflater = context.getLayoutInflater();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)

        convertView = inflater.inflate(R.layout.road_signs, null, true);
        txtTitle = (TextView) convertView.findViewById(R.id.text);
        txtArticle = (TextView) convertView.findViewById(R.id.article);
        imageView = (ImageView) convertView.findViewById(R.id.image);
        txtTitle.setText(prohibition_textSign[position]);
        txtArticle.setText(prohibition_article[position]);

        try {
            list = context.getAssets().list("prohibition_images");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            InputStream ims = context.getAssets().open("prohibition_images/" + list[position]);
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
