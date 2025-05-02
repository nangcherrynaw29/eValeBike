package integration4.evalebike.repository;
import integration4.evalebike.domain.TestReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TestReportRepository extends JpaRepository<TestReport, String> {
    @Query("SELECT tr " +
            "FROM TestReport tr " +
            "LEFT JOIN FETCH tr.reportEntries " +
            "WHERE tr.id = :id")
    Optional<TestReport> findEntriesByID(@Param("id") String id);

    @Query("SELECT tr FROM TestReport tr LEFT JOIN FETCH tr.bike WHERE tr.id = :id")
    Optional<TestReport> findBikeByID(@Param("id") String id);

    @Query("SELECT tr FROM TestReport tr LEFT JOIN FETCH tr.bike")
    List<TestReport> findAllWithBike();
}