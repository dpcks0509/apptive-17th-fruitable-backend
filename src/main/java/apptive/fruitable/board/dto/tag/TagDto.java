package apptive.fruitable.board.dto.tag;

import apptive.fruitable.board.domain.post.Post;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TagDto {

    private Post post;
    private String tagContent;
}
