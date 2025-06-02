package integration4.evalebike.controller.admin.dto.response;

import integration4.evalebike.domain.Technician;

public record TechnicianResponseDTO(Integer id, String name, String email) {
    public static TechnicianResponseDTO toDto(Technician technician) {
        if (technician == null) {
            return null;
        }

        // Return the TechnicianResponseDTO using the Technician's properties
        return new TechnicianResponseDTO(
                technician.getId(),           // Assuming the Technician entity has an 'id' field
                technician.getName(),         // Get the Technician's name
                technician.getEmail()        // Get the Technician's email
        );
    }
}