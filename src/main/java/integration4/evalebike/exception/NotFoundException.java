package integration4.evalebike.exception;

public class NotFoundException extends RuntimeException {

    private NotFoundException(final String message) {
        super(message);
    }

    public static NotFoundException forTechnician(final int technicianId) {
        return new NotFoundException(
                "Technician with ID %d was not found"
                        .formatted(technicianId)
        );
    }
}