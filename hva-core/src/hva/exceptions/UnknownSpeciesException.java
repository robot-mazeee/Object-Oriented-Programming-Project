package hva.exceptions;

import java.io.Serial;

public class UnknownSpeciesException extends Exception {
    @Serial
    private static final long serialVersionUID = 202410102210L;
    private String _speciesId;

    public UnknownSpeciesException(String speciesId){
        _speciesId = speciesId;
    }

    public String getId(){
        return _speciesId;
    }
}
