package hva.app.animal;

import java.util.DuplicateFormatFlagsException;

import hva.Hotel;
import hva.app.exceptions.*;
import hva.exceptions.DuplicateAnimalException;
import hva.exceptions.DuplicateSpeciesException;
import hva.exceptions.DuplicateSpeciesNameException;
import hva.exceptions.UnknownSpeciesException;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnrecognizedEntryException;

import java.io.IOException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterAnimal extends Command<Hotel> {

    DoRegisterAnimal(Hotel receiver) {
        super(Label.REGISTER_ANIMAL, receiver);
        addStringField("animalId", Prompt.animalKey());
        addStringField("animalName", Prompt.animalName());
        addStringField("speciesId", Prompt.speciesKey());
        addStringField("habitatId", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String animalId = stringField("animalId");
        String animalName = stringField("animalName");
        String speciesId = stringField("speciesId");
        String habitatId = stringField("habitatId");

        try {
            _receiver.registerAnimal(new String[] {"ANIMAL", animalId, 
            animalName, speciesId, habitatId});
        } catch (DuplicateAnimalException e) {
            throw new DuplicateAnimalKeyException(animalId);
        } catch (UnknownSpeciesException e1) {
            String speciesName = Form.requestString(Prompt.speciesName());
            try {
                _receiver.registerSpecies(new String[] {"ESPÉCIE",
                speciesId, speciesName});
            } catch (DuplicateSpeciesException |
            DuplicateSpeciesNameException e2) {
                e2.printStackTrace();
            } catch (IOException | UnrecognizedEntryException e2){
                e2.printStackTrace();
            }
            try {
                _receiver.registerAnimal(new String[] {"ANIMAL", animalId, 
            animalName, speciesId, habitatId});
            } 
            catch (DuplicateAnimalException | UnknownSpeciesException e2) {
                    e2.printStackTrace();
            } catch (UnknownHabitatException e2){
                throw new UnknownHabitatKeyException(habitatId);
            } catch (IOException | UnrecognizedEntryException e2){
                e2.printStackTrace();
            }
        } catch (UnknownHabitatException e2){
            throw new UnknownHabitatKeyException(habitatId);
        } catch (IOException |UnrecognizedEntryException e) { e.printStackTrace(); }
    }
}