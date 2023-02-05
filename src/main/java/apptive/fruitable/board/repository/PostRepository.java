package apptive.fruitable.board.repository;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.login.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findByUserId(MemberEntity memberEntity);
    boolean existsByUserId(MemberEntity userId);
    List<Post> findAllByTagList(String tagContent);
}
