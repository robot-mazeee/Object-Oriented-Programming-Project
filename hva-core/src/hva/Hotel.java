package hva;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import hva.animal.Animal;
import hva.employee.Employee;
import hva.employee.Keeper;
import hva.employee.Vet;
import hva.exceptions.ImportFileException;
import hva.exceptions.UnknownAnimalException;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.VeterinarianNotAuthorizedCoreException;
import hva.exceptions.WrongVaccinationException;
import hva.exceptions.UnknownDataException;
import hva.exceptions.DuplicateAnimalException;
import hva.exceptions.DuplicateEmployeeException;
import hva.exceptions.DuplicateHabitatException;
import hva.exceptions.DuplicateTreeException;
import hva.exceptions.DuplicateVaccineException;
import hva.exceptions.DuplicateSpeciesException;
import hva.exceptions.DuplicateSpeciesNameException;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnknownSpeciesException;
import hva.exceptions.UnknownTreeException;
import hva.exceptions.UnknownVaccineException;
import hva.exceptions.UnknownVeterinarianException;
import hva.exceptions.UnknownResponsibilityException;
import hva.exceptions.UnknownEmployeeException;
import hva.habitat.Habitat;
import hva.habitat.Influence;
import hva.season.Season;
import hva.species.Species;
import hva.tree.Deciduous;
import hva.tree.EverGreen;
import hva.tree.Tree;
import hva.tree.TreeState.TreeState;
import hva.tree.TreeState.DeciduousAutumnState;
import hva.tree.TreeState.DeciduousSpringState;
import hva.tree.TreeState.DeciduousSummerState;
import hva.tree.TreeState.DeciduousWinterState;
import hva.tree.TreeState.EvergreenAutumnState;
import hva.tree.TreeState.EvergreenSpringState;
import hva.tree.TreeState.EvergreenSummerState;
import hva.tree.TreeState.EvergreenWinterState;
import hva.vaccine.Damage;
import hva.vaccine.Vaccination;
import hva.vaccine.Vaccine;


public class Hotel implements Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** If the state of the application has been changed. */
    private boolean _changed = false;

    /** All animals registered in the hotel. */
    private Map<String, Animal> _animalsList = 
        new TreeMap<String, Animal>(String.CASE_INSENSITIVE_ORDER);
    
    /** All species registered in the hotel by id. */
    private Map<String, Species> _speciesList = 
        new TreeMap<String, Species>(String.CASE_INSENSITIVE_ORDER);
    
    /** All employees registered in the hotel. */
    private Map<String, Employee> _employeesList = 
        new TreeMap<String, Employee>(String.CASE_INSENSITIVE_ORDER);
    
    /** All habitats registered in the hotel. */
    private Map<String, Habitat> _habitatsList = 
        new TreeMap<String, Habitat>(String.CASE_INSENSITIVE_ORDER);

    /** All trees registered in the hotel. */
    private Map<String, Tree> _treesList = 
        new TreeMap<String, Tree>(String.CASE_INSENSITIVE_ORDER);

    /** All vaccines registered in the hotel. */
    private Map<String, Vaccine> _vaccinesList = 
        new TreeMap<String, Vaccine>(String.CASE_INSENSITIVE_ORDER);

    /** All vaccinations that occurred. */
    private List<Vaccination> _vaccinationRegister = new ArrayList<>();
    
    /** All wrong vaccinations that occurred. */
    private List<Vaccination> _wrongVaccinationRegister = 
        new ArrayList<>();

    /** Current season. */
    private Season _season;

    public Hotel() {
        _season = Season.Spring;
    }

    /**
     * Read text input file and create domain entities.
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    void importFile(String filename) throws ImportFileException {
        try (BufferedReader reader = new BufferedReader(
            new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("[\\|,]" );
                try {
                    registerEntry(fields);
                } catch (DuplicateSpeciesException|
                DuplicateAnimalException|
                DuplicateEmployeeException| 
                DuplicateHabitatException| 
                DuplicateTreeException|
                DuplicateVaccineException|
                DuplicateSpeciesNameException|
                UnknownSpeciesException|
                UnknownHabitatException| 
                UnknownTreeException|
                UnknownDataException e) {
                    throw new ImportFileException(filename, e);
                }
            }

        } catch (IOException | UnrecognizedEntryException e) {
            throw new ImportFileException(filename, e);
        }
    }

    /**
     * Register entities in the hotel by their type.
     * @param fields 0 - type, 1 - id, 2 - name, 3 - other
     * @throws IOException
     * @throws UnrecognizedEntryException
     * @throws UnknownDataException
     * @throws DuplicateSpeciesException
     * @throws DuplicateAnimalException
     * @throws DuplicateEmployeeException
     * @throws DuplicateHabitatException
     * @throws DuplicateTreeException
     * @throws DuplicateVaccineException
     * @throws DuplicateSpeciesNameException
     * @throws UnknownSpeciesException
     * @throws UnknownHabitatException
     * @throws UnknownTreeException
     */
    public void registerEntry(String... fields) throws IOException, 
    UnknownDataException, UnrecognizedEntryException, 
    DuplicateSpeciesException, DuplicateAnimalException,
    DuplicateEmployeeException, DuplicateHabitatException,
    DuplicateTreeException, DuplicateVaccineException,
    DuplicateSpeciesNameException, UnknownSpeciesException,
    UnknownHabitatException, UnknownTreeException {
        switch (fields[0]) {
            case "ESPÉCIE" -> registerSpecies(fields);
            case "HABITAT" -> registerHabitat(fields);
            case "ANIMAL" -> registerAnimal(fields);
            case "TRATADOR" -> registerEmployee(fields);
            case "VETERINÁRIO" -> registerEmployee(fields);
            case "VACINA" -> registerVaccine(fields);
            case "ÁRVORE" -> registerTree(fields);
            default -> throw new UnknownDataException(fields[0]);
        }
    }

    /**
     * Register species in the hotel.
     * @param fields 0 - type, 1 - id, 2 - name.
     * @throws IOException
     * @throws UnrecognizedEntryException
     * @throws DuplicateSpeciesException
     * @throws DuplicateSpeciesNameException
     */
    public void registerSpecies(String... fields) throws IOException,
    UnrecognizedEntryException, DuplicateSpeciesException,
    DuplicateSpeciesNameException {
        String id = fields[1];
        if (keyExists("SPECIES", id)) {
            throw new DuplicateSpeciesException();
        }
        String name = fields[2];
        for (Species s : _speciesList.values()) {
            if (s.getName().equals(name))
                throw new DuplicateSpeciesNameException();
        }
        Species newSpecies = new Species(id, name);
        addSpecies(newSpecies);
        for (Habitat habitat : _habitatsList.values()) {
            habitat.addSpecies(id);
        }
        setChanged();
    }

    /**
     * Register animal in the hotel.
     * @param fields 0 - type, 1 - id, 2 - name, 3 - species id, 4 - habitat id.
     * @throws IOException
     * @throws UnrecognizedEntryException
     * @throws DuplicateAnimalException
     */
    public void registerAnimal(String... fields) throws IOException,
    UnrecognizedEntryException, DuplicateAnimalException, 
    UnknownSpeciesException, UnknownHabitatException {
        String id = fields[1];
        if (keyExists("ANIMAL", id)) {
            throw new DuplicateAnimalException();
        }
        String name = fields[2];
        String speciesId = fields[3];
        if (!keyExists("SPECIES", speciesId)) {
            throw new UnknownSpeciesException(speciesId);
        }
        String habitatId = fields[4];
        if (!keyExists("HABITAT", habitatId)) {
            throw new UnknownHabitatException();
        }
        Species species = getSpecies(speciesId);
        Habitat habitat = getHabitat(habitatId);
        Animal newAnimal = new Animal(id, name, species, habitat);
        addAnimal(newAnimal);
        species.incrementPopulation();
        habitat.addAnimal(newAnimal);
        setChanged();
    }

    /**
     * Register employee in the hotel.
     * @param fields 0 - type, 1 - id, 2 - name, 3 - responsibilities.
     * @throws IOException
     * @throws UnrecognizedEntryException
     * @throws DuplicateEmployeeKeyException
     * @throws UnknownHabitatException
     * @throws UnknownSpeciesException
     */
    public void registerEmployee(String... fields) throws IOException, 
    UnrecognizedEntryException, DuplicateEmployeeException, 
    UnknownHabitatException, UnknownSpeciesException {
        String id = fields[1];
        if (keyExists("EMPLOYEE", id)) {
            throw new DuplicateEmployeeException();
        }

        String name = fields[2];
        if (fields[0].equals("TRATADOR") | 
        fields[0].equals("TRT")) {
            Keeper newKeeper = new Keeper(id, name);
            for (int i = 3; i < fields.length; i++) {
                if (!keyExists("HABITAT", fields[i])) {
                    throw new UnknownHabitatException();
                }
                Habitat habitat = getHabitat(fields[i]);
                newKeeper.addHabitat(habitat);
                habitat.incrementNumberOfKeepers();
            }
            addEmployee(newKeeper);
        }

        if (fields[0].equals("VETERINÁRIO") | 
        fields[0].equals("VET")) {
            Vet newVet = new Vet(id, name);
            for (int i = 3; i < fields.length; i++) {
                if (!keyExists("SPECIES", fields[i])) {
                    throw new UnknownSpeciesException(fields[i]);
                }
                Species species = getSpecies(fields[i]);
                species.incrementNumberOfVets();
                newVet.addSpecies(species);
            }
            addEmployee(newVet);
        }

        setChanged();
    }

    /**
     * Register habitat in the hotel.
     * @param fields 0 - type, 1 - id, 2 - name, 3 - area, 4 - trees in habitat.
     * @throws IOException
     * @throws UnrecognizedEntryException
     * @throws DuplicateHabitatException
     * @throws UnknownTreeException
     */
    public void registerHabitat(String... fields) throws IOException, 
    UnrecognizedEntryException, DuplicateHabitatException,
    UnknownTreeException {
        String id = fields[1];
        if (keyExists("HABITAT", id)) {
            throw new DuplicateHabitatException();
        }
        String name = fields[2];
        int area = Integer.parseInt(fields[3]);

        Habitat newHabitat = new Habitat(id, name, area);
        for (int i = 4; i < fields.length; i++) {
            if (!keyExists("TREE", fields[i])) {
                throw new UnknownTreeException();
            }
            Tree tree = getTree(fields[i]);
            newHabitat.addTree(tree);
        }
        addHabitat(newHabitat);
        for (String speciesId : _speciesList.keySet()) {
            newHabitat.addSpecies(speciesId);
        }
        setChanged();
    }

    /**
     * Register vaccine in the hotel.
     * @param fields 0 - type, 1 - id, 2 - name, 3 - species list.
     * @throws IOException
     * @throws UnrecognizedEntryException
     * @throws DuplicateVaccineException
     * @throws UnknownSpeciesException
     */
    public void registerVaccine(String... fields) throws IOException, 
    UnrecognizedEntryException, DuplicateVaccineException,
    UnknownSpeciesException {
        String id = fields[1];
        if (keyExists("VACCINE", id)) {
            throw new DuplicateVaccineException();
        }
        String name = fields[2];

        Vaccine newVaccine = new Vaccine(id, name);
        for (int i = 3; i < fields.length; i++) {
            if (!keyExists("SPECIES", fields[i])) {
                throw new UnknownSpeciesException(fields[i]);
            }
            Species species = getSpecies(fields[i]);
            newVaccine.addSpecies(species);
        }
        addVacine(newVaccine);
        setChanged();
    }

    /**
     * Register tree in the hotel.
     * @param fields 0 - type, 1 - id, 2 - name, 3 - age, 4 - base cleaning 
     * difficulty, 5 - leaf type.
     * @throws IOException
     * @throws UnrecognizedEntryException
     * @throws DuplicateTreeException
     */
    public Tree registerTree(String... fields) throws IOException, 
    UnrecognizedEntryException, DuplicateTreeException {
        String id = fields[1];
        if (keyExists("TREE", id)) {
            throw new DuplicateTreeException();
        }
        String name = fields[2];
        int age = Integer.parseInt(fields[3]);
        int difficulty = Integer.parseInt(fields[4]);
        Tree newTree = null;

        TreeState treeState = new EvergreenSpringState();
        switch(fields[5]) {
            case ("PERENE") :  
                switch(_season) {
                    case Spring -> treeState = new EvergreenSpringState();
                    case Summer -> treeState = new EvergreenSummerState();
                    case Autumn -> treeState = new EvergreenAutumnState();
                    case Winter -> treeState = new EvergreenWinterState();
                }
                newTree = new EverGreen(id, name, age, difficulty, _season, treeState);
                break;

            case ("CADUCA") :
                switch(_season) {
                    case Spring -> treeState = new DeciduousSpringState();
                    case Summer -> treeState = new DeciduousSummerState();
                    case Autumn -> treeState = new DeciduousAutumnState();
                    case Winter -> treeState = new DeciduousWinterState();
                }
                newTree = new Deciduous(id, name, age, difficulty, _season, treeState);
                break;
        }
        addTree(newTree);
        setChanged();
        return newTree;
    }

    /**
     * @param id
     * @return the species with that id.
     */
    public Species getSpecies(String id) {
        return _speciesList.get(id);
    }

    /**
     * @param id
     * @return the habitat with that id.
     */
    public Habitat getHabitat(String id) {
        return _habitatsList.get(id);
    }

    /**
     * @param id
     * @return the tree with that id.
     */
    public Tree getTree(String id) {
        return _treesList.get(id);
    }

    /**
     * Add animal to the animals map.
     * @param animal
     */
    public void addAnimal(Animal animal) {
        _animalsList.put(animal.getId(), animal);
    }

    /**
     * Add species to the species maps.
     * @param species
     */
    public void addSpecies(Species species) {
        _speciesList.put(species.getId(), species);
    }

    /**
     * Add employee to the employees map.
     * @param employee
     */
    public void addEmployee(Employee employee) {
        _employeesList.put(employee.getId(), employee);
    }

    /**
     * Add habitat to the habitats map.
     * @param habitat
     */
    public void addHabitat(Habitat habitat) {
        _habitatsList.put(habitat.getId(), habitat);
    }

    /**
     * Add vaccine to the vaccines map.
     * @param vaccine
     */
    public void addVacine(Vaccine vaccine) {
        _vaccinesList.put(vaccine.getId(), vaccine);
    }

    /**
     * Add tree to the trees map.
     * @param tree
     */
    public void addTree(Tree tree) {
        _treesList.put(tree.getId(), tree);
    }

    /**
     * Check if a certain entity is registerd in the hotel.
     * @return True if it is registered and False if not.
     * @param entry_type type of the entity.
     * @param id id of the entity.
     */
    public boolean keyExists(String entry_type, String id) {
        switch (entry_type) {
            case "ANIMAL": return _animalsList.get(id) != null;
            case "EMPLOYEE": return _employeesList.get(id) != null;
            case "HABITAT": return _habitatsList.get(id) != null;
            case "SPECIES": return _speciesList.get(id) != null;
            case "TREE": return _treesList.get(id) != null;
            case "VACCINE": return _vaccinesList.get(id) != null;
            default: return false;
        }
    }

    /**
     * Show all animals registered in the hotel.
     * @return all animals registered in the hotel.
     */
    public String showAllAnimals() {
        String allAnimals = "";
        for (String key : _animalsList.keySet()) {
            Animal animal = _animalsList.get(key);
            allAnimals += animal;
        }
        return allAnimals;
    }

    /**
     * Show all habitats registered in the hotel.
     * @return all habitats registered in the hotel.
     */
    public String showAllHabitats() {
        String allHabitats = "";
        for (String key : _habitatsList.keySet()) {
            Habitat habitat = _habitatsList.get(key);
            allHabitats += habitat;
        }
        return allHabitats;
    }

    /**
     * Show all employees registered in the hotel.
     * @return all employees registered in the hotel.
     */
    public String showAllEmployees() {
        String allEmployees = "";
        for (String key : _employeesList.keySet()) {
            Employee employee = _employeesList.get(key);
            allEmployees += employee;
        }
        return allEmployees;
    }

    /**
     * Show all vaccines registered in the hotel.
     * @return all vaccines registered in the hotel.
     */
    public String showAllVaccines() {
        String allVaccines = "";
        for (String key : _vaccinesList.keySet()) {
            Vaccine vaccine = _vaccinesList.get(key);
            allVaccines += vaccine;
        }
        return allVaccines;
    }

    public void advanceAllTreesSeason() {
        for (String treeId : _treesList.keySet()) {
            Tree tree = _treesList.get(treeId);
            tree.advanceSeason();
        }
    }

    public int advanceSeason() {
        advanceAllTreesSeason();
        _season = Season.values()[(_season.ordinal()+1) % 4];
        setChanged();
        return _season.ordinal();
    }

    public int showGlobalSatisfaction() {
        float globalSatisfaction = 0;
        for (Animal animal : _animalsList.values()) {
            globalSatisfaction += animal.getSatisfaction();
        }
        for (Employee employee : _employeesList.values()) {
                globalSatisfaction += employee.getSatisfaction();
        }
        return Math.round(globalSatisfaction);
    }

    public int showAnimalSatisfaction(String animalId) throws UnknownAnimalException {
        Animal animal = _animalsList.get(animalId);
        if (animal == null) throw new UnknownAnimalException();
        int satisfaction = Math.round(animal.getSatisfaction());
        return satisfaction;
    }

    public int showEmployeeSatisfaction(String employeeId) throws UnknownEmployeeException {
        Employee employee = _employeesList.get(employeeId);
        if (employee == null) throw new UnknownEmployeeException();
            return Math.round(employee.getSatisfaction());
    }

    public void transferToHabitat(String animalId, String habitatId)
    throws UnknownAnimalException, UnknownHabitatException {
        Animal animal = _animalsList.get(animalId);
        if (animal == null)
            throw new UnknownAnimalException();
        Habitat currentHabitat = _habitatsList.get(animal.getHabitatId());
        Habitat newHabitat = _habitatsList.get(habitatId);
        if (newHabitat == null)
            throw new UnknownHabitatException();
        currentHabitat.removeAnimal(animal);
        newHabitat.addAnimal(animal);
        animal.setHabitat(newHabitat);
        setChanged();
    }

    public void addTreeToHabitat(String habitatId, String treeId)
    throws UnknownHabitatException {
        Habitat habitat = _habitatsList.get(habitatId);
        Tree tree = _treesList.get(treeId);
        if (habitat == null)
            throw new UnknownHabitatException();
        habitat.addTree(tree);
        setChanged();
    }

    public String showAllTreesInHabitat(String habitatId) throws 
    UnknownHabitatException {
        Habitat habitat = _habitatsList.get(habitatId);
        if (habitat == null) throw new UnknownHabitatException();
        String allTrees = habitat.getAllTreesInHabitat();
        return allTrees;
    }

    public String showAllAnimalsInHabitat(String habitatId) throws UnknownHabitatException {
        Habitat habitat = _habitatsList.get(habitatId);
        if (habitat == null) throw new UnknownHabitatException();
        String allAnimals = habitat.getAllAnimalsInHabitat();
        return allAnimals;
    }

    public String showAllVaccinations() {
        String allVaccinations = "";
        for (Vaccination vaccination : _vaccinationRegister) {
            allVaccinations += vaccination;
        }
        return allVaccinations;
    }

    public String showAllWrongVaccinations() {
        String allWrongVaccinations = "";
        for (Vaccination vaccination : _wrongVaccinationRegister) {
            allWrongVaccinations += vaccination;
        }
        return allWrongVaccinations;
    }

    public String showVetMedicalActs(String vetId) 
    throws UnknownVeterinarianException {
        Employee employee = _employeesList.get(vetId);
        Vet vet;
        if (employee instanceof Vet) { 
            vet = (Vet) employee;
        } else {
            throw new UnknownVeterinarianException();
        }
        return vet.getVetMedicalActs();
    }

    public String showAnimalMedicalActs(String vetId) 
    throws UnknownAnimalException {
        Animal animal = _animalsList.get(vetId);
        if (animal == null)
            throw new UnknownAnimalException();
        return animal.getAnimalMedicalActs();
    }

    public void changeHabitatArea(String habitatId, int area) throws 
    UnknownHabitatException {
        Habitat habitat = _habitatsList.get(habitatId);
        if (habitat == null) throw new UnknownHabitatException();
        else habitat.setArea(area);
        setChanged();
    }

    public void changeHabitatInfluence(String habitatId, String speciesId, String stringInfluence) throws UnknownHabitatException, UnknownSpeciesException {
        Habitat habitat = _habitatsList.get(habitatId);
        if (habitat == null) throw new UnknownHabitatException();
        Species species = _speciesList.get(speciesId);
        if (species == null) throw new UnknownSpeciesException(speciesId);

        Influence influence;
        switch (stringInfluence) {
            case "POS" -> influence = Influence.POS;
            case "NEU" -> influence = Influence.NEU;
            case "NEG" -> influence = Influence.NEG;
            default -> influence = Influence.NEU;
        }
        habitat.changeHabitatInfluence(speciesId, influence);
        setChanged();
    }

    public void addResponsibility(String employeeId, String responsibilityId)
    throws UnknownResponsibilityException, UnknownEmployeeException {
        Employee employee = _employeesList.get(employeeId);
        if (employee == null) throw new UnknownEmployeeException();
        if (employee instanceof Vet) { 
            Vet vet = (Vet) employee;
            Species species = getSpecies(responsibilityId);
            if (species == null)
                throw new UnknownResponsibilityException();
            else {
                vet.addSpecies(species);
                species.incrementNumberOfVets();
            }
        }
        else { 
            Keeper keeper = (Keeper) employee; 
            Habitat habitat = getHabitat(responsibilityId);
            if (habitat == null) 
                throw new UnknownResponsibilityException();
            else {
                keeper.addHabitat(habitat);
                habitat.incrementNumberOfKeepers();
            }
        }
        setChanged();
    }

    public void removeResponsibility(String employeeId, String responsibilityId)
    throws UnknownResponsibilityException, UnknownEmployeeException {
        Employee employee = _employeesList.get(employeeId);
        if (employee == null) throw new UnknownEmployeeException();
        if (employee instanceof Vet) { 
            Vet vet = (Vet) employee;
            Species species = getSpecies(responsibilityId);
            if (species == null | vet.getSpecies(responsibilityId) == null)
                throw new UnknownResponsibilityException();
            else {
                vet.removeSpecies(species);
                species.decrementNumberOfVets();
            }
        }
        if (employee instanceof Keeper) { 
            Keeper keeper = (Keeper) employee; 
            Habitat habitat = getHabitat(responsibilityId);
            if (habitat == null | keeper.getHabitat(responsibilityId) == null) 
                throw new UnknownResponsibilityException();
            else {
                keeper.removeHabitat(habitat);
                habitat.decrementNumberOfKeepers();
            }
        }
        setChanged();
    }

    public void vaccinateAnimal(String vaccineId, String vetId, String animalId)
    throws UnknownVeterinarianException, UnknownAnimalException, UnknownVaccineException, 
    VeterinarianNotAuthorizedCoreException, WrongVaccinationException {
        Vaccine vaccine = _vaccinesList.get(vaccineId);
        Employee employee = _employeesList.get(vetId);
        Vet vet;
        Animal animal = _animalsList.get(animalId);
        if (vaccine == null)
            throw new UnknownVaccineException();
        if (employee instanceof Vet) { 
            vet = (Vet) employee;
        }
        else {
            throw new UnknownVeterinarianException();
        }
        if (animal == null) throw new UnknownAnimalException();
        if (vet.getSpecies(animal.getSpeciesId()) == null){
            throw new VeterinarianNotAuthorizedCoreException(animal.getSpeciesId());
        }

        Species species = animal.getSpecies();
        Vaccination vaccination = new Vaccination(species, vaccine, vet);
        animal.receiveVaccination(vaccination);
        _vaccinationRegister.add(vaccination);
        vaccine.incrementNumberOfApplications();
        vet.addMedicalAct(vaccination);
        setChanged();
        Damage damage = vaccination.getDamage();
        if (!(damage == Damage.NORMAL)) {
            _wrongVaccinationRegister.add(vaccination);
            throw new WrongVaccinationException();
        }
    }

    /**
     * @return changed.
     */
    public boolean isChanged() {
        return _changed;
    }

    /**
     * Set changed.
     */
    public void setChanged() {
        _changed = true;
    }

    /** 
     * Reset changed.
     */
    public void resetChanged() {
        _changed = false;
    }
}
