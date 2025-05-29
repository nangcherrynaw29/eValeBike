package integration4.evalebike.repository;

import integration4.evalebike.domain.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BikeRepositoryTest extends JpaRepository<Bike, String> {
    Optional<Bike> findByBikeQR(String bikeQR);

}
