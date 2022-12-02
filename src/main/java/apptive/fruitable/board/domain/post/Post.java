package apptive.fruitable.board.domain.post;

import apptive.fruitable.board.dto.PostDto;
import apptive.fruitable.login.entity.MemberEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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

    @OneToMany(
            mappedBy = "post",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Photo> photo = new ArrayList<>();

    /*@Builder
    public Post(String userId, String contact, Integer vege, String title, String content, Integer price, LocalDateTime endDate, Long fileId) {
        this.userId = userId;
        this.contact = contact;
        this.vege = vege;
        this.title = title;
        this.content = content;
        this.price = price;
        this.endDate = endDate;
    }*/

    public void updatePost(PostDto postDto) {
        this.userId = postDto.getUserId();
        this.contact = postDto.getContact();
        this.vege = postDto.getVege();
        this.title = postDto.getTitle();
        this.content =  postDto.getContent();
        this.price = postDto.getPrice();
        this.endDate = postDto.getEndDate();
    }

    //Post에서 파일 처리 위함
    public void addPhoto(Photo photo) {
        this.photo.add(photo);

        //게시글에 파일이 저장되어 있지 않은 경우
        if(photo.getPost() != this)
            //파일 저장
            photo.setPost(this);
    }
}
