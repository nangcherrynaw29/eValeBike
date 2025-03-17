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

    public static NotFoundException forBike(final String bikeId) {
        return new NotFoundException(
                "Bike with ID %s was not found"
                        .formatted(bikeId)
        );
    }

    public static NotFoundException forSuperAdmin(final int superAdminId) {
        return new NotFoundException(String.format("Super admin with ID %d was not found.", superAdminId));
    }

    public static NotFoundException forAdmin(final int adminId) {
        return new NotFoundException(String.format("Admin with ID %d was not found.", adminId));
    }

}