package integration4.evalebike.repository;

import integration4.evalebike.domain.Activity;
import integration4.evalebike.domain.RecentActivity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecentActivityRepositoryTest extends JpaRepository<RecentActivity, Integer> {
    List<RecentActivity> findByUserId(Integer userId, Sort sort);
    @Query("SELECT COUNT(ra) > 0 FROM RecentActivity ra WHERE ra.activity = :activity AND ra.description = :description AND ra.userId = :userId AND ra.date = :date")
    boolean existsByActivityAndDescriptionAndUserIdAndDate(@Param("activity") Activity activity, @Param("description") String description, @Param("userId") Long userId, @Param("date") LocalDateTime date);
}
