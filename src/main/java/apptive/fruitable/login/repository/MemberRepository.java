package apptive.fruitable.login.repository;

import apptive.fruitable.login.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findById(Long id);

    Optional<MemberEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    @Transactional
    void deleteMemberEntityByEmail(String email) throws Exception;
}
