package AbstractClasses;

import CustomExceptions.InvalidOperationException;

public abstract class AirVehicle extends Vehicle {
    
    private int maxAltitude ;

    public AirVehicle (String id, String model, double maxSpeed, double currentMileage , int maxAltitude) throws InvalidOperationException{
        super (id, model, maxSpeed, currentMileage);
        this.maxAltitude = maxAltitude;
        // this.currentMileage = 0.0;
    }

    @Override 
    public double estimateJourneyTime(double distance) {
        return ((distance / getMaxSpeed()) * 0.95);
    }

    public int getMaxAltitude() {
        return maxAltitude;
    }
}