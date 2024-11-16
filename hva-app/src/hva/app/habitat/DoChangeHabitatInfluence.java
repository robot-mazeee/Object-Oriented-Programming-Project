package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnknownSpeciesException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoChangeHabitatInfluence extends Command<Hotel> {

    DoChangeHabitatInfluence(Hotel receiver) {
        super(Label.CHANGE_HABITAT_INFLUENCE, receiver);
        addStringField("habitatId", Prompt.habitatKey());
        addStringField("speciesId", hva.app.animal.Prompt.speciesKey());
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("habitatId");
        String speciesId = stringField("speciesId");
        String influence;
        do {
            influence = Form.requestString(Prompt.habitatInfluence());
        } while (!influence.equals("POS") && !influence.equals("NEU")
        && !influence.equals("NEG"));

        try {
            _receiver.changeHabitatInfluence(habitatId, speciesId, influence);
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(habitatId);
        } catch (UnknownSpeciesException e) {
            throw new UnknownSpeciesKeyException(speciesId);
        }
    }
}
