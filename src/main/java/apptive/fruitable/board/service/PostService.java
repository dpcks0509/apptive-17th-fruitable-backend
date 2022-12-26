package apptive.fruitable.board.service;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.board.dto.PostDto;
import apptive.fruitable.board.dto.PostRequestDto;
import apptive.fruitable.board.handler.S3Uploader;
import apptive.fruitable.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final TagService tagService;
    private final S3Uploader s3Uploader;

    /**
     * 글쓰기 Form에서 내용을 입력한 뒤, '글쓰기' 버튼을 누르면 Post 형식으로 요청이 오고,
     * PostService의 savePost()를 실행하게 된다.
     */
    @Transactional
    public Long savePost(PostRequestDto requestDto,
                         List<String> tags,
                         List<MultipartFile> files) throws Exception {

        //System.out.println(postDto.getTitle());
        //상품 등록
        Post post = new Post();
        //System.out.println(post.getTitle());
        PostDto postDto = new PostDto();

        postDto.setId(requestDto.getId());
        postDto.setUserId(requestDto.getUserId());
        postDto.setContact(requestDto.getContact());
        postDto.setVege(requestDto.getVege());
        postDto.setTitle(requestDto.getTitle());
        postDto.setTitle(requestDto.getTitle());
        postDto.setPrice(requestDto.getPrice());
        postDto.setEndDate(requestDto.getEndDate());

        //이미지 등록
        List<String> filePath = s3Uploader.uploadFiles(files);
        postDto.setFilePath(filePath);

        post.updatePost(postDto);

        postRepository.save(post);

        //태그 저장
        tagService.saveTag(tags);

        return post.getId();
    }

    /**
     * 게시글 목록 가져오기
     */
    public List<PostDto> getPostList() {
        List<Post> postList = postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();

        for(Post post : postList) {

            PostDto postDto = PostDto.of(post);

            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    /**
     * 게시글 클릭시 상세게시글의 내용 확인
     * @param id
     * @return 해당 게시글의 데이터만 가져와 화면에 뿌려줌
     */
    @Transactional(readOnly = true)
    public PostDto getPost(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        PostDto postDto = PostDto.of(post);

        return postDto;
    }

    @Transactional
    public void deletePost(Long id) {

        Post post = postRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        if(post.getFilePath() != null) {
            List<String> filePath = post.getFilePath();

            for(String path : filePath) {

                s3Uploader.deleteFile(path);
            }
        }
        postRepository.delete(post);
    }

    @Transactional
    public Long update(
            Long id,
            PostDto postDto,
            List<String> tags,
            List<MultipartFile> files
    ) throws IOException {

        //상품 수정
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        //이미지 등록
        if(post.getFilePath() != null) {
            List<String> filePath = post.getFilePath();

            for(String path : filePath) {

                s3Uploader.deleteFile(path);
            }
        }
        List<String> filePath = s3Uploader.uploadFiles(files);
        postDto.setFilePath(filePath);
        post.updatePost(postDto);

        //태그 등록
        tagService.saveTag(tags);

        return post.getId();
    }
}
