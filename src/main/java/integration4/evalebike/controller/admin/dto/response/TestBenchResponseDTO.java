package integration4.evalebike.controller.admin.dto.response;

import integration4.evalebike.domain.Status;

import java.time.LocalDate;

public record TestBenchResponseDTO(
        Integer testBenchId,
        String testBenchName,
        String location,
        Status status,
        LocalDate lastCalibrationDate,
        Integer technicianId
) {
}