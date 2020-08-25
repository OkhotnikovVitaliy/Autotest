package com.autotest.pdd_test.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class SQLiteCreator extends SQLiteOpenHelper {


    private static SQLiteCreator sqLiteCreator;

    private SQLiteCreator(Context context) {
        super(context, "PDD_TEST", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String CREATE_QUIZES_RESULT_TABLE = "CREATE TABLE QUIZES_RESULT(QUIZ_NUMBER INTEGER, RESULT INTEGER)";

        String CREATE_QUESTIONS_RESULTS_TABLE = "CREATE TABLE QUESTIONS_RESULTS(QUIZ_NUMBER INTEGER, QUESTION_NUMBER INTEGER, RESULT INTEGER)";
        db.execSQL(CREATE_QUIZES_RESULT_TABLE);
        db.execSQL(CREATE_QUESTIONS_RESULTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static SQLiteCreator getSqLiteCreator(Context context) {
        if (sqLiteCreator == null) {
            sqLiteCreator = new SQLiteCreator(context);
        }
        return sqLiteCreator;
    }
}
