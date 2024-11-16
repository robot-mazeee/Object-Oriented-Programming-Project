package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.exceptions.UnknownHabitatException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAnimalsInHabitat extends Command<Hotel> {

    DoShowAnimalsInHabitat(Hotel receiver) {
        super(Label.ANIMALS_IN_HABITAT, receiver);
        addStringField("habitatId", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("habitatId");
        try {
            String animalsInHabitat = _receiver.showAllAnimalsInHabitat(habitatId);
            if (animalsInHabitat.length() != 0) _display.popup(animalsInHabitat);
        } catch (UnknownHabitatException e) {
            throw new UnknownAnimalKeyException(habitatId);
        }
    }
}
