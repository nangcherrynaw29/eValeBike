package integration4.evalebike.service;

import integration4.evalebike.controller.technician.dto.BikeDto;
import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.domain.BikeOwnerBike;

import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.BikeOwnerBikeRepository;
import integration4.evalebike.repository.BikeOwnerRepository;
import integration4.evalebike.repository.BikeRepository;
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
    private  final BikeOwnerBikeRepository bikeOwnerBikeRepository;
    private final BikeRepository bikeRepository;
    private final QrCodeService qrCodeService;

    @Autowired
    public BikeOwnerService(BikeOwnerRepository bikeOwnerRepository, BikeOwnerBikeRepository bikeOwnerBikeRepository, BikeRepository bikeRepository, QrCodeService qrCodeService) {
        this.bikeOwnerRepository = bikeOwnerRepository;
        this.bikeOwnerBikeRepository = bikeOwnerBikeRepository;
        this.bikeRepository = bikeRepository;
        this.qrCodeService = qrCodeService;
    }

    public List<BikeOwner> getAll() {
        return bikeOwnerRepository.findAll();
    }

    public BikeOwner add(String name, String email, String phoneNumber, LocalDate birthDate) {
        BikeOwner bikeOwner = new BikeOwner(name, email, phoneNumber, birthDate);
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
