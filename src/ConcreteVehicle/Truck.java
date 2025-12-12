package ConcreteVehicle;

import AbstractVehicle.AbstractVehicle;

public class Truck extends AbstractVehicle {
    @Override
    public String getType() {
        return "Truck";
    }
}
