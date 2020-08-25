package com.autotest.pdd_test.listeners;

import com.autotest.pdd_test.utiles.Chapter;

public interface ChapterOnRecyclerViewItemClickListener extends OnRecyclerViewItemClickListener<Chapter> {
    void onItemClick(Chapter tag);
}