package apptive.fruitable.board.dto;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.login.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Component
public class PostRequestDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private MemberEntity userId;

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
    private LocalDateTime endDate;

    private static ModelMapper modelMapper = new ModelMapper();

    public Post createPost() {
        return modelMapper.map(this, Post.class);
    }

    public static PostRequestDto of(Post post) {
        return modelMapper.map(post, PostRequestDto.class);
    }

}

