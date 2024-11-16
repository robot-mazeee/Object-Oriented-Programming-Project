package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.exceptions.UnknownAnimalException;
import hva.exceptions.UnknownVeterinarianException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowMedicalActsOnAnimal extends Command<Hotel> {

    DoShowMedicalActsOnAnimal(Hotel receiver) {
        super(Label.MEDICAL_ACTS_ON_ANIMAL, receiver);
        addStringField("animalId", hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected void execute() throws CommandException {
        String animalId = stringField("animalId");
        try {
            String medicalActs = _receiver.showAnimalMedicalActs(animalId);
            if (medicalActs.length() != 0) _display.popup(medicalActs);
        } catch (UnknownAnimalException e) {
            throw new UnknownAnimalKeyException(animalId);
        }
    }

}
