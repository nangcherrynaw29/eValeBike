package integration4.evalebike.exceptions;

public class NotFoundException extends RuntimeException {
    private NotFoundException(final String message) {
        super(message);
    }

    public static NotFoundException superAdmin(final int id) {
        return new NotFoundException(String.format("Super admin with ID %d was not found.", id));
    }

    public static NotFoundException admin(final int id) {
        return new NotFoundException(String.format("Admin with ID %d was not found.", id));
    }
}

