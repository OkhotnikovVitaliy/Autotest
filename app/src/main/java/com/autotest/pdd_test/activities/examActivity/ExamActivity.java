package com.autotest.pdd_test.activities.examActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.autotest.pdd_test.R;
import com.autotest.pdd_test.fragments.FragmentExam;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import static com.autotest.pdd_test.activities.TicketsSheetActivity.NUM_OF_QUIZS;

public class ExamActivity extends ActivityTestExam {
    private static final int time = 1500000; //25 minutes
    private boolean isTimeOut;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_text);
        title.setText(getTitleName());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
        initTimer();
    }

    private void initTimer() {
        TextView timer = (TextView) findViewById(R.id.timer);
        timer.setText(getTime(time));
        countDownTimer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(getTime(millisUntilFinished));
            }

            public void onFinish() {
                timer.setText(getTime(0));
                isTimeOut = true;
                showAlertDialog(ExamActivity.this);
            }
        };
        countDownTimer.start();

    }

    @Override
    protected void stopAllProcesses() {
        countDownTimer.cancel();
    }

    @Override
    protected String getTitleName() {
        return getString(R.string.exam);
    }

    private String getTime(long millisUntilFinished) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished - TimeUnit.MINUTES.toMillis(minutes));
        return ((minutes >= 10 ? "" : "0") + String.valueOf(minutes)) + ":" +
                ((seconds >= 10 ? "" : "0") + String.valueOf(seconds));
    }

    @Override
    protected boolean isQuizSuccessfulDone() {
        return super.isQuizSuccessfulDone() && !isTimeOut;
    }

    @Override
    protected String getResultMessage() {
        return isQuizSuccessfulDone() ? "Сдан!" : "Не сдан!";
    }

    @Override
    protected int getLayoutAlertDialogId() {
        return R.layout.exam_alertdialog;
    }

    @Override
    protected void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 1; i <= NUM_OF_QUESTIONS; i++) {
            int quizNumber = ThreadLocalRandom.current().nextInt(1, NUM_OF_QUIZS + 1);
            int answersNumber = getResources().getIdentifier("ta" + quizNumber, "string", getPackageName());
            String[] answers = getResources().getString(answersNumber).split("\\s");
            int quiz = getResources().getIdentifier("t" + quizNumber + "_t" + i, "array", getPackageName());
            int comment = getResources().getIdentifier("c" + quizNumber + "_c" + i, "array", getPackageName());
            int answer = Integer.parseInt(answers[i - 1]);
            // answer - 1 because in the code we start from 0
            adapter.addFrag(FragmentExam.build(this, quiz, comment,answer - 1, quizNumber, i), String.valueOf(i));
        }
        viewPager.setAdapter(adapter);
    }



    @Override
    public void finish() {
        super.finish();
        countDownTimer.cancel();
    }
}
