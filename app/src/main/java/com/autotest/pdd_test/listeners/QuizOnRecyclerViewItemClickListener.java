package com.autotest.pdd_test.listeners;

import android.widget.TextView;

import java.util.List;

public interface QuizOnRecyclerViewItemClickListener extends OnRecyclerViewItemClickListener<List<TextView>> {
    void onItemClick(List<TextView> tag, Integer position);
}