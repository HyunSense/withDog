package withdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import withdog.entity.RefreshToken;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

//    @Query("select r from RefreshToken r join Member m where m.id = :id")
    @Query("select r from RefreshToken r where r.member.id = :id")
    Optional<RefreshToken> findByMemberId(Long id);
}
