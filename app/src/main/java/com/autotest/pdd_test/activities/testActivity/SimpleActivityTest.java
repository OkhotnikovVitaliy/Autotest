package com.autotest.pdd_test.activities.testActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.autotest.pdd_test.R;
import com.autotest.pdd_test.databases.QuestionsResultsDao;
import com.autotest.pdd_test.databases.QuizesResultDao;
import com.autotest.pdd_test.fragments.FragmentTest;
import java.util.Locale;
import static com.autotest.pdd_test.activities.TicketsSheetActivity.QUIZ_NUMBER;


public class SimpleActivityTest extends ActivityTest  {
    private int quizNumber;
    private QuizesResultDao quizResultDao;
    private QuestionsResultsDao questionsResultsDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        quizResultDao = new QuizesResultDao(this);
        questionsResultsDao = new QuestionsResultsDao(this);
        quizNumber = getIntent().getIntExtra(QUIZ_NUMBER, 1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_text);
        title.setText(String.format(Locale.getDefault(),
                getString(R.string.quiz_format), quizNumber));

        initViews();
    }

    protected void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        int answersNumber = getResources().getIdentifier("ta" + quizNumber, "string", getPackageName());
        String[] answers = getResources().getString(answersNumber).split("\\s");
        for (int i = 1; i <= NUM_OF_QUESTIONS; i++) {
            int quiz = getResources().getIdentifier("t" + quizNumber + "_t" + i, "array", getPackageName());
            int comment = getResources().getIdentifier("c" + quizNumber + "_c" + i, "array", getPackageName());
            int answer = Integer.parseInt(answers[i - 1]);
            // answer - 1 because in the code we start from 0
            adapter.addFrag(FragmentTest.build(this, comment,quiz, answer - 1, quizNumber, i), String.valueOf(i));
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    protected View getAlertDialogView(String message, int alertTextColor) {
        View alertDialogView = super.getAlertDialogView(message, alertTextColor);
        Button next = (Button) alertDialogView.findViewById(R.id.next_quiz);
        next.setOnClickListener(v -> finish());
        return alertDialogView;
    }

    @Override
    protected String getTitleName() {
        return String.format(Locale.getDefault(), getString(R.string.quiz_format), quizNumber);
    }

    @Override
    protected String getResultMessage() {
        return isQuizSuccessfulDone() ? "Пройден успешно!" : "Не пройден!";
    }

    @Override
    protected int getLayoutAlertDialogId() {
        return R.layout.alert_dialog;
    }

    @Override
    protected void stopAllProcesses() {
        quizResultDao.addResult(quizNumber, isQuizSuccessfulDone());
       questionsResultsDao.addResult(quizNumber, answers);
    }


}