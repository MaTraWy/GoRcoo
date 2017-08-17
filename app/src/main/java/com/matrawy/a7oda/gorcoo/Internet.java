package com.matrawy.a7oda.gorcoo;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 7oda on 7/29/2017.
 */

public class Internet {
    public static Context context;
    public static String URLL;
    public static boolean Internet_flag;
    public static boolean Cloud_flag = false;
    public static Login_Fragment log=null;
    public static ProgressDialog progress;
    public static String Version;
    public static void isOnline() {
        if(progress!=null)
            progress.setMessage("Checking Server...");
//        progress.show();
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        Internet_flag = netInfo != null && netInfo.isConnectedOrConnecting();
        IsCloudOnline();
    }
    public static void isOnline2(CallBack mm) {
        if(progress!=null)
            progress.setMessage("Checking Server...");
//        progress.show();
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        Internet_flag = netInfo != null && netInfo.isConnectedOrConnecting();
        IsCloudOnline2(mm);
    }
    public static void IsCloudOnline2(final CallBack mm) {
        Thread m = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(URLL).openConnection();
                    int responseCode = httpUrlConnection.getResponseCode();
                    Cloud_flag = responseCode == HttpURLConnection.HTTP_OK;
                    mm.callBackFunc(0);
                } catch (IOException e) {
                    Log.e("Internet Exception 22", e.toString());
                    Cloud_flag =false;
                    mm.callBackFunc(1);
                }
            }
        });
        m.start();
    }
    public static void IsCloudOnline() {
        Thread m = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("Internet", URLL);
                    HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(URLL).openConnection();
                    //httpUrlConnection.setRequestMethod("HEAD");
                    int responseCode = httpUrlConnection.getResponseCode();
                    Cloud_flag = responseCode == HttpURLConnection.HTTP_OK;
                } catch (Exception e) {
                    Log.e("Internet Exception", e.toString()+"asasasasas");
                }
                if(progress!=null && log !=null) {
                     progress.dismiss();
                    log.Database_check();
                }
            }
        });
        m.start();
    }
    public static void clear()
    {
        log = null;
        progress = null;
    }
}
