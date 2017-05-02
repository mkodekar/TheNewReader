package com.newsreader.thenewsreader.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;

import com.newsreader.thenewsreader.R;
import com.newsreader.thenewsreader.models.Source;
import com.newsreader.thenewsreader.viewholders.SourcesViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkodekar on 4/30/17.
 */

public class SourcesAdapter extends RecyclerView.Adapter<SourcesViewHolder> implements SectionIndexer {


    private ArrayList<Source> sources;
    private ArrayList<Integer> mSectionPositions;

    public SourcesAdapter(ArrayList<Source> sources) {
        this.sources = sources;
        notifyDataSetChanged();
    }

    @Override
    public SourcesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.source_row, null);
        return new SourcesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SourcesViewHolder holder, int position) {
        Source source = sources.get(position);
        holder.bind(source);
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = sources.size(); i < size; i++) {
            String section = String.valueOf(sources.get(i).getId().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}
