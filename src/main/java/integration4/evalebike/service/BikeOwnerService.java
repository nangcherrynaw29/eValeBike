package integration4.evalebike.service;

import integration4.evalebike.controller.technician.dto.BikeDto;
import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.domain.BikeOwnerBike;

import integration4.evalebike.domain.UserStatus;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.BikeOwnerBikeRepository;
import integration4.evalebike.repository.BikeOwnerRepository;
import integration4.evalebike.repository.BikeRepository;
import integration4.evalebike.utility.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class BikeOwnerService {
    private final BikeOwnerRepository bikeOwnerRepository;
    private final BikeOwnerBikeRepository bikeOwnerBikeRepository;
    private final BikeRepository bikeRepository;
    private final QrCodeService qrCodeService;
    private final PasswordUtility passwordUtility;

    @Autowired
    public BikeOwnerService(BikeOwnerRepository bikeOwnerRepository, BikeOwnerBikeRepository bikeOwnerBikeRepository, BikeRepository bikeRepository, QrCodeService qrCodeService, PasswordUtility passwordUtility) {
        this.bikeOwnerRepository = bikeOwnerRepository;
        this.bikeOwnerBikeRepository = bikeOwnerBikeRepository;
        this.bikeRepository = bikeRepository;
        this.qrCodeService = qrCodeService;
        this.passwordUtility = passwordUtility;
    }

    public List<BikeOwner> getAll() {
        return bikeOwnerRepository.findByUserStatus(UserStatus.APPROVED);
    }

    public BikeOwner add(String name, String email, String phoneNumber, LocalDate birthDate) {
        String rawPassword = passwordUtility.generateRandomPassword(8);
        String hashedPassword = passwordUtility.hashPassword(rawPassword);

        BikeOwner bikeOwner = new BikeOwner(name, email, phoneNumber, birthDate);
        bikeOwner.setPassword(hashedPassword);
        passwordUtility.sendPasswordEmail(email, rawPassword);
        return bikeOwnerRepository.save(bikeOwner);
    }

    public List<BikeDto> getAllBikes(Integer bikeOwnerId) {
        List<Bike> bikes = bikeOwnerBikeRepository.findByBikeOwnerId(bikeOwnerId)
                .stream()
                .map(BikeOwnerBike::getBike)
                .collect(Collectors.toList());

        bikes.forEach(bike -> {
            String qrImage = qrCodeService.generateQrCodeBase64(bike.getBikeQR(), 200, 200);
            bike.setQrCodeImage(qrImage); // Set the transient field, not the ID
        });

        return bikes.stream().map(BikeDto::fromBike).collect(Collectors.toList());
    }

    public void delete(Integer id) {
        BikeOwner bikeOwner = bikeOwnerRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forBikeOwner(id));
        bikeOwnerBikeRepository.deleteByBikeOwnerId(id);
        bikeOwnerRepository.delete(bikeOwner);
    }

    public long countOwnersWithBirthdayToday() {
        LocalDate today = LocalDate.now();
        return bikeOwnerRepository.countByBirthdayToday(today.getMonthValue(), today.getDayOfMonth());
    }
}