package com.autotest.pdd_test.utiles;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class Chapter implements Serializable {

    private String name;
    private Class<AppCompatActivity> classActivity;
    private int textId;

    public String getName() {
        return name;
    }

    public Class getClassActivity() {
        return classActivity;
    }

    public Chapter(String name) {
        this.name = name;
    }

    public Chapter(String name, Class classActivity) {
        this.name = name;
        this.classActivity = classActivity;
    }

    public Chapter(String name, int textId, Class classActivity) {
        this.name = name;
        this.textId = textId;
        this.classActivity = classActivity;
    }

    public int getTextId() {
        return textId;
    }

    public void setText(int textId) {
        this.textId = textId;
    }
}
