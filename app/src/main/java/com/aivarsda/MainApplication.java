package com.aivarsda;

import android.app.Application;
import android.util.Log;

import com.aivarsda.batterystatus.BatteryStateProvider;


public class MainApplication extends Application
{
    private static final String TAG = "MainApplication";

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.i(TAG,"Application started");
        initApp();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        Log.i(TAG,"Application terminated");
    }

    private void initApp()
    {
        Log.i(TAG,"Application init");
        //All other stuff for the application initialization ...
        BatteryStateProvider.getInstance().init(this);
    }
}
