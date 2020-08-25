package com.autotest.pdd_test.adapters;

import android.view.View;

import com.autotest.pdd_test.listeners.ChapterOnRecyclerViewItemClickListener;
import com.autotest.pdd_test.listeners.OnRecyclerViewItemClickListener;
import com.autotest.pdd_test.utiles.Chapter;

import java.util.List;

public class ChapterRecyclerViewAdapter extends RecyclerViewAdapter<Chapter> implements View.OnClickListener {

    public static final int chapterKey = 0x13427523;

    private ChapterOnRecyclerViewItemClickListener listener;

    public ChapterRecyclerViewAdapter(List<ViewModel> items, int itemLayout) {
        super(items, itemLayout);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        ViewModel item = getItems().get(position);
        holder.text.setText(item.getChapter().getName());
        holder.itemView.setTag(chapterKey, item.getChapter());
    }

    @Override public int getItemCount() {
        return getItems().size();
    }

    @Override
    public void setListener(OnRecyclerViewItemClickListener<Chapter> listener) {
        this.listener = (ChapterOnRecyclerViewItemClickListener) listener;
    }

    @Override
    public void onClick(View v) {
        v.animate().alpha(0.3f).withEndAction(() ->
                v.animate().alpha(1).withEndAction(() ->
                        listener.onItemClick((Chapter) v.getTag(chapterKey))));
    }
}
