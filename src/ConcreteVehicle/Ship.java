package ConcreteVehicle;

import AbstractVehicle.AbstractVehicle;

public class Ship extends AbstractVehicle {
    @Override
    public String getType() {
        return "Ship";
    }
}
