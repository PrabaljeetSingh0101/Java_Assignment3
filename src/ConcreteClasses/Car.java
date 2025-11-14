package ConcreteClasses;

import AbstractClasses.LandVehicle;
import CustomExceptions.*;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;
import Interfaces.PassengerCarrier;

public class Car extends LandVehicle implements FuelConsumable, PassengerCarrier, Maintainable 
{
    private double fuelLevel, mileageSinceLastService;
    private int passengerCapacity, currentPassengers;
    private boolean maintenanceNeeded;

    public Car (String id, String model, double maxSpeed, double currentMileage, int numOfWheels) throws InvalidOperationException {
        super (id, model, maxSpeed, currentMileage , numOfWheels);
        this.fuelLevel = 0.0;
        this.passengerCapacity = 5;
        this.currentPassengers = 0;
        this.maintenanceNeeded = false;
        this.mileageSinceLastService = currentMileage; 
    }


    // FUEL CONSUMABLE
    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if (amount < 0) 
            throw new InvalidOperationException("Amount cannot be < 0!");
        this.fuelLevel += amount;
    }
    @Override 
    public double getFuelLevel() {
        return fuelLevel;
    }
     
    public double consumeFuel(double distance) throws InsufficientFuelException { 
        double fuelNeeded = distance / calculateFuelEfficiency();
        if (fuelNeeded > fuelLevel)
            throw new InsufficientFuelException(" Insufficient fuel level!");
        fuelLevel -= fuelNeeded;
        return fuelNeeded;
    }
    // FUEL CONSUMABLE


    // Passenger Carrier
    @Override
    public void boardPassengers (int count) throws OverloadException {
        if ((count + currentPassengers) > passengerCapacity)
            throw new OverloadException ("OverBoareding, more than Capacity!");
        currentPassengers += count;
    }
    @Override
    public void disembarkPassengers(int count) throws InvalidOperationException {
        if (count > currentPassengers)
            throw new InvalidOperationException("Sorry, cannot remove more than available passengers!");
        currentPassengers -= count;
    } 
    @Override
    public int getPassengerCapacity() {
        return passengerCapacity;
    }
    @Override
    public int getCurrentPassengers() {
        return currentPassengers;
    }
    // Passenger Carrier


    // MAINTAINABLE
    public void scheduleMaintenance () {
        this.maintenanceNeeded = true;
            
    }
    public boolean needsMaintenance() {
        if (mileageSinceLastService > 10000 || maintenanceNeeded == true)
            return true;
        else 
            return false;
    }
    public void performMaintenance() {
        this.maintenanceNeeded = false;
        this.mileageSinceLastService = 0;
        System.out.println("Maintenance done on Car ID: " + getId());
    }
    // maintainable

    public boolean getMaintenanceNeeded() {
        return maintenanceNeeded;
    }

    @Override 
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException 
    {
        if (distance < 0)
            throw new InvalidOperationException("Distance cannot be < 0!"); 
        consumeFuel(distance);        
        System.out.println("Car, ID: " + getId()+ " moved.");
        this.currentMileage += distance;
        this.mileageSinceLastService += distance;
    }

    @Override
    public double calculateFuelEfficiency() {
        return 15.0;
    }

    public double getmileageSinceLastService() {
        return mileageSinceLastService;
    }
    public void setmileageSinceLastService(double mileage) {
        this.mileageSinceLastService = mileage;
    }
 
    public void setMaintenanceNeeded(boolean status) {
        this.maintenanceNeeded = status;
    }

    @Override
    public String toCsvString() {
        return String.join(",",
            "Car",
            this.getId(),//1
            this.getModel(),//2
            String.valueOf(this.getMaxSpeed()),//3
            String.valueOf(this.getNumWheels()),//4
            String.valueOf(this.getFuelLevel()),//5
            String.valueOf(this.getPassengerCapacity()),//6
            String.valueOf(this.getCurrentPassengers()),//7
            String.valueOf(this.getCurrentMileage()),//8
            String.valueOf(this.getMaintenanceNeeded()),//9
            String.valueOf(this.getmileageSinceLastService())//10
        );
    }
}