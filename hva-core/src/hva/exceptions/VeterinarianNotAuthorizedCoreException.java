package hva.exceptions;

import java.io.Serial;

public class VeterinarianNotAuthorizedCoreException extends Exception {
	@Serial
	private static final long serialVersionUID = 202410222359L;
    
    private String _speciesId;

    public VeterinarianNotAuthorizedCoreException(String speciesId){
        _speciesId = speciesId;
    }

    public String getSpeciesId(){
        return _speciesId;
    }
}
