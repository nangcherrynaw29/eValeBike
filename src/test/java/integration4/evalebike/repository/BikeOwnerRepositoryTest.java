package integration4.evalebike.repository;

import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeOwnerRepositoryTest extends JpaRepository<BikeOwner, Integer> {
    @Query("SELECT COUNT(b) FROM BikeOwner b WHERE EXTRACT(MONTH FROM b.birthDate) = :month AND EXTRACT(DAY FROM b.birthDate) = :day")
    long countByBirthdayToday(@Param("month") int month, @Param("day") int day);

    List<BikeOwner> findByUserStatus(UserStatus userStatus);
}
