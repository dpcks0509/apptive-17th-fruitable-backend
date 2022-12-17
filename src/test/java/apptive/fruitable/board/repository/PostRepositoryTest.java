package apptive.fruitable.board.repository;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.board.dto.PostDto;
import apptive.fruitable.board.service.PostService;
import apptive.fruitable.login.dto.MemberDto;
import apptive.fruitable.login.entity.MemberEntity;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class PostRepositoryTest {

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

    /*PostDto createPost() throws Exception {

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(1L);

        PostDto postDto = new PostDto();
        //postDto.setUserId("abc");
        postDto.setContact("123-456");
        postDto.setVege(1);
        postDto.setTitle("채소 팝니다");
        postDto.setContent("abc");
        postDto.setPrice(123);
        postDto.setEndDate(LocalDateTime.now());

        return postDto;
    }

    @Test
    public void 상품조회() throws Exception {

        PostDto postDto = createPost();
        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long postId = postService.savePost(postDto, multipartFileList);
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        System.out.println(post.getTitle());
        System.out.println(post.getVege());
        System.out.println(post.getPrice());
    }

    @Test
    public void 상품업데이트() throws Exception {

        PostDto postDto = createPost();
        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long postId = postService.savePost(postDto, multipartFileList);
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(postDto.getPrice(), post.getPrice());
        assertEquals(postDto.getTitle(), post.getTitle());

        postDto.setTitle("바뀌었습니다.");
        postId = postService.update(postId, postDto, multipartFileList);

        Post post1 = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(postDto.getTitle(), post1.getTitle());
    }

    @Test
    public void 상품삭제() throws Exception {

        PostDto postDto = createPost();
        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long postId = postService.savePost(postDto, multipartFileList);
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        postService.deletePost(postId);
        assertEquals(1, post.getId());
    }*/
}
