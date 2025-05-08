package integration4.evalebike.repository;

import integration4.evalebike.domain.VisualInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisualInspectionRepository extends JpaRepository<VisualInspection, Integer> {

    VisualInspection findVisualInspectionByTestReportId(String id);
}
