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
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Component
public class PostDto {

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

    private List<PhotoDto> photoDtoList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    /*@Builder
    public PostDto(String userId, String contact, Integer vege, String title, String content, Integer price, LocalDateTime endDate) {
        this.userId = userId;
        this.contact = contact;
        this.vege = vege;
        this.title = title;
        this.content = content;
        this.price = price;
        this.endDate = endDate;
    }*/

    public Post createPost() {
        return modelMapper.map(this, Post.class);
    }

    public static PostDto of(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

}
