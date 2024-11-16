package hva.app.vaccine;

import hva.Hotel;
import hva.app.exceptions.VeterinarianNotAuthorizedException;
import hva.exceptions.UnknownAnimalException;
import hva.exceptions.UnknownVaccineException;
import hva.exceptions.UnknownVeterinarianException;
import hva.exceptions.VeterinarianNotAuthorizedCoreException;
import hva.exceptions.WrongVaccinationException;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownVaccineKeyException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoVaccinateAnimal extends Command<Hotel> {

    DoVaccinateAnimal(Hotel receiver) {
        super(Label.VACCINATE_ANIMAL, receiver);
        addStringField("vaccineId", Prompt.vaccineKey());
        addStringField("vetId", Prompt.veterinarianKey());
        addStringField("animalId", hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String vaccineId = stringField("vaccineId");
        String vetId = stringField("vetId");
        String animalId = stringField("animalId");

        try {
            _receiver.vaccinateAnimal(vaccineId, vetId, animalId);
        } catch (UnknownVaccineException e) {
            throw new UnknownVaccineKeyException(vaccineId);
        } catch (UnknownAnimalException e) {
            throw new UnknownAnimalKeyException(animalId);
        } catch (UnknownVeterinarianException e) {
            throw new UnknownVeterinarianKeyException(vetId);
        } catch (VeterinarianNotAuthorizedCoreException e) {
            throw new VeterinarianNotAuthorizedException(vetId, e.getSpeciesId());
        } catch (WrongVaccinationException e) {
            _display.popup(Message.wrongVaccine(vaccineId, animalId));
        }
    }
}
