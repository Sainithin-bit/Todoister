package com.bawp.todoister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.bawp.todoister.adapter.OnBookmarkClickListener;
import com.bawp.todoister.adapter.RecyclerViewAdapter;
import com.bawp.todoister.adapter.RecyclerViewAdapterBookmark;
import com.bawp.todoister.model.Bookmark;
import com.bawp.todoister.model.TaskViewModel;
import com.bawp.todoister.utils.LinkChecker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.net.URL;

public class MainActivity2 extends AppCompatActivity implements OnBookmarkClickListener {

    private static final String TAG="ITEM";
    private RecyclerViewAdapterBookmark recyclerViewAdapterBookmark;
    private RecyclerView recyclerView;
    private TaskViewModel taskViewModel;
    BottomSheetFragmentBookmark bottomSheetFragmentBookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        taskViewModel=new ViewModelProvider.AndroidViewModelFactory(MainActivity2.this.getApplication()).create(TaskViewModel.class);

        bottomSheetFragmentBookmark=new BottomSheetFragmentBookmark();
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskViewModel.getAllBookmarks().observe(this, (bookmarks -> {
            recyclerViewAdapterBookmark=new RecyclerViewAdapterBookmark(bookmarks, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(recyclerViewAdapterBookmark);
        }));



        FloatingActionButton fab=findViewById(R.id.fab1);
        fab.setOnClickListener(view -> {
//            try {
//                Log.d("Response", String.valueOf(LinkChecker.executeLink(new URL("https://towardsdatascience.com/neural-networks-to-predict-the-market-c4861b649371"))));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            showBottomSheetDialog();
        });


    }

    public boolean showBottomSheetDialog(){
        bottomSheetFragmentBookmark.show(getSupportFragmentManager(), bottomSheetFragmentBookmark.getTag());
        return true;
    }


    @Override
    public void onClickBookmark(Bookmark bookmark) {

        Uri webpage=Uri.parse(bookmark.getUrl());
        Intent webIntent=new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }


    ItemTouchHelper.SimpleCallback itemTouchHelperCallback=new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            TaskViewModel.delete(recyclerViewAdapterBookmark.onSwap(viewHolder.getAdapterPosition()));
            recyclerViewAdapterBookmark.notifyDataSetChanged();
        }
    };
}