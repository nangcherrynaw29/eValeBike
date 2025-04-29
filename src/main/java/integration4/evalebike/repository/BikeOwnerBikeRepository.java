package integration4.evalebike.repository;

import integration4.evalebike.controller.technician.dto.BikeDto;
import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwnerBike;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BikeOwnerBikeRepository extends JpaRepository<BikeOwnerBike, Long> {
    List<BikeOwnerBike> findByBikeOwnerId(Integer bikeOwnerId);
    void deleteByBikeOwnerId(Integer bikeOwnerId);

    @Modifying
    @Transactional
    @Query("DELETE FROM BikeOwnerBike bob WHERE bob.bike.bikeQR = :bikeqr")
    void deleteByBikeQr(@Param("bikeqr") String bikeqr);
}
