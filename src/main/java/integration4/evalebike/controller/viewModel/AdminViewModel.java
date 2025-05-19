package integration4.evalebike.controller.viewModel;

import integration4.evalebike.domain.Administrator;
import integration4.evalebike.domain.Company;

public record AdminViewModel(Integer id, String name, Company company, String email) {

    public static AdminViewModel from(final Administrator administrator){
        return new AdminViewModel(
                administrator.getId(),
                administrator.getName(),
                administrator.getCompany(),
                administrator.getEmail());

    }
}
