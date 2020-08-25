package com.autotest.pdd_test.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.autotest.pdd_test.activities.testActivity.ActivityTest;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Logger;

public class QuestionsResultsDao {

    private Logger logger = Logger.getLogger(String.valueOf(QuestionsResultsDao.class));
    private SQLiteCreator sqLiteCreator;

    public QuestionsResultsDao(Context context) {
        sqLiteCreator = SQLiteCreator.getSqLiteCreator(context);
    }

    public void addResult(int quizNumber, boolean results[]) {
        StringBuilder INSERT_QUIZ_RESULT = new StringBuilder("INSERT INTO QUESTIONS_RESULTS(QUIZ_NUMBER, QUESTION_NUMBER, RESULT) VALUES");
        for (int i = 0; i < ActivityTest.NUM_OF_QUESTIONS; i++) {
            INSERT_QUIZ_RESULT.append("('").append(quizNumber).append("','")
                    .append(i).append("',").append(results[i] ? "'1'" : "'0'").append("),");
        }
        INSERT_QUIZ_RESULT.deleteCharAt(INSERT_QUIZ_RESULT.length() - 1);
        try (SQLiteDatabase db = sqLiteCreator.getWritableDatabase()) {
            db.execSQL(INSERT_QUIZ_RESULT.toString());
        } catch (SQLException e) {
            logger.warning(String.format(Locale.getDefault(),
                    "Quiz number : %d, results: %b. %s", quizNumber, Arrays.toString(results), e.getMessage()));
        }
    }

    public int getNumberOfIncorrectAnswersByQuestion(int quizNumber, int questionNumber) {
        return getNumberOfAnswersByQuestion(quizNumber, questionNumber, 0);
    }

    public int getNumberOfCorrectAnswersByQuestion(int quizNumber, int questionNumber) {
        return getNumberOfAnswersByQuestion(quizNumber, questionNumber, 1);
    }

    private int getNumberOfAnswersByQuestion(int quizNumber, int questionNumber, int result) {
        SQLiteDatabase db = sqLiteCreator.getWritableDatabase();
        String SELECT_QUIZ_RESULT = "SELECT COUNT(*) FROM QUESTIONS_RESULTS QR WHERE QR.QUIZ_NUMBER='" + quizNumber + "' AND " +
                "QR.QUESTION_NUMBER=" + "'" + questionNumber + "' AND RESULT = '" + result + "'";
        Cursor cursor = db.rawQuery(SELECT_QUIZ_RESULT, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return 0;
    }



}