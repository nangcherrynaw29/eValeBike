package integration4.evalebike.controller.Technician;

import integration4.evalebike.controller.Technician.dto.*;
import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.service.BikeOwnerService;
import integration4.evalebike.service.BikeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/technician")
public class TechnicianAPIController {

    private final BikeService bikeService;
    private final BikeOwnerService bikeOwnerService;
    private final BikeMapper bikeMapper;
    private final BikeOwnerMapper bikeOwnerMapper;

    public TechnicianAPIController(BikeService bikeService, BikeOwnerService bikeOwnerService, BikeMapper bikeMapper, BikeOwnerMapper bikeOwnerMapper) {
        this.bikeService = bikeService;
        this.bikeOwnerService = bikeOwnerService;
        this.bikeMapper = bikeMapper;
        this.bikeOwnerMapper = bikeOwnerMapper;
    }

    @PostMapping("/bikes")
    public ResponseEntity<BikeDto> createBike(@RequestBody @Valid final AddBikeDto addBikeDto) {
        final Bike bike = bikeService.addBike(addBikeDto.brand(), addBikeDto.model(), addBikeDto.chassisNumber(), addBikeDto.productionYear(),
                addBikeDto.bikeSize(), addBikeDto.mileage(), addBikeDto.gearType(), addBikeDto.engineType(), addBikeDto.powerTrain(),
                addBikeDto.accuCapacity(), addBikeDto.maxSupport(), addBikeDto.maxEnginePower(), addBikeDto.nominalEnginePower(),
                addBikeDto.engineTorque(), addBikeDto.lastTestDate());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bikeMapper.toBikeDto(bike));
    }

    @PostMapping("/bikeOwners")
    public ResponseEntity<BikeOwnerDto> createBikeOwner(@RequestBody @Valid final AddBikeOwnerDto addBikeOwnerDto) {
        final BikeOwner bikeOwner = bikeOwnerService.addBikeOwner(addBikeOwnerDto.name(), addBikeOwnerDto.email(), addBikeOwnerDto.phoneNumber(), addBikeOwnerDto.birthDate());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bikeOwnerMapper.toBikeOwnerDto(bikeOwner));
    }
}
