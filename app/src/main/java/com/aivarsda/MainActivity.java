package com.aivarsda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aivarsda.batStateObservers.BatStateObserver;
import com.aivarsda.batStateObservers.BatteryChargingObserver;
import com.aivarsda.batStateObservers.BatteryPercentageObserver;
import com.aivarsda.batStateObservers.BatteryPercentageObserver2;
import com.aivarsda.batStateObservers.NonBatteryObserver;

import com.aivarsda.batterystatus.BatteryStateProvider;
import com.aivarsda.batterystatus.Exceptions.ObjectAlreadyPresentInListException;
import com.aivarsda.batterystatus.Exceptions.UnsupportedBatteryObserverTypeException;
import com.aivarsda.batterystatus.Observers.IBatteryChargingObserver;
import com.aivarsda.batterystatus.Observers.IBatteryPercentageObserver;
import com.aivarsda.batterystatus.Observers.IBatteryStateObserver;


public class MainActivity extends AppCompatActivity
{
    private Button btn_remBatStateObserv;
    private Button btn_remChargingStateObserv;
    private Button btn_remLevelObserv;
    private Button btn_remConcreteObserv;
    private Button btn_remAllObservers;
    private Button btn_addBatObservers;
    private Button btn_addDuplicateObserver;
    private Button btn_addNonBatteryObserver;

    BatteryChargingObserver batteryChargingObserver = new BatteryChargingObserver();
    BatteryPercentageObserver batteryPercentageObserver = new BatteryPercentageObserver();
    BatteryPercentageObserver2 batteryPercentageObserver2 = new BatteryPercentageObserver2();
    BatStateObserver batStateObserver = new BatStateObserver();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding the Battery state observers:
        addBatteryObservers();

        btn_remBatStateObserv = findViewById(R.id.btn_remBatStateObserv);
        btn_remChargingStateObserv = findViewById(R.id.btn_remChargingStateObserv);
        btn_remLevelObserv = findViewById(R.id.btn_remLevelObserv);
        btn_remConcreteObserv = findViewById(R.id.btn_remConcreteObserv);
        btn_remAllObservers = findViewById(R.id.btn_remAllBatObservers);
        btn_addBatObservers = findViewById(R.id.btn_addBatObservers);
        btn_addDuplicateObserver = findViewById(R.id.btn_addDuplicateObserver);
        btn_addNonBatteryObserver = findViewById(R.id.btn_addNonBatteryObserver);


        btn_remBatStateObserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BatteryStateProvider.getInstance().removeBatteryObserversOfType(BatteryStateProvider.BatteryObserverTypes.BATTERY_STATE);
                BatteryStateProvider.getInstance().removeBatteryObserversOfType(IBatteryStateObserver.class);
            }
        });

        btn_remChargingStateObserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BatteryStateProvider.getInstance().removeBatteryObserversOfType(BatteryStateProvider.BatteryObserverTypes.BATTERY_CHARGING);
                BatteryStateProvider.getInstance().removeBatteryObserversOfType(IBatteryChargingObserver.class);
            }
        });

        btn_remLevelObserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BatteryStateProvider.getInstance().removeBatteryObserversOfType(BatteryStateProvider.BatteryObserverTypes.BATTERY_PERCENTAGE);
                BatteryStateProvider.getInstance().removeBatteryObserversOfType(IBatteryPercentageObserver.class);
            }
        });

        btn_remConcreteObserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BatteryStateProvider.getInstance().removeBatteryObserver(batteryPercentageObserver);
                } catch (UnsupportedBatteryObserverTypeException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_remAllObservers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BatteryStateProvider.getInstance().removeAllBatteryObservers();
            }
        });

        btn_addBatObservers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBatteryObservers();
            }
        });

        btn_addDuplicateObserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BatteryStateProvider.getInstance().addBatteryObserver(new BatteryChargingObserver());
                }
                catch (UnsupportedBatteryObserverTypeException e) {
                    e.printStackTrace();
                }
                catch (ObjectAlreadyPresentInListException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_addNonBatteryObserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BatteryStateProvider.getInstance().addBatteryObserver(new NonBatteryObserver());
                }
                catch (UnsupportedBatteryObserverTypeException e) {
                    e.printStackTrace();
                }
                catch (ObjectAlreadyPresentInListException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addBatteryObservers()
    {
        try
        {
            BatteryStateProvider.getInstance().addBatteryObserver(batteryChargingObserver);
            BatteryStateProvider.getInstance().addBatteryObserver(batteryPercentageObserver);
            BatteryStateProvider.getInstance().addBatteryObserver(batteryPercentageObserver2);
            BatteryStateProvider.getInstance().addBatteryObserver(batStateObserver);
        }
        catch (UnsupportedBatteryObserverTypeException e) {
            e.printStackTrace();
        }
        catch (ObjectAlreadyPresentInListException e) {
            e.printStackTrace();
        }
    }
}
