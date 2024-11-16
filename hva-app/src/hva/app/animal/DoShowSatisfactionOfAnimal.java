package hva.app.animal;

import hva.Hotel;
import hva.exceptions.UnknownAnimalException;
import hva.app.exceptions.UnknownAnimalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowSatisfactionOfAnimal extends Command<Hotel> {

    DoShowSatisfactionOfAnimal(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_ANIMAL, receiver);
        addStringField("animalId", Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String animalId = stringField("animalId");
        try {
            int animalSatisfaction = _receiver.showAnimalSatisfaction(animalId);
            _display.popup(animalSatisfaction);
        } catch (UnknownAnimalException e) {
            throw new UnknownAnimalKeyException(animalId);
        }
    }

}
