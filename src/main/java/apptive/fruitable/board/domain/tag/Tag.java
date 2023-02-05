package apptive.fruitable.board.domain.tag;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.board.dto.tag.TagDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "tag")
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @ManyToOne @Getter
    private Post post;

    @Getter
    private String tagContent;

    public void makeTag(TagDto tagDto) {

        this.post = tagDto.getPost();
        this.tagContent = tagDto.getTagContent();
    }
}
