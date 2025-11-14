– Explain how your code demonstrates inheritance, polymorphism, abstract classes and interfaces.

Inheritance

    My project uses inheritence for example the vehicle class has id, model, maxSpeed, currentMileage which are implemneted by the its chiild classes and similarly the childs childs classes.
    this makes the code reusable dont ened to write again n again. useful

Polymorphism

    This includes the methods overriding, we did which were like astractly just declared the functions and did overriding to them.
    The fleet list holds objects of the base type Vehicle. However, at runtime, each element v could actually be a Car, a Bus, or an Airplane.
    This ability for v to represent many different forms is polymorphism.

Abstract Classes

    Its provides common template for a group of related subclasses defining as here 
    The Vehicle class is abstract and declares shared properties and methods. It also defines abstract methods like move() and calculateFuelEfficiency().
    By declaring these methods as abstract, the Vehicle class forces all concrete subclasses to provide their own implementation. 

Interfaces

    Here its defining capabilities or "contracts" that can be implemented by different classes, these define the some proprerties emthods or rules which its
    class which implements it must have it.
    Interfaces in here are like FuelConsumable, CargoCarrier, and PassengerCarrier having methods e.g., refuel(), loadCargo(), boardPassengers().




– Provide clear instructions to compile (e.g., javac *.java), run (e.g., java Main) and test persistence with the sample CSV.

Compiling the Code

    Open terminal in the src folder.
    Run command : "javac */*.java MainClass/MainClass.java"

Running the Program

    After successful compilation,
    Run command "java MainClass/MainClass.java"

Testing Persistence

    Its self depricating(been wanting to use that word, lmfaou) / explainatory when we run there are options for select from 1 to 11
    like 1 to add vehicle 2 to remove vehicle 3 to start journey 4 to refuel all vehicles, 5 to perform maintenance, 6 to generte report, 
    7 to save fleet to the file, 8 load fleet from the file, 9 to search byb type like type of car or interface or etc i dont remmeber, so informal, 
    10 to list vehicles needing maintenance, 11 to exit from it yeah boring stuff to read this. 




– Describe how to use the CLI and demo features (e.g., add vehicles, simulate journey, save/load).

The program features a Command-Line Interface (CLI) for managing the vehicle fleet. When you run the program, you will see a menu of options.

    Add Vehicle (Option 1): This allows you to add a new vehicle to the fleet. You will be prompted to choose a vehicle type (e.g., Car, Truck, Airplane) and then enter its specific details like ID, model, max speed, etc.

    Remove Vehicle (Option 2) : first search by the type to get the id for vehicle, or use generate report to be removed and then type the id to be removed.

    Simulate Journey (Option 3): it will prompt to enter the distance in kilometers. The program will then update the mileage for each vehicle and consume fuel where applicable.

    Refuel All (Option 4): refuels all vehicles with the amount you type to refuel.

    Perform Maintainenece(Option 5): the vehicles which have needsmaintainance true, it will check the mileage since last service for that. and then it preforms maitainance by needsmaintanance to false and milege since last service to 0.  

    Generate Report (Option 6): it prints  total vehicles, count by type like land vehicles, water, air etc, list vehicles, average efficinecy gives out average mileage fuel one, and total.

    Save Fleet (Option 7): This saves the current state of all vehicles in the fleet to a CSV file. You will be prompted to enter a filename.

    Load Fleet (Option 8): This clears the current fleet and loads a new fleet from a specified CSV file. You will be prompted to enter the filename.

    Search By Fleet (Option 9): search by types like car, truck, bus etc or the interfaces! too.

    List Vehicles Needing Mainenance (Option 10): self depricating.

    Exit(option 11): same. 


- How Collections Are Used

    ArrayList<Vehicle>: Its the main collection for storing Vehicle objects because it's a dynamic list that allows duplicates (e.g., two cars of the same model) 
        and size dynamically adjusted and can be sorted by different criteria (speed, model) using Collections.sort(), COllections.max(), etc.

    TreeSet<String>: It stores the distinct model names of the vehicles. It automatically keeps the list of names unique and in alphabetical order.



– Include a brief walkthrough of running the demo and expected output.
    first compile and run by the commands above told;
    
    then it shows the select operation thing,
    then press 1 to add vehicles , enter all the info and then add more vehicle of differnet types

    Enter vehicle type: car
    Enter id: v1
    Enter model: m1
    Enter maxSpeed: 100
    Num of wheels: 4
    Mileage: 30
    refuel: 49
    Board Passengers: 2
    Vehicle added Successfully!

    then add another cargoship
    Enter vehicle type: cargoship
    Enter id: v2
    Enter model: m2
    Enter maxSpeed: 123
    HAs sail: false
    Mileage: 45
    Load Cargo: 123
    Refuel: 431

    Enter vehicle type: airplane
    Enter id: v3
    Enter model: m3
    Enter maxSpeed: 123
    Max Altitude: 392
    Mileage: 21
    refuel: 39832
    Load Cargo: 134
    Board Passengers: 213
    Overload! Can Add only 200 more passangers
    Board Passengers: 3

    then press 6 to get report

    Total vehicles => 3

    Count by type :
    Land Vehicle ->1
    Water Vehicle ->1
    Air Vehicle ->1

    Vehicle list :
    Type: Car, ID: v1, Model: m1, Mileage: 30.0 km
    Type: CargoShip, ID: v2, Model: m2, Mileage: 0.0 km
    Type: Airplane, ID: v3, Model: m3, Mileage: 21.0 km

    Avg. efficiency = 8.0

    Total Mileage : 51.0

    now to remove the car, press 2 and the id we got from generate report i.e. v1
    2
    Id of vehicle to be removed: 
    v1
    Succesfully removed!

    now press 6 to see the report again 
    6

    Total vehicles => 2

    Count by type :
    Land Vehicle ->0
    Water Vehicle ->1
    Air Vehicle ->1

    Vehicle list :
    Type: CargoShip, ID: v2, Model: m2, Mileage: 0.0 km
    Type: Airplane, ID: v3, Model: m3, Mileage: 21.0 km

    Avg. efficiency = 4.5

    Total Mileage : 21.0

    we can see the car was removed..

    now start journey with pressing 3 
    Distance to move: 
    123999993123123
    Invalid, erorr in moving id-> v2 Insufficient fuel level!
    Invalid, erorr in moving id-> v3 Insufficient fuel level!
    All moved successphully.

    we can see the distance i too much so entering lower, sad 4me poor functionality
    3
    Distance to move: 
    123
    Waterways Cheat Activated... ID: v2
    Plane Flying at 392 km ID: v3
    All moved successphully.

    now refuel all too much to test maintianance things..
    4
    Amount to refuel: 
    9999999
    All refueled successfully!

    3
    Distance to move: 
    100000
    Waterways Cheat Activated... ID: v2
    Plane Flying at 392 km ID: v3
    All moved successphully.

    press 10 to check if needed maintainance 
    10
    ID: v3
    Model: m3
    MaxSpeed: 123.0
    CurrentMileage: 100144.0
    yeah airplane true, shiip false becuase doesnt use fuel so no needs maintaince 


    now perform maintenance
    5
    Maintenance done on Car ID: v3
    Maintained all properly!

    recheckking if it worked properly by pressign 10
    10
    No vehicles currently need maintainence!

    press 6 if want u u know just for fun,, its not maybe
    6

    Total vehicles => 2

    Count by type :
    Land Vehicle ->0
    Water Vehicle ->1
    Air Vehicle ->1

    Vehicle list :
    Type: CargoShip, ID: v2, Model: m2, Mileage: 0.0 km
    Type: Airplane, ID: v3, Model: m3, Mileage: 100144.0 km

    Avg. efficiency = 4.5

    Total Mileage : 100144.0


    press 7 to save the fleet
    7
    Fleet data successfully saved to fleet.csv

    now open fleet to recheck..

    now press 9 to search by type
    Type name to search: 
    fuelconsumable
    ID: v2
    Model: m2
    MaxSpeed: 123.0
    CurrentMileage: 0.0
    ID: v3
    Model: m3
    MaxSpeed: 123.0
    CurrentMileage: 100144.0

    which is correct tells us the aeroplane and ship, ship because we added the functionality in case if it uses has sail false.

    testing car..
    9
    Type name to search: 
    car
    No results for the given type!

    testing airplane..
    9
    Type name to search: 
    airplane
    ID: v3
    Model: m3
    MaxSpeed: 123.0
    CurrentMileage: 100144.0

    ig thats it ..
    press 11  
    see U ... 