package MainClass;
import java.util.List;
import java.util.Scanner;

import ConcreteClasses.*;
import AbstractClasses.Vehicle;
import CustomExceptions.*;
import FleetManagerClass.FleetManager;
import Interfaces.*;

public class MainClass 
{
    public static void main (String[] args) 
    {
        FleetManager myFleet = new FleetManager();
        Scanner in = new Scanner (System.in);
        int test = 0;
        System.out.println("For HardCode testing press only 1");
        test = in.nextInt();
        in.nextLine();
        if (test == 1) {
            System.out.println("--- STARTING HARD-CODED TEST ---");
            try {
                // 1. Test Adding Vehicles (including different types and duplicate models)
                System.out.println("\n---ADDING VEHICLES");
                myFleet.addVehicle(new Car("c1", "Maruti800", 180, 5000, 4));
                myFleet.addVehicle(new Truck("t1", "Mahindra", 160, 25000, 4));
                myFleet.addVehicle(new Bus("b1", "DelhiBus", 140, 12000, 6));
                myFleet.addVehicle(new Airplane("a1", "Boeng", 900, 150000, 30000));
                myFleet.addVehicle(new CargoShip("cs1", "titanic", 50, 80000, false));
                // ship (to test 0.0 efficiency)
                myFleet.addVehicle(new CargoShip("cs2", "SailShip", 30, 1000, true));
                // car with a duplicate model
                myFleet.addVehicle(new Car("c2", "Maruti800", 190, 2000, 4));
                System.out.println("Vehicles added successfully.");

                // 2. Duplicate id handle
                System.out.println("\n--ADDING DUPLICATE ID (EXPECT ERROR)");
                try {
                    myFleet.addVehicle(new Car("c1", "i20", 170, 100, 4));
                } catch (InvalidOperationException e) {
                    System.out.println("SUCCESS: Correctly caught error: " + e.getMessage());
                }

                // Report Generation (intially)
                System.out.println("\n---INITIAL FLEET REPORT");
                System.out.println(myFleet.generateReport());

                // Fleet operations
                System.out.println("\n---RUNNING OPERATIONS (Move & Maintain)");
                myFleet.startAllJourneys(100);
                myFleet.maintainAll();
                System.out.println("Operations complete.");

                // 5. File Save
                System.out.println("\n---SAVING FLEET TO test.csv");
                myFleet.saveToFile("test.csv");
                System.out.println("Save complete.");

                // 6. File Load
                System.out.println("\n---LOADING FLEET FROM test.csv");
                // loadFromFile automatically clears the fleet first
                myFleet.loadFromFile("test.csv");
                System.out.println("Load complete.");

                // 7. Report After Load (Should match state from before saving)
                System.out.println("\n---REPORT AFTER LOADING (Confirms Save/Load)");
                System.out.println(myFleet.generateReport());

                // 8. Test Distinct Models
                System.out.println("\n---DISTINCT MODELS (from TreeSet)");
                myFleet.distinctModels(); // print the sorted, unique models

            } catch (Exception e) {
                System.out.println("--- HARD-CODED TEST FAILED ---");
                e.printStackTrace();
            }
            System.out.println("\n--- HARD-CODED TEST FINISHED ---");
        }
        
        while(true) 
        {
            int operationNum = 0;
            while (true) 
            {
                try {
                    System.out.println("\n=====================");
                    System.out.println("       MAIN MENU     ");
                    System.out.println("=====================");
                    System.out.println("1. Vehicle Management");
                    System.out.println("2. Fleet Operations");
                    System.out.println("3. Reports & Queries");
                    System.out.println("4. File Management");
                    System.out.println("5. Exit");
                    String opnum = in.nextLine().trim();
                    if (opnum.equals("close") || opnum.equals("back") || opnum.equals("finish") || opnum.equals("cancel") || opnum.equals("break"))
                        break;
                    operationNum = Integer.parseInt(opnum); 
                    break;
                } 
                catch (Exception e) {
                    System.out.println("Enter Int!");
                }
            }
            switch (operationNum) 
            {
                case 1 : 
                {
                    while (true) 
                    {
                        try 
                        {
                            System.out.println("\n--------- VEHICLE MANAGEMENT ---------");
                            System.out.println("1. Add Vehicle");
                            System.out.println("2. Remove Vehicle");
                            System.out.println("3. Back to Main Menu");
                            operationNum = in.nextInt();
                            in.nextLine().trim();
                            while (true) {
                                if (operationNum > 0 && operationNum < 6)
                                    break;
                                else 
                                    System.out.println("Invalid! Enter between 1 to 3");
                                operationNum = in.nextInt();
                                in.nextLine().trim();
                            }
                            if(operationNum == 3)
                                break;
                            switch (operationNum) 
                            {
                                case 1: 
                                {
                                    String type;
                                    while (true) {
                                        System.out.print("Enter vehicle type: ");
                                        type = in.nextLine().trim().toLowerCase();
                                        if (type.equals("car") || type.equals("bus") || type.equals("truck") || type.equals("airplane") || type.equals("cargoship") || type.equals("break") || type.equals("cancel") || type.equals("exit") || type.equals("back") || type.equals("finish") || type.equals("cancel")) {
                                            break; 
                                        } else {
                                            System.out.println("Invalid type! TYPES: Car, Bus, Truck, Cargoship, Airplane");
                                        }
                                    }

                                    // exit if came in by miscake
                                    if (type.equals("break")|| type.equals("cancel")|| type.equals("exit") || type.equals("back")|| type.equals("finish") || type.equals("close"))
                                        break;

                                    String id;
                                    while (true) {
                                        System.out.print("Enter id: ");
                                        id = in.nextLine().trim();
                                        if (id.isEmpty()) {
                                            System.out.println("Error: ID cannot be empty.");
                                        } else if (myFleet.isIdDuplicate(id)) {
                                            System.out.println("Error: This ID is already in use. Please enter different ID.");
                                        } else {
                                            break; 
                                        }
                                    }

                                    String model;
                                    while (true) {
                                        System.out.print("Enter model: ");
                                        model = in.nextLine().trim();
                                        if (model.isEmpty()) {
                                            System.out.println("Error: Model cannot be empty.");
                                        } else {
                                            break; 
                                        }
                                    }

                                    double maxSpeed = getDoubleInput(in, "Enter maxSpeed: ");

                                    switch (type.toLowerCase()) 
                                    {
                                        case "car" : 
                                        {
                                            int numWheels = getIntInput(in, "Num of wheels: ");
                                            double mileage = getDoubleInput(in, "Mileage: ");
                                            double refuel = getDoubleInput(in, "refuel: ");
                                            int NumboardPassengers = getIntInput(in, "Board Passengers: ");
                                            try {
                                                Car newCar = new Car(id, model, maxSpeed, mileage, numWheels);
                                                newCar.refuel(refuel);
                                                while (true) {
                                                    try {
                                                        newCar.boardPassengers(NumboardPassengers);
                                                        break;
                                                    }
                                                    catch (OverloadException e) {
                                                        System.out.println("Overload! Can add only " + (newCar.getPassengerCapacity()-newCar.getCurrentPassengers()) + " more passangers" );
                                                        NumboardPassengers = getIntInput(in, "Board Passengers: ");
                                                    }
                                                }
                                                
                                                myFleet.addVehicle(newCar);
                                                System.out.println("Vehicle added Successfully!");

                                            } 
                                            catch (IllegalArgumentException | InvalidOperationException e) {
                                                System.out.println ("Error: " + e.getMessage());
                                            }
                                            break;
                                        }
                                        case "airplane": 
                                        {
                                            int maxAltitude = getIntInput(in, "Max Altitude: ");
                                            double mileage = getDoubleInput(in, "Mileage: ");
                                            
                                            double refuel = getDoubleInput(in, "refuel: ");
                                            double NumloadCargo = getDoubleInput (in, "Load Cargo: ");
                                            int NumboardPassengers = getIntInput(in, "Board Passengers: ");
                                            try {
                                                Airplane newAirplane = new Airplane(id, model, maxSpeed, mileage, maxAltitude);
                                                newAirplane.refuel(refuel);
                                                while (true) {
                                                    try {
                                                        newAirplane.loadCargo(NumloadCargo);
                                                        break;
                                                    }
                                                    catch (OverloadException e) { 
                                                        System.out.println("Overload! Can add only " + (newAirplane.getCargoCapacity()-newAirplane.getCurrentCargo()) + " more cargo" );
                                                        NumloadCargo = getDoubleInput (in, "Load Cargo: ");
                                                    }
                                                }
                                                while (true) {
                                                    try {
                                                        newAirplane.boardPassengers(NumboardPassengers);
                                                        break;
                                                    }
                                                    catch (OverloadException e) {
                                                        System.out.println("Overload! Can add only " + (newAirplane.getPassengerCapacity()-newAirplane.getCurrentPassengers()) + " more passangers" );
                                                        NumboardPassengers = getIntInput(in, "Board Passengers: ");
                                                    }
                                                }
                                                
                                                myFleet.addVehicle(newAirplane);
                                            } catch (IllegalArgumentException | InvalidOperationException e) {
                                                System.out.println ("Error: " + e.getMessage());
                                            }
                                            break;
                                        }
                                        case "truck": 
                                        {
                                            int numWheels = getIntInput(in, "Num of wheels: ");
                                            double mileage = getDoubleInput(in, "Mileage: ");
                                            
                                            double refuel = getDoubleInput(in, "refuel: ");
                                            double NumloadCargo = getDoubleInput (in, "Load Cargo: ");
                                            try {
                                                Truck newTruck = new Truck(id, model, maxSpeed, mileage, numWheels);
                                                newTruck.refuel(refuel);
                                                while (true) {
                                                    try {
                                                        newTruck.loadCargo(NumloadCargo);
                                                        break;
                                                    }
                                                    catch (OverloadException e) {
                                                        System.out.println("Overload! Can add only " + (newTruck.getCargoCapacity()-newTruck.getCurrentCargo()) + " more cargo" );
                                                        NumloadCargo = getDoubleInput (in, "Load Cargo: ");
                                                    }
                                                }
                                                myFleet.addVehicle(newTruck);
                                            } catch (IllegalArgumentException | InvalidOperationException e) {
                                                System.out.println ("Error: " + e.getMessage());
                                            }
                                            break;
                                        }
                                        case "cargoship": 
                                        {
                                            Boolean hasSail = getBooleanInput(in, "Has sail(true or false): ");
                                            double mileage = getDoubleInput(in, "Mileage: ");
                                            double loadCargo = getDoubleInput(in, "Load Cargo: ");
                                            double refuel = 0.0;
                                            if (hasSail == false) {
                                                refuel = getDoubleInput(in, "Refuel: ");
                                            }  
                                            try {
                                                CargoShip newCargoShip = new CargoShip (id, model, maxSpeed, mileage, hasSail);
                                                while (true) {
                                                    try {
                                                        newCargoShip.loadCargo(loadCargo);
                                                        break;
                                                    }
                                                    catch (OverloadException e) {
                                                        System.out.println("Overload! Can add only " + (newCargoShip.getCargoCapacity()-newCargoShip.getCurrentCargo()) + " more cargo" );
                                                        loadCargo = getDoubleInput(in, "Load Cargo: ");
                                                    }
                                                }
                                                if (hasSail == false) {
                                                    newCargoShip.refuel(refuel);
                                                }
                                                myFleet.addVehicle(newCargoShip);
                                            } catch (IllegalArgumentException | InvalidOperationException e) {
                                                System.out.println ("Error: " + e.getMessage());
                                            }
                                            break;
                                        } 
                                        case "bus": 
                                        {
                                            int numWheels = getIntInput(in, "Num of wheels: ");
                                            double mileage = getDoubleInput(in, "Mileage: ");
                                            
                                            double refuel = getDoubleInput(in, "Refuel: ");
                                            double loadCargo = getDoubleInput(in, "Load Cargo: ");
                                            try {
                                                Bus newBus = new Bus (id, model, maxSpeed, mileage, numWheels);     
                                                newBus.refuel(refuel);
                                                while (true) {
                                                    try {
                                                        newBus.loadCargo(loadCargo);
                                                        break;
                                                    }
                                                    catch (OverloadException e) {
                                                        System.out.println("Overload! Can add only " + (newBus.getCargoCapacity()-newBus.getCurrentCargo()) + " more cargo" );
                                                        loadCargo = getDoubleInput(in, "Load Cargo: ");
                                                    }
                                                }
                                                while (true) {
                                                    try {
                                                        int NumboardPassengers = getIntInput(in, "Board Passengers: ");
                                                        newBus.boardPassengers(NumboardPassengers);
                                                        break;
                                                    }
                                                    catch (OverloadException e) {
                                                        System.out.println("Overload! Can add only " + (newBus.getPassengerCapacity()-newBus.getCurrentPassengers()) + " more passangers" );
                                                    }
                                                }
                                                myFleet.addVehicle(newBus);
                                            } catch (IllegalArgumentException | InvalidOperationException e) {
                                                System.out.println ("Error: " + e.getMessage());
                                            }
                                            break;
                                        } 
                                        default: {
                                            throw new IllegalArgumentException("Unknown vehicle of type " + type);
                                        }
                                    }
                                    break;
                                }
                                case 2 : 
                                {
                                    System.out.println ("Id of vehicle to be removed: ");
                                    String id2rmv = in.nextLine().trim();

                                    // exit if came in by miscake --
                                    String temp = id2rmv.toLowerCase();
                                    if (temp.equals("break")|| temp.equals("cancel")|| temp.equals("exit") || temp.equals("back")|| temp.equals("finish")|| temp.equals("close"))
                                        break;
                                    // ------------------------------
                                    try {
                                        myFleet.removeVehicle(id2rmv);
                                    } 
                                    catch (InvalidOperationException e) {
                                        System.out.println("Error: " + e);
                                    }
                                    break;
                                }
                            }
                        break;
                        } 
                        catch (Exception e) {
                            System.out.println("Enter Int!");
                            in.nextLine().trim();
                        }
                    }
                break;
                }
                case 2 : {
                    while (true) {
                        try {
                            System.out.println("--- Fleet Operations ---");
                            System.out.println("1. Start journey");
                            System.out.println("2. Refuel All");
                            System.out.println("3. Perform maintenance");
                            System.out.println("4. Back to Main Menu");
                            operationNum = in.nextInt();
                            in.nextLine().trim();
                            if(operationNum == 4)
                                break;
                            switch (operationNum) {
                                case 1 :
                                {
                                    System.out.println ("Distance to move: ");
                                    String Sdistance = in.nextLine().trim();

                                    // exit if came in by miscake --
                                    String temp = Sdistance.toLowerCase();
                                    if (temp.equals("break")|| temp.equals("cancel")|| temp.equals("exit") || temp.equals("back")|| temp.equals("finish")|| temp.equals("close"))
                                        break;
                                    // ------------------------------

                                    double distance = Double.parseDouble(Sdistance);
                                    try {
                                    myFleet.startAllJourneys(distance);
                                    }
                                    catch (InvalidOperationException | InsufficientFuelException e) {
                                        System.out.println("Erorr: " + e);
                                    }
                                    break;
                                }
                                case 2 :
                                {
                                    System.out.println ("Amount to refuel: ");
                                    String Srefuel = in.nextLine().trim();
                                    double refuel = Double.parseDouble(Srefuel);
                                    try {
                                        myFleet.refuelAll (refuel);
                                    }
                                    catch (InvalidOperationException e) {
                                        System.out.println("Erorr: " + e);
                                    }
                                    break;
                                }
                                case 3 :
                                {
                                    myFleet.maintainAll();
                                    break;
                                } 
                            }
                            break;
                        } 
                        catch (Exception e) {
                            System.out.println("Enter Int!");
                            in.nextLine().trim();
                        }
                    }
                    break;
                }
                case 3 : {
                    while (true) {
                        try {
                            System.out.println("--- Reports & Queries ---");
                            System.out.println("1. Generate Report");
                            System.out.println("2. Search by Type");
                            System.out.println("3. List Vehicles Needing Maintenance");
                            System.out.println("4. List Distinct Models");
                            System.out.println("5. Sort");
                            System.out.println("6. Back to Main Menu");
                            operationNum = in.nextInt();
                            in.nextLine().trim();
                            if(operationNum == 6)
                                break;
                            switch (operationNum) {
                                case 1 : {
                                    System.out.println(myFleet.generateReport());
                                    break;
                                }
                                case 2 : {
                                    System.out.println ("Type name to search: ");
                                    String theType = in.nextLine().trim();

                                    // exit if came in by miscake --
                                    String temp = theType.toLowerCase();
                                    if (temp.equals("break")|| temp.equals("cancel")|| temp.equals("exit") || temp.equals("back") || temp.equals("finish") || temp.equals("close"))
                                        break;
                                    // ------------------------------
                                    
                                    Class<?> searchType = null;

                                    switch (theType.toLowerCase()) {
                                        case "car" : {
                                            searchType = Car.class; break;
                                        }
                                        case "bus" : {
                                            searchType = Bus.class;break;
                                        }
                                        case "truck" : {
                                            searchType = Truck.class;break;
                                        }
                                        case "cargoship" : {
                                            searchType = CargoShip.class;break;
                                        }
                                        case "airplane" : {
                                            searchType = Airplane.class;break;
                                        }
                                        case "fuelconsumable" : {
                                            searchType = FuelConsumable.class;break;
                                        }
                                        case "maintainable" : {
                                            searchType = Maintainable.class;break;
                                        }
                                        case "cargocarrier" : {
                                            searchType = CargoCarrier.class;break;
                                        }
                                        case "passengercarrier" : {
                                            searchType = PassengerCarrier.class;break;
                                        }
                                        default :{
                                            System.out.println("Unknown type entered, sorry..");break;
                                        }
                                    }

                                    if (searchType == null) {
                                        System.out.println("Invalid Search type!");
                                        break; 
                                    }

                                    List<Vehicle> searchResults = myFleet.searchByType(searchType);
                                    if (searchResults.isEmpty())
                                        System.out.println ("No results for the given type!");
                                    else {
                                        for (Vehicle i : searchResults) {
                                            i.displayInfo();
                                        }
                                    }
                                    break;
                                }
                                case 3 : 
                                {
                                    List<Vehicle> maintainenceList = myFleet.getVehiclesNeedingMaintenance();
                                    if (maintainenceList.isEmpty())
                                        System.out.println ("No vehicles currently need maintainence!");
                                    else {
                                        for (Vehicle i : maintainenceList) {
                                            i.displayInfo();
                                        }
                                    }
                                    break;
                                }
                                case 4: {
                                    myFleet.distinctModels();
                                    break;
                                }
                                case 5 :
                                {
                                    System.out.println("Sort By: ");
                                    System.out.println("\t1.Speed: ");
                                    System.out.println("\t2.Model: ");
                                    System.out.println("\t3.Efficiency: ");
                                    String nums = in.nextLine().trim();
                                    // exit if came in by miscake --
                                    String temp = nums.toLowerCase();
                                    if (temp.equals("break")|| temp.equals("cancel")|| temp.equals("exit") || temp.equals("back") || temp.equals("finish")|| temp.equals("close"))
                                        break;
                                    // ------------------------------
                                    while (true) {
                                        if (nums.equals("1") || nums.equals("2") || nums.equals("3"))
                                            break;
                                        else {
                                            System.out.println("Invalid! Enter 1, 2 or 3");
                                            nums = in.nextLine().trim();
                                        }
                                    }
                                    int num = Integer.parseInt(nums);
                                    switch (num) {
                                        case 1: {
                                            System.out.println("1.Increasing: ");
                                            System.out.println("2.Decreasing: ");
                                            temp = in.nextLine().trim();
                                            // input handling 
                                            while (true) {
                                                if (nums.equals("1") || nums.equals("2"))
                                                    break;
                                                else {
                                                    System.out.println("Invalid! Enter 1 or 2");
                                                    nums = in.nextLine().trim();
                                                }
                                            }
                                            // input handling 
                                            num = Integer.parseInt(temp);
                                            switch(num) {
                                                case 1: {
                                                    List<Vehicle> result = myFleet.sortByIncSpeed();
                                                    for (int i = 0; i < result.size(); i++) {
                                                        System.out.println(result.get(i).getClass().getSimpleName()+"\tID: "+result.get(i).getId()+"\tSpeed: "+result.get(i).getMaxSpeed());
                                                    }
                                                    break;
                                                }
                                                case 2: {
                                                    List<Vehicle> result = myFleet.sortByDecSpeed();
                                                    for (int i = 0; i < result.size(); i++) {
                                                        System.out.println(result.get(i).getClass().getSimpleName()+"\tID: "+result.get(i).getId()+"\tSpeed: "+result.get(i).getMaxSpeed());
                                                    }
                                                    break;
                                                }
                                            }
                                            break;
                                        }
                                        case 2: {
                                            List<Vehicle> result = myFleet.sortByModel();
                                            for (Vehicle v : result) {
                                                System.out.println(v.getClass().getSimpleName()+"\tID: "+v.getId()+"\tModel: "+v.getModel());
                                            }
                                            break;
                                        }
                                        case 3: {
                                            System.out.println("1.Increasing: ");
                                            System.out.println("2.Decreasing: ");
                                            temp = in.nextLine().trim();
                                            // input handling 
                                            while (true) {
                                                if (nums.equals("1") || nums.equals("2"))
                                                    break;
                                                else {
                                                    System.out.println("Invalid! Enter 1 or 2");
                                                    nums = in.nextLine().trim();
                                                }
                                            }
                                            // input handling 
                                            num = Integer.parseInt(temp);
                                            switch(num) {
                                                case 1: {
                                                    List<Vehicle> result = myFleet.sortByIncEff();
                                                    for (Vehicle v: result) {
                                                        System.out.println(v.getClass().getSimpleName()+"\tID: "+v.getId() + "\t"+v.calculateFuelEfficiency());
                                                    }
                                                    break;
                                                }
                                                case 2: {
                                                    List<Vehicle> result = myFleet.sortByDecEff();
                                                    for (Vehicle v: result) {
                                                        System.out.println(v.getClass().getSimpleName()+"\tID: "+v.getId() + "\t"+v.calculateFuelEfficiency());
                                                    }
                                                    break;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            break;
                        } 
                        catch (Exception e) {
                            System.out.println("Enter Int!");
                            in.nextLine().trim();
                        }
                    }
                    break;
                }
                case 4 : {
                    while (true) {
                        try {
                            System.out.println("--- File Management ---");
                            System.out.println("1. Save Fleet");
                            System.out.println("2. Load Fleet");
                            System.out.println("3. Back to Main Menu");
                            operationNum = in.nextInt();
                            in.nextLine().trim();
                            if(operationNum == 3)
                                break;
                            switch (operationNum) {
                                case 1: {
                                    myFleet.saveToFile("fleet.csv");
                                    break;
                                }
                                case 2: {
                                    try {
                                        myFleet.loadFromFile("fleet.csv");
                                    }
                                    catch (Exception e) {
                                        System.out.println ("Error" + e);
                                    }
                                    break;
                                }
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid! Enter 1, 2 or 3");
                            in.nextLine().trim();
                        }
                    }
                break;
                }
                case 5 : {
                    System.out.println ("Exited, see U soon..");
                    in.close();
                    return;
                }
                default : {
                    System.out.println("Invalid! Enter between 1 to 5");
                break;
                }
            }
        }
    } 

    private static double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Double.parseDouble(line);
            } 
            catch (Exception e) {
                System.out.println("Invalid input! enter Double!");
            }
        }
    }

    private static int getIntInput (Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line);
            }
            catch (Exception e) {
                System.out.println("Invalid input! enter Integer");
            }
        }
    }

    private static boolean getBooleanInput (Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Boolean.parseBoolean(line);
            }
            catch (Exception e) {
                System.out.println("Invalid input! enter Integer");
            }
        }
    }
}
