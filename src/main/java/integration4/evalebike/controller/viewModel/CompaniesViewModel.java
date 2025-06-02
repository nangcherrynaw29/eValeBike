package integration4.evalebike.controller.viewModel;

import integration4.evalebike.domain.Company;

import java.util.List;

public record CompaniesViewModel (List<CompanyViewModel> companies){
    public static CompaniesViewModel from(final List<Company> companies){
        final var companyViewModels =companies.stream().map(CompanyViewModel::from).toList();
        return new CompaniesViewModel(companyViewModels);

    }
}
