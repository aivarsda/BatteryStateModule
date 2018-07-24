package com.aivarsda.batterystatus;

public class BatteryState
{
    int         health;         //1 Unknown,2 Good,3 Overheat,4 Dead,5 Over voltage,6 Unspecified failure,7 Cold.
    int         chargingStatus; //1 UNKNOWN, 2 CHARGING, 3 DISCHARGING, 4 NOT_CHARGING, 5 FULL.
    int         pluggedStatus;  //0 NotPlugged, 1 AC, 2 USB, 4 Wireless
    boolean     isBatteryPresent;
    String      technology;
    int         temperature;
    int         voltage;
    int         batteryLevel;

    public BatteryState(int curHealth, int curChargingStatus, int curPluggedStatus, boolean isBatteryPresent, String technology, int temperature, int voltage, int curBatteryLevel) {
        this.health = curHealth;
        this.chargingStatus = curChargingStatus;
        this.pluggedStatus = curPluggedStatus;
        this.isBatteryPresent = isBatteryPresent;
        this.technology = technology;
        this.temperature = temperature;
        this.voltage = voltage;
        this.batteryLevel = curBatteryLevel;
    }

    public int getHealth() {
        return health;
    }

    public int getChargingStatus() {
        return chargingStatus;
    }

    public int getPluggedStatus() {
        return pluggedStatus;
    }

    public boolean isBatteryPresent() {
        return isBatteryPresent;
    }

    public String getTechnology() {
        return technology;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getVoltage() {
        return voltage;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }
}
