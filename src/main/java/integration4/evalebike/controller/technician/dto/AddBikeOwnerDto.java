package integration4.evalebike.controller.technician.dto;

import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record AddBikeOwnerDto(String name, String email, String phoneNumber, @PastOrPresent LocalDate birthDate) {
}
