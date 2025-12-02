package ThreadsHandler;

import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

import ConcreteVehicle.Ship;
import GlobalVariables.GlobalVar;
import MakeVehicles.MakeVehicles;

public class threadHandle2 {
    public static Ship s1 = new MakeVehicles().MakeShip();
    public threadHandle2() {
        Runnable runAir = () -> {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                if (GlobalVar.Play) {   
                    if (s1.getGasLvl() > 0) {
                        
                        s1.setStatus("Running");
                        s1.setDisTvl(s1.getDisTvl() + 1);
                        s1.setGasLvl(s1.getGasLvl() - 5);

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
                        s1.setStatus("Out-of-Fuel");
                    }
                } else {
                    s1.setStatus("Paused");
                }
            }

        };
        new Thread(runAir).start(); 
    }
}   
