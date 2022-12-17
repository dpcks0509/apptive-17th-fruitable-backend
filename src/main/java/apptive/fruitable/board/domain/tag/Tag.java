package apptive.fruitable.board.domain.tag;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.board.dto.TagDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "tag")
@Getter
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column
    private String tagContent;

    public void updateTag(TagDto tagDto) {
        this.tagContent = tagDto.getTagContent();
    }
}
