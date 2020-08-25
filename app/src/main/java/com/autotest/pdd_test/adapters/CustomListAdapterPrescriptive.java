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

public class CustomListAdapterPrescriptive extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] prescriptive_textSign;
    private final String[] prescriptive_article;
    private final int prescriptive_images;
    private String[] list;
    LayoutInflater inflater;
    TextView txtTitle;
    TextView txtArticle;
    ImageView imageView;

    public CustomListAdapterPrescriptive(Activity context, String[] prescriptive_textSign, String[] prescriptive_article, int prescriptive_images) {
        super(context, R.layout.road_signs, prescriptive_textSign);
        this.context = context;
        this.prescriptive_textSign = prescriptive_textSign;
        this.prescriptive_article = prescriptive_article;
        this.prescriptive_images = prescriptive_images;
        inflater = context.getLayoutInflater();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)

        convertView = inflater.inflate(R.layout.road_signs, null, true);
        txtTitle = (TextView) convertView.findViewById(R.id.text);
        txtArticle = (TextView) convertView.findViewById(R.id.article);
        imageView = (ImageView) convertView.findViewById(R.id.image);
        txtTitle.setText(prescriptive_textSign[position]);
        txtArticle.setText(prescriptive_article[position]);

        try {
            list = context.getAssets().list("prescriptive_images");

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            InputStream ims = context.getAssets().open("prescriptive_images/" + list[position]);
            Drawable d = Drawable.createFromStream(ims, null);
            ims.close();
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
