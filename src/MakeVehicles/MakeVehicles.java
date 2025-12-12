package MakeVehicles;

import ConcreteVehicle.Airplane;
import ConcreteVehicle.Ship;
import ConcreteVehicle.Truck;

public class MakeVehicles  {
    public Airplane MakeAirplane() {
        Airplane a1 = new Airplane();
        a1.setID(1);
        a1.setDisTvl(0);
        a1.setGasLvl(100);
        a1.setStatus("Paused");
        return a1;
    }
    public Ship MakeShip() {
        Ship s1 = new Ship();
        s1.setID(2);
        s1.setDisTvl(0);
        s1.setGasLvl(100);
        s1.setStatus("Paused");
        return s1;
    
    }
    public Truck MakeTruck() {
        Truck t1 = new Truck();
        t1.setID(3);
        t1.setDisTvl(0);
        t1.setGasLvl(100);
        t1.setStatus("Paused");
        return t1;
    }
        
}
