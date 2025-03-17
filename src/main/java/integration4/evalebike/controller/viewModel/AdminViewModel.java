package integration4.evalebike.controller.viewModel;

import integration4.evalebike.domain.Administrator;

public record AdminViewModel(Integer id, String name, String companyName, String email) {

    public static AdminViewModel from(final Administrator administrator){
        return new AdminViewModel(
                administrator.getId(),
                administrator.getName(),
                administrator.getCompanyName(),
                administrator.getEmail());

    }
}
