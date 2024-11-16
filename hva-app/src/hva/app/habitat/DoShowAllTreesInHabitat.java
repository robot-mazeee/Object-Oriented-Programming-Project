package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.UnknownHabitatException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAllTreesInHabitat extends Command<Hotel> {

    DoShowAllTreesInHabitat(Hotel receiver) {
        super(Label.SHOW_TREES_IN_HABITAT, receiver);
        addStringField("habitatId", Prompt.habitatKey());
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("habitatId");

        try {
            String allTrees = _receiver.showAllTreesInHabitat(habitatId);
            if (allTrees.length() != 0) _display.popup(allTrees);
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(habitatId);
        }
    }
}
