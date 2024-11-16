package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.UnknownEmployeeException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowSatisfactionOfEmployee extends Command<Hotel> {

    DoShowSatisfactionOfEmployee(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_EMPLOYEE, receiver);
        addStringField("employeeId", Prompt.employeeKey());
    }

    @Override
    protected void execute() throws CommandException {
        String employeeId = stringField("employeeId");
        try {
            int employeeSatisfaction = _receiver.showEmployeeSatisfaction(employeeId);
            _display.popup(employeeSatisfaction);
        } catch (UnknownEmployeeException e) {
            throw new UnknownEmployeeKeyException(employeeId);
        }
    }

}
