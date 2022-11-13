package apptive.fruitable.board.service;

import apptive.fruitable.board.domain.post.Photo;
import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.board.dto.PhotoDto;
import apptive.fruitable.board.dto.PostDto;
import apptive.fruitable.board.repository.PhotoRepository;
import apptive.fruitable.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final PhotoService photoService;

    /**
     * 글쓰기 Form에서 내용을 입력한 뒤, '글쓰기' 버튼을 누르면 Post 형식으로 요청이 오고,
     * PostService의 savePost()를 실행하게 된다.
     */
    @Transactional
    public Long savePost(PostDto postDto, List<MultipartFile> files) throws Exception {

        //System.out.println(postDto.getTitle());
        //상품 등록
        Post post = new Post();
        post.updatePost(postDto);
        //System.out.println(post.getTitle());
        postRepository.save(post);

        //이미지 등록
        for(int i=0; i<files.size(); i++) {

            Photo photo = new Photo();
            photo.setPost(post);

            if(i==0) photo.setRepImg("Y");
            else photo.setRepImg("N");

            photoService.saveFile(photo, files.get(i));
        }

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

        List<Photo> photoList = photoRepository.findAllById(Collections.singleton(id));
        List<PhotoDto> photoDtoList = new ArrayList<>();

        for(Photo photo : photoList) {
            PhotoDto photoDto = PhotoDto.of(photo);
            photoDtoList.add(photoDto);
        }

        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        PostDto postDto = PostDto.of(post);
        postDto.setPhotoDtoList(photoDtoList);

        return postDto;
    }

    @Transactional
    public void deletePost(Long id) {

        Post post = postRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        postRepository.delete(post);
    }

    @Transactional
    public Long update(
            Long id,
            PostDto postDto,
            List<MultipartFile> files
    ) throws Exception {

        //상품 수정
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        post.updatePost(postDto);

        //이미지 등록
        for(int i=0; i<files.size(); i++) {
            photoService.updatePhoto(id, files.get(i));
        }

        return post.getId();
    }
}
