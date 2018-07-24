package com.aivarsda.batStateObservers;

import android.util.Log;

import com.aivarsda.batterystatus.Observers.IBatteryPercentageObserver;


public class BatteryPercentageObserver2 implements IBatteryPercentageObserver
{
    private static final String TAG = "BatteryPercentObserver2";

    @Override
    public void onBatteryPercentageChanged(int batteryLvl) {
        Log.i(TAG,"onBatteryPercentageChanged_2 -> "+batteryLvl);
    }
}
