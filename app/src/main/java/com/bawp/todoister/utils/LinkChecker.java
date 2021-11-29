package com.bawp.todoister.utils;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.*;

public class LinkChecker{

    public static int executeLink(@NonNull URL url) throws IOException {
        HttpURLConnection conn=null;

        conn=(HttpURLConnection)url.openConnection();

        conn.connect();

        int statusCode=conn.getResponseCode();
        return statusCode;
    }


}
