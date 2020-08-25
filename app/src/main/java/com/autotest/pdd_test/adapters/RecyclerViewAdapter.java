package com.autotest.pdd_test.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.autotest.pdd_test.R;
import com.autotest.pdd_test.listeners.OnRecyclerViewItemClickListener;
import com.autotest.pdd_test.utiles.Chapter;

import java.util.List;

public abstract class RecyclerViewAdapter<Tag> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
        implements View.OnClickListener {

    private List<ViewModel> items;
    private int itemLayout;

    public RecyclerViewAdapter(List<ViewModel> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public abstract void onBindViewHolder(ViewHolder holder, int position);

    @Override public int getItemCount() {
        return items.size();
    }

    public abstract void setListener(OnRecyclerViewItemClickListener<Tag> listener);

    @Override
    public abstract void onClick(View v);

    public List<ViewModel> getItems() {
        return items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.chapter_name);
        }
    }

    public static class ViewModel {

        private long id;
        private Chapter chapter;

        public ViewModel(long id, Chapter chapter) {
            this.id = id;
            this.chapter = chapter;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }


        public Chapter getChapter() {
            return chapter;
        }

        public void setChapter(Chapter chapter) {
            this.chapter = chapter;
        }
    }
}