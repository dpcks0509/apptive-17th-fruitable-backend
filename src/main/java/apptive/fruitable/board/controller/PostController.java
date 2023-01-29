package apptive.fruitable.board.controller;

import apptive.fruitable.board.dto.post.PostDto;
import apptive.fruitable.board.dto.post.PostRequestDto;
import apptive.fruitable.board.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "post controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    /**
     * postDtoList를 "board/list"에 postList로 전달
     */
    @Operation(
            summary = "post list api",
            description = "전체 게시글 리스트 가져오기"
    )
    @GetMapping("")
    public List<PostDto> list() {

        return postService.getPostList();
    }

    /**
     * Post로 받은 데이터를 데이터베이스에 추가
     * @return 원래 화면
     */
    @Operation(
            summary = "게시글 작성 api",
            description = "게시글 작성"
    )
    @PostMapping(value = "",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Long write(@Valid @RequestPart(value = "requestDto") PostRequestDto requestDto,
                      @RequestPart(value = "images") List<MultipartFile> photoFileList,
                      @RequestPart(value = "tags") List<String> contentList
                      ) throws Exception {

        return postService.savePost(requestDto, photoFileList, contentList);
    }

    /**
     * 각 게시글 클릭시 /post/1 과 같이 get 요청, 해당 아이디의 데이터가 view로 전달되도록 함
     * @param postId
     * @return postId 에 해당하는 postDto 객체 전체
     */
    @Operation(
            summary = "상세게시글 api",
            description = "상세 게시글 확인 가능"
    )
    @GetMapping("/{postId}")
    public PostDto detail(@PathVariable Long postId) {

        return postService.getPost(postId);
    }

    /**
     * 서버에 put 요청이 오면, 데이터베이스에 변경된 데이터를 저장함
     */
    @Operation(
            summary = "게시글 업데이트",
            description = "게시글 업데이트"
    )
    @PutMapping("/{postId}")
    @CrossOrigin
    public ResponseEntity<?> update(@PathVariable Long postId,
                                    @Valid @RequestPart(value = "requestDto") PostRequestDto requestDto,
                                    @RequestPart(value = "images") List<MultipartFile> photoFileList,
                                    @RequestPart(value = "tags") List<String> contentList) {

        if(postId == null) {
            return new ResponseEntity<>("게시글이 존재하는지 확인해주세요.", HttpStatus.BAD_REQUEST);
        }

        try {
            postService.update(postId, requestDto, photoFileList, contentList);
            return new ResponseEntity<>("업데이트에 성공했습니다.", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("게시글 업데이트에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "delete api",
            description = "삭제 api"
    )
    @DeleteMapping("/{postId}")
    public void delete(@PathVariable("postId") Long postId) {

        postService.deletePost(postId);
    }
}
