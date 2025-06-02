package integration4.evalebike.repository;

import integration4.evalebike.domain.Company;
import integration4.evalebike.domain.Technician;
import integration4.evalebike.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Integer> {
    List<Technician> findByUserStatus(UserStatus userStatus);

    List<Technician> findByUserStatusAndCompany(UserStatus status, Company company);

    @Query("SELECT t FROM Technician t " +
            "WHERE (:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:email IS NULL OR LOWER(t.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    List<Technician> findByFilters(
            @Param("name") String name,
            @Param("email") String email);

    Optional<Technician> findByEmail(String technicianUsername);
}