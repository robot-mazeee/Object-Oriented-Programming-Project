package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.DuplicateHabitatKeyException;
import hva.exceptions.DuplicateHabitatException;
import hva.exceptions.UnknownTreeException;
import hva.exceptions.UnrecognizedEntryException;

import java.io.IOException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoRegisterHabitat extends Command<Hotel> {

    DoRegisterHabitat(Hotel receiver) {
        super(Label.REGISTER_HABITAT, receiver);
        addStringField("habitatId", Prompt.habitatKey());
        addStringField("name", Prompt.habitatName());
        addStringField("area", Prompt.habitatArea());
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("habitatId");
        String habitatName = stringField("name");
        String area = stringField("area");
        try {
            _receiver.registerHabitat(new String[] {"HABITAT", habitatId, 
            habitatName, area});
        } catch (DuplicateHabitatException e) {
            throw new DuplicateHabitatKeyException(habitatId);
        } catch (IOException |UnrecognizedEntryException |
        UnknownTreeException e) {
            e.printStackTrace();
        }
        
    }

}
