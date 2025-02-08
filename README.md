# Description

In this project, I implemented a conceptual hotel, where animals, trees, vets and keepers can interact.

This repository provides tests which can be ran with the following commands:

```
java -Dimport=test.import -Din=test.in -Dout=test.outhyp hva.app.App
diff -b test.out test.outhyp
```

# Description
Animals of various species are cared for by keepers, who are responsible by cleaning habitats, and vets, who are responsible by vaccinating the animals. When an animal is wrongly vaccinated, its health is affected for the rest of its life. It is possible to calculate the animals' and employees' satisfaction.
Habitats have trees, which influence how difficult it is to clean the habitat. Trees have a life cycle and age dependent on the season of the year.

# Functionality

# Main Menu
The main menu allows the user to manage and save the state of the application, perform global operations and open submenus. 

1. Create empty application
2. Open existing application
3. Save application state (through Java Serialization)
4. Advance Season
5. See Satisfaction State, Manage Animals
6. Manage Employees
7. Manage Vaccines
8. Manage Habitats
9. Consults
    
# Check Global Satisfaction
The application returns the sum of all animals' and employees' satisfactions.

# Animal Management Menu

1. View all Animals 
2. Register new Animal
3. Transfer Animal to another Habitat 
4. Calculate Animal Satisfaction
   
# Employee Management Menu

1. View all Employees
2. Register new Employee 
3. Assign a new Responsibility (Habitat to clean or Animal to vaccinate) 
4. Remove an Employee's Responsibility
5. Calculate Employee Satisfaction

# Habitat Management Menu

1. View all Habitats
2. Register new Habitat 
3. Change area of an Habitat 
4. Change Habitat's Influence over a Species
5. Plant a new Tree in Habitat
6. View all Trees in Habitat

# Vaccine Management Menu

1. View all Vaccines
2. Register new Vaccine
3. Vaccinate an Animal
4. List Vaccination History
5. Clear Vaccination History

# Consults Menu

1. View all Animals in Habitat
2. View all Animal Vaccinations
3. View all Vaccinations performed by a Vet
4. View all Wrong Vaccinations
