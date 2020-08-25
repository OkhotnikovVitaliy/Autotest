package com.autotest.pdd_test.adapters;

import android.view.View;
import android.widget.TextView;

import com.autotest.pdd_test.fragments.FragmentExam;
import com.autotest.pdd_test.listeners.OnRecyclerViewItemClickListener;
import com.autotest.pdd_test.listeners.QuizOnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class QuizRecyclerViewAdapterExam extends RecyclerViewAdapter<List<TextView>> implements View.OnClickListener {

    public static final int chapterKey = 0x13427523;
    public static final int positionKey = 0x16167979;

    private FragmentExam fragment;
    private QuizOnRecyclerViewItemClickListener listener;
    private List<TextView> textViews = new ArrayList<>();

    public QuizRecyclerViewAdapterExam(List<ViewModel> items, int itemLayout, FragmentExam fragment) {
        super(items, itemLayout);
        this.fragment = fragment;
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        ViewModel item = getItems().get(position);
        holder.text.setText(item.getChapter().getName());
        holder.itemView.setTag(chapterKey, item.getChapter());
        holder.itemView.setTag(positionKey, position);
        textViews.add(holder.text);
        if (fragment != null) {
            fragment.setCorrectColors(holder.text, position);
        }
    }

    @Override
    public void setListener(OnRecyclerViewItemClickListener<List<TextView>> listener) {
        this.listener = (QuizOnRecyclerViewItemClickListener) listener;
    }

    @Override
    public void onClick(View v) {
        v.animate().alpha(0.3f).withEndAction(() ->
                v.animate().alpha(1).withEndAction(() ->
                        listener.onItemClick(textViews, (Integer) v.getTag(positionKey))));
    }

}
