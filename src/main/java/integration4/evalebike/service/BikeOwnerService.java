package integration4.evalebike.service;

import integration4.evalebike.controller.technician.dto.BikeWithChassisNumberDto;
import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.domain.BikeOwnerBike;
import integration4.evalebike.repository.BikeOwnerBikeRepository;
import integration4.evalebike.repository.BikeOwnerRepository;
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
    private final QrCodeService qrCodeService;

    @Autowired
    public BikeOwnerService(BikeOwnerRepository bikeOwnerRepository, BikeOwnerBikeRepository bikeOwnerBikeRepository, QrCodeService qrCodeService) {
        this.bikeOwnerRepository = bikeOwnerRepository;
        this.bikeOwnerBikeRepository = bikeOwnerBikeRepository;
        this.qrCodeService = qrCodeService;
    }

    public List<BikeOwner> getAllBikeOwners() {
        return bikeOwnerRepository.findAll();
    }

    public BikeOwner addBikeOwner(String name, String email, String phoneNumber, LocalDate birthDate) {
        BikeOwner bikeOwner = new BikeOwner(name, email, phoneNumber, birthDate);
        return bikeOwnerRepository.save(bikeOwner);
    }

    public List<BikeWithChassisNumberDto> getAllBikeWithChassisNumbersOfOwner(Integer bikeOwnerId) {
        List<BikeOwnerBike> bikeOwnerBikes = bikeOwnerBikeRepository.findByBikeOwnerId(bikeOwnerId);
        bikeOwnerBikes.forEach(bikeOwnerBike -> {
            String qrImage = qrCodeService.generateQrCodeBase64(bikeOwnerBike.getBike().getBikeQR(), 200, 200);
            bikeOwnerBike.setQrImage(qrImage);
        });

        return bikeOwnerBikes.stream().map(BikeWithChassisNumberDto::fromBikeOwnerBike).collect(Collectors.toList());

    }
}
