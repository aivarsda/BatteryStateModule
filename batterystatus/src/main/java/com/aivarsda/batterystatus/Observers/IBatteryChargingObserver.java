package com.aivarsda.batterystatus.Observers;

public interface IBatteryChargingObserver
{
    /** Charging states: 1 UNKNOWN, 2 CHARGING, 3 DISCHARGING, 4 NOT_CHARGING, 5 FULL.
     * @param batteryChargingState NOT_CHARGING = 0, ON_CHARGING = 1, UNKNOWN = -1
     */
    void onBatteryChargingStateChanged(int batteryChargingState);

    /**Plugged States : 0 NotPlugged, 1 AC, 2 USB, 4 Wireless
     * @param batteryPluggedState 0 NotPlugged, 1 AC, 2 USB, 4 Wireless
     */
    void onBatteryPluggedStateChanged(int batteryPluggedState);
}
