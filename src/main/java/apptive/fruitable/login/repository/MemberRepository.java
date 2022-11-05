package apptive.fruitable.login.repository;

import apptive.fruitable.login.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByN(Long n);
    Optional<MemberEntity> findById(String Id);
    boolean existsById(String id);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    @Transactional
    void deleteMemberEntityById(String id) throws Exception;
}
