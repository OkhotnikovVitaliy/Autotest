package com.autotest.pdd_test.databases;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Locale;
import java.util.logging.Logger;


public class QuizesResultDao {

    private Logger logger = Logger.getLogger(String.valueOf(SQLiteOpenHelper.class));

    private SQLiteCreator sqLiteCreator;
    public QuizesResultDao(Context context) {
        sqLiteCreator = SQLiteCreator.getSqLiteCreator(context);
    }

    public void addResult(int quizNumber, boolean result) {
        String INSERT_QUIZ_RESULT = "INSERT OR REPLACE INTO QUIZES_RESULT(QUIZ_NUMBER, RESULT) " +
                "VALUES('" + quizNumber + "'," + (result ? "'1'" : "'0'") + ")";
        try (SQLiteDatabase db = sqLiteCreator.getWritableDatabase()) {
            db.execSQL(INSERT_QUIZ_RESULT);
        } catch (SQLException e) {
            logger.warning(String.format(Locale.getDefault(),
                    "Quiz number : %d, result: %b. %s", quizNumber, result, e.getMessage()));
        }
    }

    public int getResultByQuiz(int quizNumber) {

        SQLiteDatabase db = sqLiteCreator.getWritableDatabase();
        String INSERT_QUIZ_RESULT = "SELECT QR.RESULT FROM QUIZES_RESULT QR WHERE QR.QUIZ_NUMBER='" + quizNumber + "'";
        Cursor cursor = db.rawQuery(INSERT_QUIZ_RESULT, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        cursor.close();

       db.close();
        return -1;
    }
}
