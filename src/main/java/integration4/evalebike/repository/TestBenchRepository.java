package integration4.evalebike.repository;

import integration4.evalebike.domain.TestBench;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class TestBenchRepository implements JpaRepository<TestBench, Integer> {
}
