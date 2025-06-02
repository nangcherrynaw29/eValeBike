package integration4.evalebike.controller.superAdmin;

import integration4.evalebike.controller.superAdmin.dto.CompanyDto;
import integration4.evalebike.domain.Company;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CompanyMapper {
    CompanyDto toCompanyDto(Company company);
}
