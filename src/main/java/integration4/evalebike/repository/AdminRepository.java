package integration4.evalebike.repository;

import integration4.evalebike.domain.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AdminRepository implements JpaRepository<Administrator, Integer> {
}
