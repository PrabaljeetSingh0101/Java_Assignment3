package ThreadsHandler;

import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

import ConcreteVehicle.Truck;
import GlobalVariables.GlobalVar;
import MakeVehicles.MakeVehicles;

public class threadHandle3 {
    public static Truck t1 = new MakeVehicles().MakeTruck();
    public threadHandle3() {
        Runnable runTruck = () -> {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                if (GlobalVar.Play) {   
                    if (t1.getGasLvl() > 0) {
                        
                        t1.setStatus("Running");
                        t1.setDisTvl(t1.getDisTvl() + 1);
                        t1.setGasLvl(t1.getGasLvl() - 5);

                        // Update Global Variable
                        if (GlobalVar.fixed) {
                            synchronized(GlobalVar.distanceLock) {
                                GlobalVar.Total_distance_travelled_by_all += 1;
                            }
                        } else {
                            GlobalVar.Total_distance_travelled_by_all += 1;
                        }                
                    }
                    else {
                        t1.setStatus("Out-of-Fuel");
                    }
                } else {
                    t1.setStatus("Paused");
                }
            }

        };
        new Thread(runTruck).start(); 
    }
}   
