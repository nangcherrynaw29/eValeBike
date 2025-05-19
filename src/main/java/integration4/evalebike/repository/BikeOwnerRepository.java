package integration4.evalebike.repository;

import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.domain.Company;
import integration4.evalebike.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeOwnerRepository extends JpaRepository<BikeOwner, Integer> {
    @Query("SELECT COUNT(b) FROM BikeOwner b WHERE EXTRACT(MONTH FROM b.birthDate) = :month AND EXTRACT(DAY FROM b.birthDate) = :day")
    long countByBirthdayToday(@Param("month") int month, @Param("day") int day);

    List<BikeOwner> findByUserStatus(UserStatus userStatus);
    List<BikeOwner> findByUserStatusAndCompany(UserStatus status, Company company);

    @Query("SELECT b FROM BikeOwner b " +
            "WHERE b.userStatus = :status " +
            "AND (:name IS NULL OR LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:email IS NULL OR LOWER(b.email) LIKE LOWER(CONCAT('%', :email, '%'))) " +
            "AND (:phoneNumber IS NULL OR b.phoneNumber LIKE CONCAT('%', :phoneNumber, '%'))")
    List<BikeOwner> findByFilters(
            @Param("name") String name,
            @Param("email") String email
    );
}
