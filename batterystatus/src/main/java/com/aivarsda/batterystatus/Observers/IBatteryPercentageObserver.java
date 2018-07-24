package com.aivarsda.batterystatus.Observers;

public interface IBatteryPercentageObserver
{
    /**Will be triggered when the battery percentage is changed.
     * @param batteryLvl The battery level in %.
     */
    void onBatteryPercentageChanged(int batteryLvl);
}
