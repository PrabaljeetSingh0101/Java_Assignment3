package AbstractClasses;

import CustomExceptions.InvalidOperationException;

public abstract class WaterVehicle extends Vehicle {
    
    private boolean hasSail;

    public WaterVehicle (String id, String model, double maxSpeed, double currentMileage, boolean hasSail) throws InvalidOperationException {
        super (id, model, maxSpeed, 0.0);
        this.hasSail = hasSail;
        // this.currentMileage = 0.0;
    }

    @Override 
    public double estimateJourneyTime(double distance) {
        return ((distance / getMaxSpeed()) * 1.15);
    }

    public boolean getSail() {
        return hasSail;
    }

}