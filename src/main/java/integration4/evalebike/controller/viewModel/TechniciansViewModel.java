package integration4.evalebike.controller.viewModel;

import integration4.evalebike.domain.Technician;

import java.util.List;

public record TechniciansViewModel(List<TechnicianViewModel> technicians) {
    public static TechniciansViewModel fromTechnician(final List<Technician> technicians) {
        return new TechniciansViewModel(technicians.stream().map(TechnicianViewModel::fromTechnician).toList());
    }
}
