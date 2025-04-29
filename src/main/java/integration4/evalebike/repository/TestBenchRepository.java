package integration4.evalebike.repository;

import integration4.evalebike.domain.Status;
import integration4.evalebike.domain.TestBench;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestBenchRepository extends JpaRepository<TestBench, Integer> {
    List<TestBench> findByTechnicianIdAndStatus(Integer technicianId, Status status);
}
