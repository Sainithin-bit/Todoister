package com.bawp.todoister.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

public class util {
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat= (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE, MMM d");
        return simpleDateFormat.format(date);
    }

    public static void hideSoftKeyboard(View view){
        InputMethodManager imm=(InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
