package apptive.fruitable.board.dto;

import apptive.fruitable.board.domain.hashtag.Tag;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
public class TagDto {

    private Long tagId;
    private String tagContent;

    private static ModelMapper modelMapper = new ModelMapper();

    public Tag createPost() {
        return modelMapper.map(this, Tag.class);
    }

    public static TagDto of(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}
