package hva.app.vaccine;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowVaccinations extends Command<Hotel> {

    DoShowVaccinations(Hotel receiver) {
        super(Label.SHOW_VACCINATIONS, receiver);
    }

    @Override
    protected final void execute() {
        String allVaccinations = _receiver.showAllVaccinations();
        if (allVaccinations.length() != 0) _display.popup(allVaccinations);
    }
}
