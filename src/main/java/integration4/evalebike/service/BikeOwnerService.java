package integration4.evalebike.service;

import integration4.evalebike.repository.BikeOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BikeOwnerService {
    private final BikeOwnerRepository bikeOwnerRepository;

    @Autowired
    public BikeOwnerService(BikeOwnerRepository bikeOwnerRepository) {
        this.bikeOwnerRepository = bikeOwnerRepository;
    }
}
