package integration4.evalebike.repository;

import integration4.evalebike.domain.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminRepositoryTest extends JpaRepository<SuperAdmin, Integer> {



}
