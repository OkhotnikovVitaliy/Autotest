package com.autotest.pdd_test.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.autotest.pdd_test.R;
import com.autotest.pdd_test.activities.testActivity.ActivityTest;
import com.autotest.pdd_test.activities.testActivity.SimpleActivityTest;
import com.autotest.pdd_test.adapters.QuizRecyclerViewAdapter;
import com.autotest.pdd_test.adapters.RecyclerViewAdapter;
import com.autotest.pdd_test.listeners.QuizOnRecyclerViewItemClickListener;
import com.autotest.pdd_test.utiles.Chapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class FragmentTest extends Fragment {

    private ActivityTest parent;
    private int quizNumber;
    private int quizRecourse;
    private int questionNumber;
    private int answersNumber;
    private int comments;
    private View inflate;
    private HashMap<Integer, Integer> colorMap = new HashMap<>();
    private RecyclerView chapters;


    public FragmentTest() {
    }


    public static FragmentTest build(ActivityTest parent, int comment, int quizRecourse, int answersNumber, int quizNumber, int questionNumber) {
        FragmentTest quizFragment = new FragmentTest();
        quizFragment.setParent(parent);
        quizFragment.setQuizRecourse(quizRecourse);
        quizFragment.setQuestionNumber(questionNumber);
        quizFragment.setQuizNumber(quizNumber);
        quizFragment.setAnswersNumber(answersNumber);
        quizFragment.setComment(comment);
        return quizFragment;
    }

    public void setParent(ActivityTest parent) {
        this.parent = parent;
    }

    public void setQuizRecourse(int quizRecourse) {
        this.quizRecourse = quizRecourse;
    }

    public void setAnswersNumber(int answersNumber) {
        this.answersNumber = answersNumber;
    }

    public void setQuizNumber(int quizNumber) {
        this.quizNumber = quizNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void setComment(int comment) {
        this.comments = comment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflate = inflater.inflate(R.layout.fragment_test, container, false);
        Button goToNextQuestion = inflate.findViewById(R.id.next_button);
        goToNextQuestion.setVisibility(View.GONE);

        goToNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    ActivityTest activityTest = (ActivityTest) getActivity();
                    activityTest.nextQuestion();
                }
            }
        });

        drawQuiz();
        return inflate;
    }


    private void initImage() {
        ImageView image = (ImageView) inflate.findViewById(R.id.question_image);
        try {
            String imageName = "t" + quizNumber + "_t" + questionNumber + ".jpg";
            Drawable d = Drawable.createFromStream(parent.getAssets().open(imageName), null);
            image.setImageDrawable(d);
        } catch (IOException e) {
            image.setLayoutParams(new FrameLayout.LayoutParams(0, 0));
        }
    }

    private void initQuestion(String[] quiz) {
        TextView question = (TextView) inflate.findViewById(R.id.question);
        question.setText(quiz[0]);

    }

    private void initComment(String[] comment) {
        TextView comment_field = (TextView) inflate.findViewById(R.id.comment);
        comment_field.setText(comment[0]);

    }

    private List<RecyclerViewAdapter.ViewModel> getQuestions(String[] quiz) {
        List<RecyclerViewAdapter.ViewModel> questions = new ArrayList<>();
        for (int i = 1; i < quiz.length; i++) {
            questions.add(new RecyclerViewAdapter.ViewModel(i, new Chapter(quiz[i])));
        }
        return questions;
    }

    private void initAnswers(String[] quiz) {
        chapters = (RecyclerView) inflate.findViewById(R.id.answers);
        chapters.setNestedScrollingEnabled(false);

        final ScrollView scrollview = ((ScrollView) inflate.findViewById(R.id.scrollView));
        chapters.setHasFixedSize(true);
        RecyclerViewAdapter<List<TextView>> recyclerViewAdapter = new QuizRecyclerViewAdapter(getQuestions(quiz), R.layout.question, this);
        chapters.setLayoutManager(new GridLayoutManager(parent, 1));
        chapters.setAdapter(recyclerViewAdapter);
        chapters.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAdapter.setListener((QuizOnRecyclerViewItemClickListener) (textViews, position) -> {

            if (colorMap.isEmpty()) {
                TextView currentTextView = textViews.get(position);
                TextView correctTextView = textViews.get(answersNumber);
                Button goToNextQuestion = inflate.findViewById(R.id.next_button);
                int color = R.color.correctAnswer;
                if (position != answersNumber) {
                    color = R.color.incorrectAnswer;
                    scrollview.postDelayed((new Runnable() {
                        @Override
                        public void run() {
                            scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    }), 500);
                    goToNextQuestion.setVisibility(View.VISIBLE);
                }
                setColorForAnswer(currentTextView, color);
                setColorForAnswer(correctTextView, R.color.correctAnswer);
                colorMap.put(position, color);
                colorMap.put(answersNumber, R.color.correctAnswer);
                parent.resultListener(position == answersNumber, questionNumber);
            }
        });
        TextView comment_field = (TextView) inflate.findViewById(R.id.comment);
        TextView hint = (TextView)inflate.findViewById(R.id.hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleActivityTest simpleActivityTest = (SimpleActivityTest) getActivity();
                if (!simpleActivityTest.fab.isEnabled()) {
                    scrollview.postDelayed((new Runnable() {
                        @Override
                        public void run() {
                            scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    }), 200);
                    comment_field.setVisibility(View.VISIBLE);
                }
                else{
                    showHintAlert();
                }
            }
        });
    }
    private void showHintAlert(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Подсказки");
        alertDialog.setMessage("Необходимо активировать подсказки");
        alertDialog.setIcon(R.drawable.star);
        alertDialog.setNegativeButton("ЗАКРЫТЬ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void drawQuiz() {

        String[] quiz = parent.getResources().getStringArray(quizRecourse);
       String[] comment = parent.getResources().getStringArray(comments);
        initImage();
        initQuestion(quiz);
        initAnswers(quiz);
        initComment(comment);

    }

    public void setCorrectColors(TextView textView, int position) {
        if (colorMap.get(position) != null) {
            setColorForAnswer(textView, colorMap.get(position));
        }
    }

    private void setColorForAnswer(TextView textView, int color) {
        textView.setBackgroundColor(parent.getResources().getColor(color, parent.getTheme()));
        ((LinearLayout) textView.getParent()).setBackgroundColor(parent.getResources().getColor(color, parent.getTheme()));
    }

}