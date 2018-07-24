package com.aivarsda.batterystatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.aivarsda.batterystatus.Exceptions.ObjectAlreadyPresentInListException;
import com.aivarsda.batterystatus.Exceptions.UnsupportedBatteryObserverTypeException;
import com.aivarsda.batterystatus.Observers.IBatteryChargingObserver;
import com.aivarsda.batterystatus.Observers.IBatteryPercentageObserver;
import com.aivarsda.batterystatus.Observers.IBatteryStateObserver;
import com.aivarsda.batterystatus.Utils.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BatteryStateProvider
{
    private Context                             context;
    private List<Object>                        batObservers = new ArrayList<>();
    private BroadcastReceiver                   batteryChangeReceiver;

    private int prevChargingState   = -1;
    private int prevPluggedState    = -1;
    private int prevBatteryLevel    =  0;


    private static class SingletonHolder {
        private static final BatteryStateProvider INSTANCE = new BatteryStateProvider();
    }

    private BatteryStateProvider() {
    }

    public static BatteryStateProvider getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**Will initiate the BatteryStateProvider and start monitoring the Battery state.
     * @param ctx Application context.
     */
    public void init(Context ctx)
    {
        Logger.enableLogging(true);
        Logger.log("init BatteryStateProvider");

        context = ctx;
        startMonitoringBatteryState();
    }

     /**Adding an observer that will receive updates when the battery level changes or the battery charging state changes.
     * @param obj Must implement one of the Battery state observer interfaces: IBatteryStateObserver, IBatteryChargingObserver, IBatteryPercentageObserver.
     */
    public void addBatteryObserver(Object obj) throws ObjectAlreadyPresentInListException, UnsupportedBatteryObserverTypeException
    {
        Logger.log("adding "+obj.getClass().getSimpleName());
        if(isSupportedObserverType(obj))
        {
            if(batObservers.size() == 0)
            {
                batObservers.add(obj);
            }
            else
            {
                if(isObserverAlreadyAssigned(obj))
                    throw new ObjectAlreadyPresentInListException(obj,"Trying to add battery observer:");
                else
                    batObservers.add(obj);
            }
        }
        else
        {
            throw new UnsupportedBatteryObserverTypeException(obj);
        }
    }

    /**Remove the Battery observer, should be one of the following types: IBatteryStateObserver, IBatteryChargingObserver, IBatteryPercentageObserver.
     * @param obj Must implement one of the Battery state observer interfaces: IBatteryStateObserver, IBatteryChargingObserver, IBatteryPercentageObserver.
     */
    public void removeBatteryObserver(Object obj) throws UnsupportedBatteryObserverTypeException
    {
        Logger.log("removing "+obj.getClass().getCanonicalName());
        if(isSupportedObserverType(obj))
            this.batObservers.remove(obj);
        else
            throw new UnsupportedBatteryObserverTypeException(obj);
    }

    /**Remove all the Battery observer of specific type, should be one of the following types: IBatteryStateObserver, IBatteryChargingObserver, IBatteryPercentageObserver.
     * @param batteryObserverTypeClass One of the supported battery observer types: IBatteryStateObserver, IBatteryPercentageObserver, IBatteryChargingObserver.
     */
    public void removeBatteryObserversOfType(Class batteryObserverTypeClass)
    {
        Logger.log("batteryObserverTypeClass = "+batteryObserverTypeClass.getSimpleName());
        Iterator<Object> it = this.batObservers.iterator();
        while (it.hasNext()) {
            Object observerObj = it.next();
            if (batteryObserverTypeClass.isInstance(observerObj))
            {
                Logger.log("remove -> "+observerObj.getClass().getSimpleName());
                it.remove();
            }
        }
    }

    /**
     * Remove Battery observers of all types: BATTERY_STATE, BATTERY_PERCENTAGE, BATTERY_CHARGING
     */
    public void removeAllBatteryObservers()
    {
        Logger.log("");
        this.batObservers.clear();
    }

    private boolean isObserverAlreadyAssigned(Object obj)
    {
        boolean hasInstanceOfTheObj = false;
        for (Object object : this.batObservers)
        {
            hasInstanceOfTheObj = object.getClass().equals( obj.getClass());
            if(hasInstanceOfTheObj)
                return hasInstanceOfTheObj;
        }
        return hasInstanceOfTheObj;
    }

    private boolean isSupportedObserverType(Object obj)
    {
        if(implementsInterface(obj, IBatteryStateObserver.class)
                || implementsInterface(obj, IBatteryChargingObserver.class)
                || implementsInterface(obj, IBatteryPercentageObserver.class))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /** Will check if the object is implementing the interface class.
     * @param object Object to check if it's implementing the interface.
     * @param interFace Interface class that we want to check, if it is implemented by the object.
     * @return True - If the Interface class is implemented by the Object, False - Otherwise.
     */
    private boolean implementsInterface(Object object, Class interFace){
        return interFace.isInstance(object);
    }

    private void startMonitoringBatteryState()
    {
        batteryChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                //1 Unknown,2 Good,3 Overheat,4 Dead,5 Over voltage,6 Unspecified failure,7 Cold.
                int curHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
                //1 UNKNOWN, 2 CHARGING, 3 DISCHARGING, 4 NOT_CHARGING, 5 FULL.
                int curChargingStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                //0 NotPlugged, 1 AC, 2 USB, 4 Wireless
                int curPluggedStatus = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean isBatteryPresent = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, true);
                String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
                int voltage = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
                int rawLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int curBatteryLevel = 0;
                if (rawLevel >= 0 && scale > 0)
                    curBatteryLevel  = (rawLevel * 100) / scale;

                Logger.log("received battery status: " +
                        "\n pluggedStatus: "+curPluggedStatus+
                        "\n chargingStatus: "+curChargingStatus+
                        "\n Bat Level: "+ curBatteryLevel +
                        "\n Bat Health: "+curHealth+
                        "\n isBatteryPresent: "+isBatteryPresent+
                        "\n technology: "+technology+
                        "\n temperature: "+temperature+
                        "\n voltage:"+voltage
                );

                BatteryState batteryState = new BatteryState(
                        curHealth,
                        curChargingStatus,
                        curPluggedStatus,
                        isBatteryPresent,
                        technology,
                        temperature,
                        voltage,
                        curBatteryLevel);
                onBatteryStateChanged(batteryState);

                if(isPluggedStatusChanged(curPluggedStatus) || isChargingStateChanged(curChargingStatus))
                {
                    onBatteryChargingStateChanged(curChargingStatus, curPluggedStatus);
                }

                if(isBatteryLevelChanged(curBatteryLevel))
                {
                    onBatteryLevelChanged(curBatteryLevel);
                }
            }
        };

        context.registerReceiver(batteryChangeReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private boolean isBatteryLevelChanged(int curBatteryLevel)
    {
        boolean isBatteryLevelChanged = (prevBatteryLevel != curBatteryLevel ? true : false);
        prevBatteryLevel = curBatteryLevel;
        return isBatteryLevelChanged;
    }

    private boolean isChargingStateChanged(int currentChargingState)
    {
        boolean isChargingStateChanged = (prevChargingState != currentChargingState ? true : false);
        prevChargingState = currentChargingState;
        return isChargingStateChanged;
    }

    private boolean isPluggedStatusChanged(int currentPluggedState)
    {
        boolean isPluggedStatusChanged = (prevPluggedState != currentPluggedState ? true : false);
        prevPluggedState = currentPluggedState;
        return isPluggedStatusChanged;
    }

    private void onBatteryStateChanged(BatteryState batteryState)
    {
        for (Object obj : this.batObservers)
        {
            if(implementsInterface(obj, IBatteryStateObserver.class))
                ((IBatteryStateObserver)obj).onBatteryStateChanged(batteryState);
        }
    }

    private void onBatteryLevelChanged(int batLvl)
    {
        for (Object obj : this.batObservers)
        {
            if(implementsInterface(obj, IBatteryPercentageObserver.class))
                ((IBatteryPercentageObserver)obj).onBatteryPercentageChanged(batLvl);
        }
    }

    private void onBatteryChargingStateChanged(int curBatChargingState, int curBatPluggedState)
    {
        for (Object obj : this.batObservers)
        {
            if(implementsInterface(obj, IBatteryChargingObserver.class))
            {
                ((IBatteryChargingObserver)obj).onBatteryChargingStateChanged(curBatChargingState);
                ((IBatteryChargingObserver)obj).onBatteryPluggedStateChanged(curBatPluggedState);
            }
        }
    }
}
