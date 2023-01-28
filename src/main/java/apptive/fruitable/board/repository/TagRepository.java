package apptive.fruitable.board.repository;

import apptive.fruitable.board.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
