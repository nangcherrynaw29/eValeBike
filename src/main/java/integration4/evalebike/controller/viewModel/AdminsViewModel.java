package integration4.evalebike.controller.viewModel;

import integration4.evalebike.domain.Administrator;

import java.util.List;

public record AdminsViewModel (List<AdminViewModel> admins){
    public static AdminsViewModel from(final List<Administrator> administrators){
        final var adminViewModels =administrators.stream().map(AdminViewModel::from).toList();
        return new AdminsViewModel(adminViewModels);

    }
}
