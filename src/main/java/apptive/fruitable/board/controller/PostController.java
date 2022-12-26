package apptive.fruitable.board.controller;

import apptive.fruitable.board.dto.PostDto;
import apptive.fruitable.board.dto.PostRequestDto;
import apptive.fruitable.board.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "post controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
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
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Long write(@Valid PostRequestDto requestDto,
                      List<String> tags,
                      Model model,
                      @RequestParam("photoFile") List<MultipartFile> photoFileList) throws Exception {

        if(photoFileList.get(0).isEmpty() && requestDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
        }

        return postService.savePost(requestDto, tags, photoFileList);
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
    public void update(@PathVariable Long postId,
                         PostDto postDto,
                         List<String> tags,
                         BindingResult bindingResult,
                         @RequestParam("photoFile") List<MultipartFile> photoFileList,
                         Model model) {

        if(bindingResult.hasErrors()) return;

        if(photoFileList.get(0).isEmpty() && postId == null) {
            model.addAttribute("errorMessage", "첫번재 상품 이미지는 필수 입력값 입니다.");
            return;
        }

        try {
            postService.update(postId, postDto, tags, photoFileList);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생했습니다.");
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
