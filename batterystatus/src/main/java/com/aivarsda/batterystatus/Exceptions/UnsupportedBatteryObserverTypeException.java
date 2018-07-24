package com.aivarsda.batterystatus.Exceptions;

public class UnsupportedBatteryObserverTypeException extends Exception
{
    private static final String EXCEPTION_MSG = " - is unsupported battery observer type. Must implement one of the following: IBatteryChargingObserver, IBatterypercentageObserver, IBatteryStateObserver.";
    public UnsupportedBatteryObserverTypeException(Object obj) {
        super(obj.getClass().getCanonicalName() + EXCEPTION_MSG);
    }
}
