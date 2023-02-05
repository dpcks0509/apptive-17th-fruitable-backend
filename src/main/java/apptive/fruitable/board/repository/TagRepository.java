package apptive.fruitable.board.repository;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.board.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    void deleteAllByPost(Post post);
    List<Tag> findAllByTagContent(String tagContent);
}
