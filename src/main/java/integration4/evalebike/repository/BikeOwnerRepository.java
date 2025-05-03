package integration4.evalebike.repository;

import integration4.evalebike.domain.BikeOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeOwnerRepository extends JpaRepository<BikeOwner, Integer> {
    @Query("SELECT COUNT(b) FROM BikeOwner b WHERE EXTRACT(MONTH FROM b.birthDate) = :month AND EXTRACT(DAY FROM b.birthDate) = :day")
    long countByBirthdayToday(@Param("month") int month, @Param("day") int day);
}
