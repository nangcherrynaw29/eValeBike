package integration4.evalebike.service;

import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.repository.BikeOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class BikeOwnerService {
    private final BikeOwnerRepository bikeOwnerRepository;

    @Autowired
    public BikeOwnerService(BikeOwnerRepository bikeOwnerRepository) {
        this.bikeOwnerRepository = bikeOwnerRepository;
    }

    public List<BikeOwner> getAllBikeOwners() {
        return bikeOwnerRepository.findAll();
    }

    public BikeOwner addBikeOwner(String name, String email, String phoneNumber, LocalDate birthDate) {
        BikeOwner bikeOwner = new BikeOwner(name, email, phoneNumber, birthDate);
        return bikeOwnerRepository.save(bikeOwner);
    }
}
