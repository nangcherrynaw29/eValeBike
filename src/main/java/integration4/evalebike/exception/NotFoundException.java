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

    public static NotFoundException forBikeOwner(final Integer bikeOwnerId) {
        return new NotFoundException(
                "Bike Owner with ID %s was not found"
                        .formatted(bikeOwnerId)
        );
    }

    public static NotFoundException forBike(final String bikeId) {
        return new NotFoundException(
                "Bike with ID %s was not found"
                        .formatted(bikeId)
        );
    }

    public static NotFoundException forSuperAdmin(final int superAdminId) {
        return new NotFoundException(
                "Super admin with ID %d was not found.".
                        formatted(superAdminId));
    }

    public static NotFoundException forAdmin(final int adminId) {
        return new NotFoundException(
                "Admin with ID %d was not found."
                        .formatted(adminId));
    }

    public static NotFoundException forTestBench(final int testBenchId) {
        return new NotFoundException(
                "Test bench with ID %d was not found"
                        .formatted(testBenchId)
        );
    }

    public static NotFoundException forCompany(final int companyId) {
        return new NotFoundException(
                "Company with ID %d was not found"
                        .formatted(companyId)
        );
    }
}