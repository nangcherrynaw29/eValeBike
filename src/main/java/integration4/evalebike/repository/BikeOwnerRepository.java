package integration4.evalebike.repository;

import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeOwnerRepository extends JpaRepository<BikeOwner, Integer> {

}
