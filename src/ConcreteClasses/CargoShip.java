package ConcreteClasses;
import AbstractClasses.WaterVehicle;
import CustomExceptions.*;
import Interfaces.CargoCarrier;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;

public class CargoShip extends WaterVehicle implements CargoCarrier, Maintainable, FuelConsumable  
{
    private int cargoCapacity;
    private double currentCargo, fuelLevel, mileageSinceLastService;
    private boolean maintenanceNeeded;

    // some error here
    public CargoShip (String id, String model, double maxSpeed, double currentMileage, boolean hasSail) throws InvalidOperationException {
        super (id, model, maxSpeed, currentMileage, hasSail);
        this.fuelLevel = 0.0;
        this.cargoCapacity = 50000;
        this.currentCargo = 0.0;
        this.maintenanceNeeded = false;
        this.mileageSinceLastService = currentMileage;
    }

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


    // MAINTAINABLE
    public void scheduleMaintenance () {
        if (getSail() == true) {
            System.out.println ("Eco Friendly Ships dont need Repairs!");
            return;
        }
        this.maintenanceNeeded = true;
            
    }
    public boolean needsMaintenance() {
        if (getSail() == true) {
            return false;
        }
        if (mileageSinceLastService > 10000 || maintenanceNeeded == true)
            return true;
        else 
            return false;
    }
    public void performMaintenance() {
        if (getSail() == true) {
            System.out.println ("Eco Friendly Ships dont need Repairs!");
            return;
        }
        this.maintenanceNeeded = false;
        this.mileageSinceLastService = 0; 
        System.out.println("Maintenance done on Car ID: " + getId());
    }
    // maintainable


    // FUEL CONSUMABLE
    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if (getSail() == true)
            throw new InvalidOperationException("The Ship is eco Friendly!");
        if (amount < 0) 
            throw new InvalidOperationException("Amount cannot be < 0!");
        this.fuelLevel += amount;
    }
    
    public double getFuelLevel() {
        if (getSail() == true) {
            return 0.0;
        }
        return fuelLevel;
    }
    @Override    
    public double consumeFuel(double distance) throws InsufficientFuelException {
        if (getSail() == true)
            throw new InsufficientFuelException("The Ship is eco Friendly!");
        double fuelNeeded = distance / calculateFuelEfficiency();
        if (fuelNeeded > fuelLevel)
            throw new InsufficientFuelException(" Insufficient fuel level!");
        fuelLevel -= fuelNeeded;
        return fuelNeeded;
    }
    // FUEL CONSUMABLE


    @Override 
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        
        if (distance < 0)
            throw new InvalidOperationException("Distance cannot be < 0!"); 
        
        if (getSail() == false) {
            consumeFuel(distance);
        System.out.println("CargoShip, ID: " + getId()+ " sailed.");
        }
        else {
            this.currentMileage += distance;
            this.mileageSinceLastService += distance;
            System.out.println("CargoShip, ID: " + getId()+ " sailed.");
        }

    }

    @Override
    public double calculateFuelEfficiency() {
        if (getSail() == true) 
            return 0.0;
        else 
            return 4.0; 
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
            "CargoShip",
            this.getId(),
            this.getModel(),
            String.valueOf(this.getMaxSpeed()),//3
            String.valueOf(this.getSail()),//4
            String.valueOf(this.getSail() ? 0.0 : getFuelLevel()),
            String.valueOf(this.getCargoCapacity()),//6
            String.valueOf(this.getCurrentCargo()),//7
            String.valueOf(this.getCurrentMileage()),//8
            String.valueOf(this.needsMaintenance()),//9
            String.valueOf(this.getmileageSinceLastService())//10
        );
    }
}