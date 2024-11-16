package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnknownVeterinarianException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowMedicalActsByVeterinarian extends Command<Hotel> {

    DoShowMedicalActsByVeterinarian(Hotel receiver) {
        super(Label.MEDICAL_ACTS_BY_VET, receiver);
        addStringField("vetId", hva.app.employee.Prompt.employeeKey());
    }

    @Override
    protected void execute() throws CommandException {
        String vetId = stringField("vetId");
        try {
            String medicalActs = _receiver.showVetMedicalActs(vetId);
            if (medicalActs.length() != 0) _display.popup(medicalActs);
        } catch (UnknownVeterinarianException e) {
            throw new UnknownVeterinarianKeyException(vetId);
        }
    }
}
