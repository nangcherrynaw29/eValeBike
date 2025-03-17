package integration4.evalebike.repository;

import integration4.evalebike.domain.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends JpaRepository<Bike, String> {
}
