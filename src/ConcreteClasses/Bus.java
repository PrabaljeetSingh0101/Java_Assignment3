package ConcreteClasses;
import AbstractClasses.LandVehicle;
import CustomExceptions.*;
import Interfaces.*;

public class Bus extends LandVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable 
{
    private int passengerCapacity, currentPassengers;
    private double cargoCapacity, currentCargo, fuelLevel, mileageSinceLastService;
    private boolean maintenanceNeeded;

    public Bus (String id, String model, double maxSpeed, double currentMileage,int numOfWheels) throws InvalidOperationException {
        super (id, model, maxSpeed, currentMileage ,numOfWheels);
        this.fuelLevel = 0;
        this.passengerCapacity = 50;
        this.currentPassengers = 0;
        this.cargoCapacity = 500;
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
    @Override    
    public double consumeFuel(double distance) throws InsufficientFuelException {
        double fuelNeeded = distance / calculateFuelEfficiency();
        if (fuelNeeded > fuelLevel)
            throw new InsufficientFuelException (" Insufficient fuel level!");
        fuelLevel -= fuelNeeded;
        return fuelNeeded;
    }
    // FUEL CONSUMABLE


    // CARGO CARRIER
    @Override
    public void loadCargo (double weight) throws OverloadException {
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
            throw new InvalidOperationException("Sorry, passengers cant be < 0!");
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


    @Override 
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException 
    {    
        if (distance < 0)
            throw new InvalidOperationException("Distance cannot be < 0!"); 
        consumeFuel(distance);
        System.out.println("Bus, ID: "+ getId()+ " moved.");
        this.currentMileage += distance;
        this.mileageSinceLastService += distance; 
    }

    @Override
    public double calculateFuelEfficiency() {
        return 10.0;
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
            "Bus",
            this.getId(),//1
            this.getModel(),//2
            String.valueOf(this.getMaxSpeed()),//3
            String.valueOf(this.getNumWheels()),//4
            String.valueOf(this.getFuelLevel()),//5
            String.valueOf(this.getPassengerCapacity()),//6
            String.valueOf(this.getCargoCapacity()),//7
            String.valueOf(this.getCurrentCargo()),//8
            String.valueOf(this.getCurrentPassengers()),//9
            String.valueOf(this.getCurrentMileage()),//10
            String.valueOf(this.needsMaintenance()),//11
            String.valueOf(this.getmileageSinceLastService())//12
        );
    }
}