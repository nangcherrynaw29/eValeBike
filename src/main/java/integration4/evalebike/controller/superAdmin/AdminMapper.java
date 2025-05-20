package integration4.evalebike.controller.superAdmin;

import integration4.evalebike.controller.superAdmin.dto.AdministratorDto;
import integration4.evalebike.domain.Administrator;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AdminMapper {
    AdministratorDto toAdminDto(Administrator administrator);
}
