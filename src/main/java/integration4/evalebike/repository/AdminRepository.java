package integration4.evalebike.repository;

import integration4.evalebike.domain.Administrator;
import integration4.evalebike.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Administrator, Integer> {

    List<Administrator> findByUserStatus(UserStatus userStatus);
}
