package integration4.evalebike.controller.technician.dto;

import integration4.evalebike.domain.BikeOwner;

import java.time.LocalDate;

public record BikeOwnerDto(int id, String name, String email, String phoneNumber, LocalDate birthDate) {
    public static BikeOwnerDto fromBikeOwner(final BikeOwner bikeOwner) {
        return new BikeOwnerDto(bikeOwner.getId(), bikeOwner.getName(), bikeOwner.getEmail(), bikeOwner.getPhoneNumber(), bikeOwner.getBirthDate());
    }
}
