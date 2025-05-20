package integration4.evalebike.repository;

import integration4.evalebike.domain.User;
import integration4.evalebike.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    List<User> findByUserStatus(UserStatus userStatus);

    @Query("SELECT u FROM User u WHERE u.createdBy.id = :creatorId")
    List<User> findByCreatedById(@Param("creatorId") Integer creatorId);
}