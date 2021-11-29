package com.bawp.todoister.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.todoister.R;
import com.bawp.todoister.model.Bookmark;
import com.bawp.todoister.model.Task;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdapterBookmark extends RecyclerView.Adapter<RecyclerViewAdapterBookmark.ViewHolder>{
    private final List<Bookmark> bookmarks;
    private final OnBookmarkClickListener bookmarkClickListener;
    public RecyclerViewAdapterBookmark(List<Bookmark> bookmarks, OnBookmarkClickListener bookmarkClickListener) {
        this.bookmarks=bookmarks;
        this.bookmarkClickListener=bookmarkClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterBookmark.ViewHolder holder, int position) {

        Bookmark bookmark=bookmarks.get(position);

        holder.name.setText(bookmark.getName());
        holder.url.setText(bookmark.getUrl());
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }

    public Bookmark onSwap(int adapterPosition) {
        Bookmark bookmark=bookmarks.get(adapterPosition);
        return bookmark;

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public TextView url;
        OnBookmarkClickListener onBookmarkClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            url=itemView.findViewById(R.id.url);


            this.onBookmarkClickListener=bookmarkClickListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Bookmark bookmark=bookmarks.get(getAdapterPosition());
            int id=view.getId();
            if (id==R.id.bookmark_row){
                onBookmarkClickListener.onClickBookmark(bookmark);
            }

        }
    }
}

