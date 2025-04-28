package integration4.evalebike.repository;

import integration4.evalebike.domain.RecentActivity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecentActivityRepository extends JpaRepository<RecentActivity, Integer> {
    List<RecentActivity> findByUserId(Integer userId, Sort sort);
}
