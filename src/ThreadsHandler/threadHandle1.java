package ThreadsHandler;

import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

import ConcreteVehicle.Airplane;
import GlobalVariables.GlobalVar;
import MakeVehicles.MakeVehicles;

public class threadHandle1 {
    public static Airplane a1 = new MakeVehicles().MakeAirplane();
    public threadHandle1() {
        Runnable runAir = () -> {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                if (GlobalVar.Play) {   
                    if (a1.getGasLvl() > 0) {
                        a1.setStatus("Running");
                        a1.setDisTvl(a1.getDisTvl() + 1);
                        a1.setGasLvl(a1.getGasLvl() - 5);

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
                        a1.setStatus("Out-of-Fuel");
                    }
                } else {
                    a1.setStatus("Paused");
                }
            }

        };
        new Thread(runAir).start(); 
    }
}   
