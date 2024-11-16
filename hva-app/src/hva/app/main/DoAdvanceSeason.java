package hva.app.main;

import hva.HotelManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoAdvanceSeason extends Command<HotelManager> {
    DoAdvanceSeason(HotelManager receiver) {
        super(Label.ADVANCE_SEASON, receiver);
	}

    @Override
    protected final void execute() {
        int newSeason = _receiver.getHotel().advanceSeason();
        _display.popup(newSeason);
    }
}
