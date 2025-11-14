package ConcreteClasses;
import AbstractClasses.LandVehicle;
import CustomExceptions.*;
import Interfaces.*;

public class Truck extends LandVehicle implements FuelConsumable, CargoCarrier, Maintainable 
{
    private double fuelLevel;
    private double cargoCapacity, currentCargo;
    private boolean maintenanceNeeded;
    private double mileageSinceLastService; 


    public Truck (String id, String model, double maxSpeed, double currentMileage, int numOfWheels) throws InvalidOperationException {
        super (id, model, maxSpeed, currentMileage ,numOfWheels);
        this.fuelLevel = 0;
        this.cargoCapacity = 5000.0;
        this.currentCargo = 0.0;
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


    // CARGO CARRIER
    @Override
    public void loadCargo (double weight) throws OverloadException{
        if ((currentCargo + weight) > cargoCapacity)
            throw new OverloadException("cargo Overload, cannot handle!");
        currentCargo += weight; 
    }
    @Override
    public void unloadCargo (double weight) throws InvalidOperationException {
        if (weight > currentCargo)
            throw new InvalidOperationException("Unloading weight is greater than current weigth!");
        currentCargo -= weight;
    }
    @Override
    public double getCargoCapacity() {
        return cargoCapacity;
    }
    @Override
    public double getCurrentCargo() {
        return currentCargo;
    }
    // CARGO CARRIER
    

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


    @Override 
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        
        if (distance < 0)
            throw new InvalidOperationException("Distance cannot be < 0!"); 
        consumeFuel(distance);
        System.out.println("Truck, ID: " + getId()+ " moved.");        
        this.currentMileage += distance;
        this.mileageSinceLastService += distance;
    }

    @Override
    public double calculateFuelEfficiency() {
        if (currentCargo > 0.5*(cargoCapacity))
            return 8.0*0.9;
        else
            return 8.0;
    }

    public double getmileageSinceLastService() {
        return mileageSinceLastService;
    }
    public void setmileageSinceLastService(double mileage) {
        this.mileageSinceLastService = mileage;
    }

    @Override
    public String toCsvString() {
        return String.join(",",
            "Truck",
            this.getId(),//1
            this.getModel(),//2
            String.valueOf(this.getMaxSpeed()),//3
            String.valueOf(this.getNumWheels()),//4
            String.valueOf(this.getFuelLevel()),//5
            String.valueOf(this.getCargoCapacity()),//6
            String.valueOf(this.getCurrentCargo()),//7
            String.valueOf(this.getCurrentMileage()),//8
            String.valueOf(this.needsMaintenance()),//9
            String.valueOf(this.getmileageSinceLastService())//10
        );
    }
}