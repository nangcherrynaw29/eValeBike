package integration4.evalebike.repository;

import integration4.evalebike.domain.BikeOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class BikeOwnerRepository implements JpaRepository<BikeOwner, Integer> {
}
