package hva.app.animal;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;

class DoShowAllAnimals extends Command<Hotel> {

    DoShowAllAnimals(Hotel receiver) {
        super(Label.SHOW_ALL_ANIMALS, receiver);
    }

    @Override
    protected final void execute() {
        String allAnimals = _receiver.showAllAnimals();
        if (allAnimals.length() != 0) _display.popup(allAnimals);
    }
}
