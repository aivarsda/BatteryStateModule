package com.aivarsda.batStateObservers;

import android.util.Log;

import com.aivarsda.batterystatus.BatteryState;
import com.aivarsda.batterystatus.Observers.IBatteryStateObserver;


public class BatStateObserver implements IBatteryStateObserver
{
    private static final String TAG = "BatStateObserver";

    @Override
    public void onBatteryStateChanged(BatteryState batteryState) {
        Log.i(TAG,"onBatteryStateChanged()" +
                "\n curPluggedStatus: "+batteryState.getPluggedStatus()+
                "\n curChargingStatus: "+batteryState.getChargingStatus()+
                "\n Bat Level: "+ batteryState.getBatteryLevel() +
                "\n Bat Health: "+batteryState.getHealth()+
                "\n isBatteryPresent: "+batteryState.isBatteryPresent()+
                "\n technology: "+batteryState.getTechnology()+
                "\n temperature: "+batteryState.getTemperature()+
                "\n voltage:"+batteryState.getVoltage()
        );
    }
}
