package apptive.fruitable.board.dto;

import apptive.fruitable.board.domain.post.Photo;
import lombok.Getter;

@Getter
public class PhotoResponseDto {

    private Long fileId;

    public PhotoResponseDto(Photo entity) {
        this.fileId = entity.getId();
    }
}
