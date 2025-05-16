package integration4.evalebike.repository;

import integration4.evalebike.domain.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BikeRepository extends JpaRepository<Bike, String> {
    Optional<Bike> findByBikeQR(String bikeQR);

    List<Bike> findByBrandContainingIgnoreCase(String brand);
    List<Bike> findByModelContainingIgnoreCase(String model);
    List<Bike> findByChassisNumberContainingIgnoreCase(String chassisNumber);

}
