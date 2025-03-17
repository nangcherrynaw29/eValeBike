package integration4.evalebike.controller.viewmodel;

import integration4.evalebike.domain.Technician;

public record TechnicianViewModel(Integer id, String name, String email, String password) {
    public static TechnicianViewModel fromTechnician(final Technician technician) {
        return new TechnicianViewModel(technician.getId(), technician.getName(), technician.getEmail(), technician.getPassword());
    }
}
