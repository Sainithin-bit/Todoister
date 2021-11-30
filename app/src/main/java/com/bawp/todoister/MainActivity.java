package com.bawp.todoister;

import android.content.Intent;
import android.os.Bundle;

import com.bawp.todoister.adapter.OnTodoClickListener;
import com.bawp.todoister.adapter.RecyclerViewAdapter;
import com.bawp.todoister.model.SharedViewModel;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.model.TaskViewModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements OnTodoClickListener {

    private static final String TAG="ITEM";
    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int counter=0;
    private SharedViewModel sharedViewModel;
    BottomSheetFragmentTask bottomSheetFragmentTask;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        bottomSheetFragmentTask =new BottomSheetFragmentTask();
        ConstraintLayout constraintLayout=findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior=BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);


        drawerLayout=findViewById(R.id.drawer_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        taskViewModel=new ViewModelProvider.AndroidViewModelFactory(
          MainActivity.this.getApplication())
                .create(TaskViewModel.class);


        sharedViewModel=new ViewModelProvider(this)
                .get(SharedViewModel.class);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskViewModel.getAllTasks().observe(this, (tasks) -> {
            recyclerViewAdapter =new RecyclerViewAdapter(tasks, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(recyclerViewAdapter);

        });






        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {


//            Task task=new Task("Todo"+ counter++, Priority.HIGH, Calendar.getInstance().getTime(),Calendar.getInstance().getTime(), false);
//            TaskViewModel.insert(task);

//            Bookmark bookmark=new Bookmark("Google_search", "https://www.google.com/");
//            TaskViewModel.insert(bookmark);
            showBottomSheetDialog();


        });


    }



    private void showBottomSheetDialog(){
        bottomSheetFragmentTask.show(getSupportFragmentManager(), bottomSheetFragmentTask.getTag());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        }
        else if(id==R.id.Logout){
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(task -> {
                        finish();
                    });;
        }
        else if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTodoClick(Task task) {

        sharedViewModel.selectItem(task);
        sharedViewModel.setIsEdit(true);
        showBottomSheetDialog();
    }

    @Override
    public void onTodoRadioButtonClick(Task task) {

        TaskViewModel.delete(task);

    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback=new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            TaskViewModel.delete(recyclerViewAdapter.onSwap(viewHolder.getAdapterPosition()));
            recyclerViewAdapter.notifyDataSetChanged();
        }
    };
}