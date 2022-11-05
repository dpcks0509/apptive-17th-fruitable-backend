package apptive.fruitable.board.dto;

import apptive.fruitable.board.domain.post.Photo;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class PhotoDto {

    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;
    private String repImg;

    private static ModelMapper modelMapper = new ModelMapper();

    public static PhotoDto of(Photo photo) {
        return modelMapper.map(photo, PhotoDto.class);
    }
}
