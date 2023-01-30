package apptive.fruitable.board.domain.post;

import apptive.fruitable.board.domain.tag.Tag;
import apptive.fruitable.board.dto.post.PostRequestDto;
import apptive.fruitable.converter.StringListConverter;
import apptive.fruitable.login.entity.MemberEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
@EntityListeners(AutoCloseable.class)
@Table(name = "post")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    //@JoinColumn(name = "member_id")
    private MemberEntity userId;

    @Convert(converter = StringListConverter.class)
    private List<String> tagList;

    @Column(nullable = false, length = 20)
    private String contact;

    //게시글 필요 정보
    @Column(nullable = false)
    private Integer vege; //0 - 과일, 1 - 채소
    @Column(length = 20, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(nullable = false)
    private Integer price;
    private LocalDate endDate;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> filePath;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> fileURL;

    public void updatePost(PostRequestDto postDto) {
        this.userId = postDto.getUserId();
        this.contact = postDto.getContact();
        this.vege = postDto.getVege();
        this.title = postDto.getTitle();
        this.content =  postDto.getContent();
        this.price = postDto.getPrice();
        this.endDate = postDto.getEndDate();
    }
}
