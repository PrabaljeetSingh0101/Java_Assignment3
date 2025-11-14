package AbstractClasses;

import CustomExceptions.InsufficientFuelException;
import CustomExceptions.InvalidOperationException;

public abstract class Vehicle implements Comparable<Vehicle> {
    
    private String id;
    private String model;
    private double maxSpeed;
    protected double currentMileage;

    public Vehicle (String id, String model, double maxSpeed, double currentMileage) throws InvalidOperationException {

        if (id == null || id == "") {
            throw new InvalidOperationException("Vehicle id cannot be empty! Invalid.");
        }
        if (currentMileage < 0) {
            throw new InvalidOperationException("Mileage cannot be neagtive!");
        }
        this.id = id;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.currentMileage = currentMileage;
    
    }

    @Override 
    public int compareTo (Vehicle other) {
        double eff1 = this.calculateFuelEfficiency();
        double eff2 = other.calculateFuelEfficiency();

        return Double.compare(eff1, eff2);
    } 

    public abstract void move (double distance) throws InvalidOperationException, InsufficientFuelException;

    // abstract void fueling() throws InvalidOperationException;

        // Returns km per liter (or 0 for non-fuel vehicles).
    public abstract double calculateFuelEfficiency();


        // Returns time in hours (distance / maxSpeed, adjusted by type).
    public abstract double estimateJourneyTime(double distance);

    public void displayInfo() {
        System.out.println ("ID: " + id + "\nModel: " + model + "\nMaxSpeed: " + maxSpeed + "\nCurrentMileage: " + currentMileage);
    }

    public double getCurrentMileage() {
        return currentMileage;
    } 

    protected void updatedCurrentMileage (double distance) {
        if (distance > 0)
            this.currentMileage += distance;
    }

    public String getId() {
        return id;  
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public abstract String toCsvString();

    public String getModel() {
        return model;
    }
}