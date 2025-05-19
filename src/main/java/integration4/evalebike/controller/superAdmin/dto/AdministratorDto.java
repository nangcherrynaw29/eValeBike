package integration4.evalebike.controller.superAdmin.dto;

import integration4.evalebike.domain.Company;

public record AdministratorDto(Integer id, String name, String email, Company company) {
}
