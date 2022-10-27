package pe.gob.regioncusco.sipac.mss3.common;

public class BadRequestException extends RuntimeException {
    private static final String DESCRIPTION = "Bad Request Exception";

    public BadRequestException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
