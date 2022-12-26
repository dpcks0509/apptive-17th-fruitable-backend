package apptive.fruitable.board.domain.post;

import apptive.fruitable.board.domain.tag.Tag;
import apptive.fruitable.board.dto.PostDto;
import apptive.fruitable.converter.StringListConverter;
import apptive.fruitable.login.entity.MemberEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AutoCloseable.class)
@Table(name = "post")
public class Post {

    @Id @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //회원 정보 (Userid - 외래키(@Column), contact - 직접 받아옴)
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    //@JoinColumn(name = "member_id")
    private MemberEntity userId;
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
    private LocalDateTime endDate;
    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> filePath;

    @OneToMany(
            mappedBy = "post",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Tag> tags = new ArrayList<>();

    public void updatePost(PostDto postDto) {
        this.userId = postDto.getUserId();
        this.contact = postDto.getContact();
        this.vege = postDto.getVege();
        this.title = postDto.getTitle();
        this.content =  postDto.getContent();
        this.price = postDto.getPrice();
        this.endDate = postDto.getEndDate();
        this.filePath = postDto.getFilePath();
    }
}
