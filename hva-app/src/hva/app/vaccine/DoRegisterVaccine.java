package hva.app.vaccine;

import hva.Hotel;
import hva.app.exceptions.DuplicateVaccineKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.DuplicateVaccineException;
import hva.exceptions.UnknownSpeciesException;
import hva.exceptions.UnrecognizedEntryException;

import java.util.stream.Stream;
import java.io.IOException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoRegisterVaccine extends Command<Hotel> {

    DoRegisterVaccine(Hotel receiver) {
        super(Label.REGISTER_VACCINE, receiver);
        addStringField("vaccineId", Prompt.vaccineKey());
        addStringField("name", Prompt.vaccineName());
        addStringField("speciesList", Prompt.listOfSpeciesKeys());
    }

    @Override
    protected final void execute() throws CommandException {
        String vaccineId = stringField("vaccineId");
        String name = stringField("name");
        String[] species = stringField("speciesList").trim().split(",");

        try {
            String[] fields = new String[3+species.length];
            fields[0] = "VACINA";
            fields[1] = vaccineId;
            fields[2] = name;
            System.arraycopy(species, 0, fields, 3, species.length);
            _receiver.registerVaccine(fields);
            
        } catch (DuplicateVaccineException e) {
            throw new DuplicateVaccineKeyException(vaccineId);
        } catch (UnknownSpeciesException e) {
            throw new UnknownSpeciesKeyException(e.getId());
        } catch (IOException |UnrecognizedEntryException e) {
            e.printStackTrace();
        }
    }

}
