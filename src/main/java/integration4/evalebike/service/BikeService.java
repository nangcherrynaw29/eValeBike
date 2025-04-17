package integration4.evalebike.service;

import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeSize;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.BikeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class BikeService {
    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public List<Bike> getBikes() {
        return bikeRepository.findAll();
    }

    public Bike getBikeByQR(String qrCode) {
        return bikeRepository.findById(qrCode).orElseThrow(() -> NotFoundException.forBike(qrCode));
    }

    public Bike addBike(String brand, String model, String chassisNumber, int productionYear, BikeSize bikeSize,
                        int mileage, String gearType, String engineType,
                        String powerTrain,
                        double accuCapacity, double maxSupport,
                        double maxEnginePower, double nominalEnginePower, double engineTorque, LocalDate lastTestDate) throws Exception {

        Bike bike = new Bike(brand, model, chassisNumber, productionYear, bikeSize, mileage, gearType, engineType, powerTrain,
                accuCapacity, maxSupport, maxEnginePower, nominalEnginePower, engineTorque, lastTestDate);

        return bikeRepository.save(bike);
    }

    public Optional<Bike> findById(String bikeQR) {
        return bikeRepository.findById(bikeQR);
    }
}
