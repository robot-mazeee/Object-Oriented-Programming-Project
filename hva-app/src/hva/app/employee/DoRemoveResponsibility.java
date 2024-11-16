package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.NoResponsibilityException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.UnknownEmployeeException;
import hva.exceptions.UnknownResponsibilityException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRemoveResponsibility extends Command<Hotel> {

    DoRemoveResponsibility(Hotel receiver) {
        super(Label.REMOVE_RESPONSABILITY, receiver);
        addStringField("employeeId", Prompt.employeeKey());
        addStringField("responsibilityId", Prompt.responsibilityKey());
    }

    @Override
    protected void execute() throws CommandException {
        String employeeId = stringField("employeeId");
        String responsibilityId = stringField("responsibilityId");
        try {
            _receiver.removeResponsibility(employeeId, responsibilityId);
        } catch (UnknownEmployeeException e) {
            throw new UnknownEmployeeKeyException(employeeId);
        } catch (UnknownResponsibilityException e) {
            throw new NoResponsibilityException(employeeId, responsibilityId);
        }
    }
}