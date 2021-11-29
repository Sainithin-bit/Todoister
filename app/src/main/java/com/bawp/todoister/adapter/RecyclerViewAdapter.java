package com.bawp.todoister.adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.todoister.R;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.utils.util;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Task> taskList;
    private final OnTodoClickListener todoClickListener;

    public RecyclerViewAdapter(List<Task> taskList, OnTodoClickListener todoClickListener) {
        this.taskList = taskList;
        this.todoClickListener = todoClickListener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Task task=taskList.get(position);
        String formatted= util.formatDate(task.getDueDate());

        holder.task.setText(task.getTask());
        holder.todayChip.setText(formatted);



    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public Task onSwap(int adapterPosition) {
        Task currtask=taskList.get(adapterPosition);
        return currtask;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public AppCompatRadioButton radioButton;
        public AppCompatTextView task;
        public Chip todayChip;

        OnTodoClickListener onTodoClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton=itemView.findViewById(R.id.todo_radio_button);
            task=itemView.findViewById(R.id.todo_row_todo);
            todayChip=itemView.findViewById(R.id.todo_row_chip);
            this.onTodoClickListener=todoClickListener;

            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            Task currTask=taskList.get(getAdapterPosition());
            int id=view.getId();
            if (id==R.id.todo_row_layout){

                onTodoClickListener.onTodoClick(currTask);

            }else if (id==R.id.todo_radio_button){

                onTodoClickListener.onTodoRadioButtonClick(currTask);

            }

        }




    }


}
