package com.bawp.todoister;

import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bawp.todoister.model.Priority;
import com.bawp.todoister.model.SharedViewModel;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.model.TaskViewModel;
import com.bawp.todoister.utils.util;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragmentTask extends BottomSheetDialogFragment implements View.OnClickListener {

    private EditText enterTodo;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private ImageButton savedButton;
    private CalendarView calendarView;
    private Group calendargroup;
    private Date dueDate;
    Calendar calendar=Calendar.getInstance();
    private SharedViewModel sharedViewModel;
    private boolean isEdit;
    Priority priority;

    public BottomSheetFragmentTask() {


    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.bottom_sheet, container, false);

        calendargroup=view.findViewById(R.id.calendar_group);
        calendarView=view.findViewById(R.id.calendar_view);
        calendarButton=view.findViewById(R.id.today_calendar_button);
        enterTodo=view.findViewById(R.id.enter_todo_et);
        savedButton=view.findViewById(R.id.save_todo_button);
        priorityButton=view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup=view.findViewById(R.id.radioGroup_priority);


        Chip todayChip=view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip=view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextweekChip=view.findViewById(R.id.next_week_chip);
        nextweekChip.setOnClickListener(this);


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        if (sharedViewModel.getSelectedItem().getValue()!=null){
            isEdit=sharedViewModel.getIsEdit();
            Task task=sharedViewModel.getSelectedItem().getValue();
            enterTodo.setText(task.getTask());
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel=new ViewModelProvider(requireActivity())
                .get(SharedViewModel.class);

        calendarButton.setOnClickListener(view12 -> {
            calendargroup.setVisibility(
                    calendargroup.getVisibility()==View.GONE ? View.VISIBLE : View.GONE
            );
            util.hideSoftKeyboard(view12);
        });

        priorityButton.setOnClickListener(view13 -> {


            util.hideSoftKeyboard(view13);
            priorityRadioGroup.setVisibility(
                    priorityRadioGroup.getVisibility()==View.GONE? View.VISIBLE : View.GONE
            );
            priorityRadioGroup.setOnCheckedChangeListener((radioGroup, checkedID)->{

                if(priorityRadioGroup.getVisibility()==View.VISIBLE){
                    selectedButtonId=checkedID;
                    selectedRadioButton=view.findViewById(selectedButtonId);
                    if (selectedRadioButton.getId()==R.id.radioButton_high){
                        priority = Priority.HIGH;
                    }else if(selectedRadioButton.getId()==R.id.radioButton_med){
                        priority = Priority.MEDIUM;
                    }
                    else if(selectedRadioButton.getId()==R.id.radioButton_low)
                    {
                        priority=Priority.LOW;
                    }
                    else{
                        priority=Priority.LOW;
                    }

                }
                else{
                    priority=Priority.LOW;
                }

            });
        });

        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMoth)->{
            calendar.clear();
            calendar.set(year, month, dayOfMoth);
            dueDate=calendar.getTime();
        });

        savedButton.setOnClickListener(view1 -> {
                String task=enterTodo.getText().toString();
                if (!TextUtils.isEmpty(task) && dueDate!=null && priority!=null){
                    Task mytask=new Task(task, priority, dueDate , Calendar.getInstance().getTime(), false);

                    if(isEdit){
                        Task updateTask=sharedViewModel.getSelectedItem().getValue();
                        updateTask.setTask(task);
                        updateTask.setDateCreated(Calendar.getInstance().getTime());
                        updateTask.setPriority(priority);
                        updateTask.setDueDate(dueDate);
                        TaskViewModel.update(updateTask);
                        sharedViewModel.setIsEdit(false);
                    }else{

                        TaskViewModel.insert(mytask);

                    }
                    enterTodo.setText("");
                    if(this.isVisible()){
                        this.dismiss();
                    }
                }else{
                    Snackbar.make(savedButton, R.string.empty_field, Snackbar.LENGTH_LONG).show();
                }

        });

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.today_chip){
            calendar.clear();
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            dueDate=calendar.getTime();
        }
        else if(id==R.id.tomorrow_chip){
            calendar.clear();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dueDate=calendar.getTime();
        }
        else if (id==R.id.next_week_chip){
            calendar.clear();
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            dueDate=calendar.getTime();
        }
    }
}