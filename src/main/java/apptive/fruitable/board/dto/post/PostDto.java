package apptive.fruitable.board.dto.post;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.board.domain.tag.Tag;
import apptive.fruitable.login.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Component
public class PostDto {

    private Long id;

    private MemberEntity userId;
    private List<String> tagList;

    @NotBlank(message = "연락처를 입력해 주세요")
    private String contact;
    @NotNull(message = "과일/채소 여부를 알려주세요")
    private Integer vege;
    @NotBlank(message = "제목을 입력해 주세요")
    private String title;
    @NotBlank(message = "내용을 입력해 주세요")
    private String content;
    @NotNull(message = "가격을 입력해주세요")
    private Integer price;
    private LocalDate endDate;

    private List<String> filePath = new ArrayList<>();
    private List<String> fileURL = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Post createPost() {
        return modelMapper.map(this, Post.class);
    }

    public static PostDto of(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

}
