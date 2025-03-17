package integration4.evalebike.controller.admin.dto;

import integration4.evalebike.controller.admin.dto.request.TechnicianRequestDTO;
import integration4.evalebike.controller.admin.dto.response.TechnicianResponseDTO;
import integration4.evalebike.domain.Technician;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnicianMapper {

    // Convert DTO to Entity
    Technician toEntity(TechnicianRequestDTO dto);

    // Convert Entity to DTO
    TechnicianResponseDTO toDto(Technician technician);
}