package com.bawp.todoister.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetBitmapfromURL {

    public static Bitmap getbitmap(String src){
        try {
            URL url = new URL(src);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream inputStream= conn.getInputStream();
            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }catch (IOException e){
            e.printStackTrace();
            return null;

        }


    }
}
