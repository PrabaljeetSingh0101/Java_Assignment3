package AbstractClasses;

import CustomExceptions.InvalidOperationException;

public abstract class LandVehicle extends Vehicle {
    
    private int numWheels;

    public LandVehicle (String id, String model, double maxSpeed, double currentMileage, int numOfWheels) throws InvalidOperationException {
        super (id, model, maxSpeed, currentMileage);
        if (numOfWheels < 0) {
            throw new InvalidOperationException("Num Of Wheels Cannot be Negative!");
        }
        this.numWheels = numOfWheels;
    }

    @Override 
    public double estimateJourneyTime(double distance) {
        return ((distance / getMaxSpeed()) * 1.1);
    }


    public int getNumWheels() {
        return this.numWheels;
    }
}