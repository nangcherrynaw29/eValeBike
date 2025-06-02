package integration4.evalebike.controller.viewModel;

import integration4.evalebike.domain.Company;

public record CompanyViewModel(Integer id, String name, String address, String email, String phone) {

    public static CompanyViewModel from(final Company company) {
        return new CompanyViewModel(
                company.getId(),
                company.getName(),
                company.getAddress(),
                company.getEmail(),
                company.getPhone());
    }
}
