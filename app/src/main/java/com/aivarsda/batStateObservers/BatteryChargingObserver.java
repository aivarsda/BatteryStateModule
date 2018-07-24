package com.aivarsda.batStateObservers;

import android.util.Log;

import com.aivarsda.batterystatus.Observers.IBatteryChargingObserver;


public class BatteryChargingObserver implements IBatteryChargingObserver
{
    private static final String TAG = "BatteryChargingObserver";

    @Override
    public void onBatteryChargingStateChanged(int batteryChargingState) {
        Log.i(TAG,"\nonBatteryChargingStateChanged -> "+batteryChargingState);
    }

    @Override
    public void onBatteryPluggedStateChanged(int batteryPluggedState) {
        Log.i(TAG,"\nonBatteryPluggedStateChanged -> "+batteryPluggedState);
    }
}
