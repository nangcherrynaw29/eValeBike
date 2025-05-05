package integration4.evalebike.repository;


import integration4.evalebike.domain.TestReportEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TestReportEntryRepository extends JpaRepository<TestReportEntry, Long> {
    List<TestReportEntry> findTestReportEntriesByTestReportId(String testReportId);
}