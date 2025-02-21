package integration4.evalebike.repository;

import integration4.evalebike.domain.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class TechnicianRepository implements JpaRepository<Technician, Integer> {
}
