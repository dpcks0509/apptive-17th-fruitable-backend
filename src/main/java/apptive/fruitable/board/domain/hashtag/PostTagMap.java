package apptive.fruitable.board.domain.hashtag;

import apptive.fruitable.board.domain.post.Post;
import lombok.Getter;

import javax.persistence.*;

/*@Entity
@Getter
@Table(name = "post_tag_map")
public class PostTagMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_tag_map_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
*/