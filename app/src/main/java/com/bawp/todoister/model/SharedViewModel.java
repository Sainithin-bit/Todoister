package com.bawp.todoister.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Task> selectedItem=new MutableLiveData<Task>();
    private boolean IsEdit;

    public void selectItem(Task task){
        selectedItem.setValue(task);
    }

    public LiveData<Task> getSelectedItem(){

        return selectedItem;
    }

    public boolean getIsEdit() {
        return IsEdit;
    }

    public void setIsEdit(boolean edit) {
        this.IsEdit = edit;
    }
}
