package apptive.fruitable.board.dto;

import apptive.fruitable.board.domain.tag.Tag;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class TagDto {

    private Long id;
    private String tagContent;

    private static ModelMapper modelMapper = new ModelMapper();

    public static TagDto of(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}
