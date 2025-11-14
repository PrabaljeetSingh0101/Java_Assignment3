package FleetManagerClass;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import AbstractClasses.*;
import ConcreteClasses.*;
import CustomExceptions.*;
import Interfaces.CargoCarrier;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;
import Interfaces.PassengerCarrier;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class FleetManager
{
    private List<Vehicle> fleet = new ArrayList<Vehicle>();
    private TreeSet<String> differenModels = new TreeSet<>(); // to maintain distinct model names

    public void addVehicle(Vehicle v) throws InvalidOperationException
    {
        for (Vehicle i : fleet) {
            if (v.getId().equals(i.getId()))
                throw new InvalidOperationException("ID Should Not Match!");
        }
        fleet.add(v);
        differenModels.add(v.getModel());
    }   

    public void removeVehicle(String id) throws InvalidOperationException
    {   
        boolean wasRemooved = false;
        String model = null;
        Vehicle toRemove = null;
        for (Vehicle v: fleet) {
            if (v.getId().equals(id)) {
                wasRemooved = true;
                model = v.getModel();
                toRemove = v;
                break;
            }
        }
        if (wasRemooved == false)
            throw new InvalidOperationException("Invalid Id!");
        int count = 0;
        for (Vehicle v: fleet) {
            if (v.getModel().equals(model)) {
                count++;
            }
        }
        if (count == 1)
            differenModels.remove(model);
        fleet.remove(toRemove);
        System.out.println("Succesfully removed!");
    }

    public void startAllJourneys(double distance) throws InvalidOperationException, InsufficientFuelException 
    {
        for (Vehicle i : fleet) {
            try {
                i.move (distance);
                if (i instanceof FuelConsumable spaces) {
                    if (i instanceof CargoShip ship) {
                        if (ship.getSail() == true) {
                            System.out.println("\tSails without fuel, goes any far.");
                            continue;
                        }
                    }
                    double fuelLevel = spaces.getFuelLevel();
                    double fuelEfficiency = i.calculateFuelEfficiency();
                    double far = fuelLevel * fuelEfficiency;
                    // System.out.println ("\tCan go more "+ far + " km.");
                    System.out.printf ("\tCan go more %.1f km.\n", far);
                }
            } catch ( InvalidOperationException |  InsufficientFuelException e) {
                System.out.println (i.getClass().getSimpleName()+", ID: "+ i.getId() + e.getMessage());
                if (i instanceof FuelConsumable spaces) {
                    double fuelLevel = spaces.getFuelLevel();
                    double fuelEfficiency = i.calculateFuelEfficiency();
                    double far = fuelLevel * fuelEfficiency;
                    System.out.printf ("\tCan go more %.1f km.\n", far);
                }
            }
        }
    } 

    public void refuelAll (double amount) throws InvalidOperationException
    {
        if (amount <= 0)
            throw new InvalidOperationException("Refueling amount insufficient.");
        else
        {
            for (Vehicle i : fleet) {
                if (i instanceof FuelConsumable) {
                    FuelConsumable fuelTypeVehicle = (FuelConsumable)i;
                    fuelTypeVehicle.refuel (amount);
                }   
            }
            System.out.println ("All refueled successfully!");
        }
    }

    // private double getTotalFuelConsumption(double distance) 
    // {
    //     double ans = 0;
    //     for (Vehicle v : fleet) {
    //         if (v instanceof FuelConsumable) {
    //             try {
    //                 FuelConsumable i = (FuelConsumable) v;
    //                 ans += i.consumeFuel(distance);
    //             } catch (InsufficientFuelException e) {
    //                 System.out.println("Error for id: " + v.getId() + " with eeror -> "+e.getMessage());
    //             }
    //         }
    //     }
    //     return ans;
    // }
    
    public void maintainAll() 
    {
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable) {
                Maintainable i = (Maintainable)v;
                if (i.needsMaintenance() == true) {
                    i.performMaintenance();
                }
            }
        }
        System.out.println("Maintained all properly!");
    }

    public List<Vehicle> searchByType(Class<?> type) 
    {
        List <Vehicle> ans = new ArrayList<>();
        for (Vehicle v : fleet) {
            if (type.isInstance(v)) {
                ans.add (v);
            }
        }
        return ans;
    }
    
    public void sortFleetByEfficiency()
    {
        Collections.sort(fleet);
    }
    
    public String generateReport()
    {
        // total vehicles 
        String ans = "";
        ans += "\nTotal vehicles : " + fleet.size() + "\n";
        
        //count by type
        ans += ("\nCount by type :" + "\n");
        int lv = 0, wv = 0, av = 0;
        for (Vehicle i : fleet) {
            if (i instanceof LandVehicle)
                lv ++;
            if (i instanceof WaterVehicle)
                wv ++;
            if (i instanceof AirVehicle)
                av ++;
        }
        ans += ("\tLand Vehicle : " + lv + "\n");
        ans += ("\tWater Vehicle: " + wv + "\n");
        ans += ("\tAir Vehicle  : " + av + "\n");

        // vehicle list
        ans += "\nVehicle list :\n";
        if (fleet.isEmpty()) {
            ans += "The fleet is currently empty.\n";
        } else {
            for (Vehicle v : fleet) {
                ans += String.format("\t%-10s, ID: %-5s, Model: %-5s, Mileage: %-5.1f km\n", v.getClass().getSimpleName(), v.getId(), v.getModel(), v.getCurrentMileage());
            }
        }

        // average efficiency
        int count = 0;
        double totalEfficiency = 0;
        for (Vehicle v : fleet) {
            if (v instanceof FuelConsumable) {
                if (v instanceof CargoShip cp) {
                    if (!cp.getSail()) {
                        count ++;
                        totalEfficiency += cp.calculateFuelEfficiency();
                    }
                }
                else {
                    count ++;
                    totalEfficiency += v.calculateFuelEfficiency();
                }
            }
        }
        double AvgEff = 0.0;
        if (count > 0)
            AvgEff = (totalEfficiency) / (count);
        ans += "\n\tAvg. efficiency: " + AvgEff + "\n";

        // total mileage
        double TotMileage = 0;
        for (Vehicle v : fleet) {
            TotMileage += v.getCurrentMileage();
        } 
        ans += "\tTotal Mileage  : " + TotMileage + "\n\n"; 

        
        // maintenance status
        for (Vehicle v: fleet) {
            if (v instanceof Maintainable) {
                Maintainable m = (Maintainable) v;
                if (m.needsMaintenance()) {
                    ans += String.format("Needs Maintenance: Type: %s, ID: %s\n", v.getClass().getSimpleName(), v.getId());
                }
            }
        }

        // SORTING
        // speed
        if (fleet.isEmpty()) {
            ans += "\nFleet is empty. No speed data.\n";
        }
        else {
            // Using Collections.max() to find just the one fastest vehicle.
            // This is faster than sorting the whole list.
            Vehicle fastest = Collections.max(fleet, new SortBySpeed());
            Vehicle slowest = Collections.min(fleet, new SortBySpeed());
            ans += "Fastest Vehicle: \n";
            ans += "\tSpeed: " + fastest.getMaxSpeed() + "\n\t"+fastest.getClass().getSimpleName()+"\n\tID: "+fastest.getId();
            ans += "\nSlowest Vehicle :\n";
            ans += "\tSpeed: " + slowest.getMaxSpeed() + "\n\t"+slowest.getClass().getSimpleName()+"\n\tID: "+slowest.getId();    
        }
        //speed

        // fuel efficiency
        List<Vehicle> fuelvehicles = new ArrayList<>();

        // making new list that only includes vehicles that actually use fuel.
        // This loop filters out sailing ships, etc.
        for (Vehicle i: fleet) {
            if (i instanceof FuelConsumable) { 
                if (i instanceof CargoShip ship) { 
                    if (ship.getSail() == false) {
                        fuelvehicles.add(i);
                    }
                }
                else { 
                    fuelvehicles.add(i);
                }
            }
        }
        if (fuelvehicles.isEmpty()) {
            ans += "\n\nNo fuel-consuming vehicles to report.\n";
        }
        else {
            Vehicle mostEff = Collections.max(fuelvehicles, new SortByEfficiency());
            Vehicle leastEff = Collections.min(fuelvehicles, new SortByEfficiency());
            ans += "\n\nMost Efficient Vehicle:\n";
            ans += "\tEfficiency: " + mostEff.calculateFuelEfficiency() + "\n\t"+mostEff.getClass().getSimpleName()+"\n\tID: "+mostEff.getId();
            ans += "\nLeast Effecient Vehicle:\n";
            ans += "\tEfficiency: " + leastEff.calculateFuelEfficiency() + "\n\t"+leastEff.getClass().getSimpleName()+"\n\tID: "+leastEff.getId();    
        }
        // fuel efficiency

        //total fleet capacity
        int totalPassengers = 0;
        double CargoWeight = 0;
        for (Vehicle v: fleet) {
            if (v instanceof PassengerCarrier) {
                PassengerCarrier pc = (PassengerCarrier)v;
                totalPassengers += pc.getCurrentPassengers();
            }
            if (v instanceof CargoCarrier) {
                CargoCarrier cc = (CargoCarrier)v;
                CargoWeight += cc.getCurrentCargo();
            }
        }
        ans += "\n\nTotal Fleet Capacity: ";
        ans += "\n\tTotal Passengers: " + totalPassengers;
        ans += "\n\tTotal Cargo Weight: " + CargoWeight+"\n";
        //total fleet capacity
        return ans;
        // SORTING
    }
    
    public List<Vehicle> getVehiclesNeedingMaintenance() {
        List <Vehicle> result = new ArrayList <> ();
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable) {
                Maintainable m = (Maintainable) v;
                if (m.needsMaintenance()) {
                    result.add(v);
                }
            }
        }
        return result;
    }

    private Vehicle createVehicleFromString(String[] data) throws InsufficientFuelException, InvalidOperationException, OverloadException {
        String type = data[0];
        String id = data[1];
        String model = data[2];
        double maxSpeed = Double.parseDouble(data[3]);
        
        switch (type.toLowerCase()) {
            case "car":
                int numWheels = Integer.parseInt(data[4]);
                double carMileage = Double.parseDouble(data[8]);
                
                Car car = new Car(id, model, maxSpeed, carMileage, numWheels);
                car.refuel(Double.parseDouble(data[5])); 
                car.boardPassengers(Integer.parseInt(data[7])); 
                car.setMaintenanceNeeded(Boolean.parseBoolean(data[9]));
                car.setmileageSinceLastService(Double.parseDouble(data[10]));
                return car;

            case "truck":
                int numofWheels = Integer.parseInt(data[4]);
                double truckMileage = Double.parseDouble(data[8]);
                
                Truck truck = new Truck(id, model, maxSpeed, truckMileage, numofWheels);
                truck.refuel(Double.parseDouble(data[5])); 
                truck.loadCargo(Double.parseDouble(data[7])); 
                truck.setmileageSinceLastService(Double.parseDouble(data[10])); 
                return truck;
  
            case "cargoship":
                boolean hasSail = Boolean.parseBoolean(data[4]);
                double shipMileage = Double.parseDouble(data[8]);
                
                CargoShip ship = new CargoShip(id, model, maxSpeed, shipMileage, hasSail);
                if (!hasSail) {
                    ship.refuel(Double.parseDouble(data[5])); 
                    ship.setmileageSinceLastService(Double.parseDouble(data[10]));
                }
                ship.loadCargo(Double.parseDouble(data[7])); 
                return ship;

            case "bus":
                int numOfWheels = Integer.parseInt(data[4]);
                double busMileage = Double.parseDouble(data[10]);
                
                Bus bus = new Bus(id, model, maxSpeed, busMileage, numOfWheels);
                bus.refuel(Double.parseDouble(data[5])); 
                bus.loadCargo(Double.parseDouble(data[8])); 
                bus.boardPassengers(Integer.parseInt(data[9]));
                bus.setmileageSinceLastService(Double.parseDouble(data[12])); 
                return bus;

            case "airplane":
                int maxAltitude = Integer.parseInt(data[4]);
                double airplaneMileage = Double.parseDouble(data[10]);
                
                Airplane airplane = new Airplane(id, model, maxSpeed, airplaneMileage, maxAltitude);
                airplane.refuel(Double.parseDouble(data[5])); 
                airplane.loadCargo(Double.parseDouble(data[8])); 
                airplane.boardPassengers(Integer.parseInt(data[9])); 
                airplane.setmileageSinceLastService(Double.parseDouble(data[12])); 
                return airplane;

            default:
                throw new IllegalArgumentException("Unknown vehicle type in file: " + type);
        }
    }

    public void loadFromFile(String filename) {
        fleet.clear();
        differenModels.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                try {
                    Vehicle newVehicle = createVehicleFromString(parts);
                    addVehicle(newVehicle);
                }
                catch (Exception e) {
                    System.out.println("Error: " + e);
                }
            }
            System.out.println("Fleet data successfully loaded");
        } catch (IOException e) {
            System.out.println ("Error! Couldn't read file: " + e.getMessage());
        }
    }

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Vehicle v : fleet) {
                writer.write(v.toCsvString());
                writer.newLine();
            }
            System.out.println("Fleet data successfully saved to " + filename);
        } catch (IOException e) {
            System.out.println ("Counldn't save fleet to file. " + e.getMessage());
        }
    }

    public boolean isIdDuplicate (String id) {
        for (Vehicle i : fleet) {
            if (id.equals(i.getId()))
                return true;
        }
        return false;
    }

    // SORTING
    // helper class tells Collections.sort() how to compare two vehicles.
    class SortBySpeed implements Comparator<Vehicle> {
        public int compare(Vehicle a, Vehicle b) {
            return Double.compare(a.getMaxSpeed(), b.getMaxSpeed());
        }
    }
    class SortByModel implements Comparator<Vehicle> {
        public int compare (Vehicle a, Vehicle b) {
            return a.getModel().compareTo(b.getModel());
        }
    }
    
    class SortByEfficiency implements Comparator<Vehicle> {
        public int compare(Vehicle a, Vehicle b) {
            return Double.compare(a.calculateFuelEfficiency(), b.calculateFuelEfficiency());
        }
    }
    // returns a new list sorted from slowest to fastest.
    public List<Vehicle> sortByIncSpeed() {
        List<Vehicle> result = new ArrayList<>();
        result.addAll(fleet);
        // made a copy so we don't change the main list
        // Collections.sort() changes the list order using our helper func
        Collections.sort(result, new SortBySpeed());
        return result;
    }

    public List<Vehicle> sortByDecSpeed() {
        List<Vehicle> result = new ArrayList<>();
        result.addAll(fleet);
        // .reverseOrder() changes helper fun order reverse
        Collections.sort(result, Collections.reverseOrder(new SortBySpeed()));
        return result;
    }

    public List<Vehicle>sortByModel() {
        List<Vehicle> result = new ArrayList<>();
        result.addAll(fleet);
        Collections.sort(result, new SortByModel());
        return result;
    }

    public List<Vehicle> sortByIncEff() {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : fleet) {
            if (v instanceof FuelConsumable) {
                result.add(v);
            }
        }
        // Remove sailing ships, bcz they don't use fuel
        result.removeIf(v -> {
            if (v instanceof CargoShip ship) {
                return ship.getSail();
            }
            return false;
        });
        Collections.sort(result, new SortByEfficiency());
        return result;
    }

    public List<Vehicle> sortByDecEff() {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : fleet) {
            if (v instanceof FuelConsumable) {
                result.add(v);
            }
        }
        result.removeIf(v -> {
            if (v instanceof CargoShip ship) {
                return ship.getSail();
            }
            return false;
        });
        Collections.sort(result, Collections.reverseOrder(new SortByEfficiency()));
        return result;
    }
    // SORTING

    public void distinctModels() {
        for (String value: differenModels) {
            System.out.println(value);
        }
    }
} 
