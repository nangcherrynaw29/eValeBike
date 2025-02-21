package integration4.evalebike.repository;

import integration4.evalebike.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class ReportRepository implements JpaRepository<Report, Integer> {
}
