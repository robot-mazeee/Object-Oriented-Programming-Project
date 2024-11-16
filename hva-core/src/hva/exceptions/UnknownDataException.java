package hva.exceptions;

public class UnknownDataException extends Exception {
    private static final long serialVersionUID = 202410092129L;

    public UnknownDataException(String unknown_data) {
        super(unknown_data);
    }
}
