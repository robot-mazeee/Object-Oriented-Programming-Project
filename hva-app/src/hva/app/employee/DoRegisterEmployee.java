package hva.app.employee;

import java.io.IOException;

import hva.Hotel;
import hva.exceptions.DuplicateEmployeeException;
import hva.exceptions.UnknownSpeciesException;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnrecognizedEntryException;
import hva.app.exceptions.*;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterEmployee extends Command<Hotel> {

    DoRegisterEmployee(Hotel receiver) {
        super(Label.REGISTER_EMPLOYEE, receiver);
        addStringField("employeeId", Prompt.employeeKey());
        addStringField("name", Prompt.employeeName());
    }

    @Override
    protected void execute() throws CommandException {
        String employeeType;
        String employeeId = stringField("employeeId");
        String employeeName = stringField("name");
        do {
            employeeType = Form.requestString(Prompt.employeeType());
        } while (!employeeType.equals("VET") && !employeeType.equals("TRT"));

        try {
            _receiver.registerEmployee(new String[] {employeeType, employeeId, employeeName});
        } catch (DuplicateEmployeeException e) {
            throw new DuplicateEmployeeKeyException(employeeId);
        } catch (UnknownSpeciesException | UnknownHabitatException e1) {
            e1.printStackTrace();
        }
        catch (IOException | UnrecognizedEntryException e) { 
            e.printStackTrace(); 
        }
    }
}