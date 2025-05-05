package integration4.evalebike.repository;

import integration4.evalebike.domain.Technician;
import integration4.evalebike.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Integer> {
    List<Technician> findByUserStatus(UserStatus userStatus);
}
