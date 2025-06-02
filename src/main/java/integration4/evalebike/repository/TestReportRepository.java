package integration4.evalebike.repository;

import integration4.evalebike.domain.TestReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestReportRepository extends JpaRepository<TestReport, String> {
    @Query("SELECT tr " + "FROM TestReport tr " + "LEFT JOIN FETCH tr.reportEntries " + "WHERE tr.id = :id")
    Optional<TestReport> findEntriesByID(@Param("id") String id);

    @Query("SELECT tr FROM TestReport tr LEFT JOIN FETCH tr.bike WHERE tr.id = :id")
    Optional<TestReport> findBikeByID(@Param("id") String id);

    @Query("SELECT tr FROM TestReport tr LEFT JOIN FETCH tr.bike JOIN FETCH tr.reportEntries tre")
    List<TestReport> findAllWithBikeAndReportEntries();

    @Query("""
                SELECT tr FROM TestReport tr 
                JOIN tr.bike b 
                JOIN BikeOwnerBike bob ON bob.bike = b 
                JOIN FETCH tr.reportEntries tre 
                WHERE bob.bikeOwner.id = :ownerId
            """)
    List<TestReport> findAllByBikeOwnerId(@Param("ownerId") Integer ownerId);

    @Query("""
                SELECT DISTINCT tr FROM TestReport tr 
                JOIN tr.bike b 
                JOIN BikeOwnerBike bob ON bob.bike = b 
                JOIN bob.bikeOwner owner 
                JOIN FETCH tr.reportEntries tre 
                WHERE owner.company.id = :companyId
            """)
    List<TestReport> findAllByOwnerCompanyId(@Param("companyId") Integer companyId);

    @Query("""
               SELECT tr FROM TestReport tr
                       JOIN FETCH tr.bike b
                       JOIN FETCH tr.reportEntries tre
                       WHERE b.bikeQR = :bikeQr
            """)
    List<TestReport> findTestReportsByBikeQrWithBikeAndEntries(@Param("bikeQr") String bikeQr);

    @Override
    long count();

    @Query("SELECT COUNT(t) FROM TestReport t WHERE LOWER(t.state) = LOWER(:state)")
    long countByStateIgnoreCase(@Param("state") String state);
}