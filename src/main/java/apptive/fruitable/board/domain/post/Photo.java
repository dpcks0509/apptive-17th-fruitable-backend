package apptive.fruitable.board.domain.post;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Entity
@NoArgsConstructor
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    public String imgName;

    @Column(nullable = false)
    private String origFilename;

    @Column(nullable = false)
    private String repImg;

    @Column(nullable = false)
    private String filePath;

    public void updatePhoto(String origFilename, String imgName, String filePath) {
        this.origFilename = origFilename;
        this.imgName = imgName;
        this.filePath = filePath;
    }
}
