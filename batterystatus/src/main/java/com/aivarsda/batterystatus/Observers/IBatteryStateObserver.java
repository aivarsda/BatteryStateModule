package com.aivarsda.batterystatus.Observers;

import com.aivarsda.batterystatus.BatteryState;

public interface IBatteryStateObserver
{
    /**Will return the full battery state including all battery parameters.
     * @param batteryState
     */
    void onBatteryStateChanged(BatteryState batteryState);
}
