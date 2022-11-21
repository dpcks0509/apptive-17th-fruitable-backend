package apptive.fruitable.board.service;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.board.dto.PostDto;
import apptive.fruitable.board.repository.PhotoRepository;
import apptive.fruitable.board.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PhotoRepository photoRepository;

    List<MultipartFile> createMultipartFiles() throws Exception {

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0; i<5; i++) {

            String path = "/Users/hjhwang/Projects/Spring/fruitable/img";
            String imgName = "image" + i + ".jpg";

            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, imgName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    void createUser() throws Exception {

        return;
    }

    @Test
    public void 상품등록() throws Exception {

        /*MemberDto member = new MemberDto("12345",
                "pwd12345", "pwd12345", "name",
                "email@naver.com", "phone");

        memberService.save(member);
        MemberEntity memberEntity = toSaveEntity(member);*/

        PostDto postDto = new PostDto();
        postDto.setUserId("abc");
        postDto.setContact("123-456");
        postDto.setVege(1);
        postDto.setTitle("채소 팝니다");
        postDto.setContent("abc");
        postDto.setPrice(123);
        postDto.setEndDate(LocalDateTime.now());

        List<MultipartFile> multipartFileList = createMultipartFiles();
        //System.out.println(postDto.getTitle());
        Long postId = postService.savePost(postDto, multipartFileList);

        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(postDto.getPrice(), post.getPrice());
        assertEquals(postDto.getTitle(), post.getTitle());
    }
}
