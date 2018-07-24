package com.aivarsda.batStateObservers;

import android.util.Log;

import com.aivarsda.batterystatus.Observers.IBatteryPercentageObserver;


public class BatteryPercentageObserver implements IBatteryPercentageObserver
{
    private static final String TAG = "BatteryPercentObserver";

    @Override
    public void onBatteryPercentageChanged(int batteryLvl) {
        Log.i(TAG,"onBatteryPercentageChanged -> "+batteryLvl);
    }
}
