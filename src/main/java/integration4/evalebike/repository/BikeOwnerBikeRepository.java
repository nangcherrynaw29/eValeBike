package integration4.evalebike.repository;

import integration4.evalebike.controller.technician.dto.BikeDto;
import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwnerBike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BikeOwnerBikeRepository extends JpaRepository<BikeOwnerBike, Long> {
    List<BikeOwnerBike> findByBikeOwnerId(Integer bikeOwnerId);

}
