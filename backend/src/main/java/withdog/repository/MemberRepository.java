package withdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import withdog.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    //TODO: 쿼리성능 개선 체크
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
