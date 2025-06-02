package integration4.evalebike.controller.admin.dto.request;

import integration4.evalebike.domain.Technician;

public record TechnicianRequestDTO(String name, String email, Integer companyId) {

    public static Technician toEntity(TechnicianRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        // Create a new Technician and map properties from TechnicianRequestDTO
        Technician technician = new Technician();

        // Set values from DTO to Technician (the fields common between TechnicianRequestDTO and Technician)
        technician.setName(dto.name());
        technician.setEmail(dto.email());
        return technician;
    }
}
