package apptive.fruitable.board.controller;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.board.dto.PostDto;
import apptive.fruitable.board.service.PhotoService;
import apptive.fruitable.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final PhotoService photoService;

    /**
     * postDtoList를 "board/list"에 postList로 전달
     */
    @GetMapping("")
    public List<PostDto> list() {

        return postService.getPostList();
    }

    /**
     * 글쓰는 페이지로 이동
     */
    /*@GetMapping("/{postId}")
    public PostDto getPostByUserId(@PathVariable Long postId) {

        return postService.getPost(postId);
    }*/

    /**
     * Post로 받은 데이터를 데이터베이스에 추가
     * @return 원래 화면
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Long write(@Valid PostDto postDto, Model model,
                      @RequestParam("photoFile") List<MultipartFile> photoFileList) throws Exception {

        if(photoFileList.get(0).isEmpty() && postDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
        }

        return postService.savePost(postDto, photoFileList);
    }

    /**
     * 각 게시글 클릭시 /post/1 과 같이 get 요청, 해당 아이디의 데이터가 view로 전달되도록 함
     * @param postId
     * @return postId 에 해당하는 postDto 객체 전체
     */
    @GetMapping("/{postId}")
    public PostDto detail(@PathVariable Long postId) {

        return postService.getPost(postId);
    }

    /**
     * id에 해당하는 게시글을 수정할 수 있음
     * @param id
     * @param model
     * @return put 형식으로 /post/edit/{id}로 서버에 요청 감
     */
    /*@GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {

        PostDto postDto = postService.getPost(id);
        model.addAttribute("post", postDto);
        return "board/edit";
    }*/

    /**
     * 서버에 put 요청이 오면, 데이터베이스에 변경된 데이터를 저장함
     */
    @PutMapping("/{postId}")
    @CrossOrigin
    public void update(@PathVariable Long postId,
                         PostDto postDto,
                         BindingResult bindingResult,
                         @RequestParam("photoFile") List<MultipartFile> photoFileList,
                         Model model) throws Exception {

        if(bindingResult.hasErrors()) return;

        if(photoFileList.get(0).isEmpty() && postId == null) {
            model.addAttribute("errorMessage", "첫번재 상품 이미지는 필수 입력값 입니다.");
            return;
        }

        try {
            postService.update(postId, postDto, photoFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생했습니다.");
        }
    }

    @DeleteMapping("/{postId}")
    public void delete(@PathVariable("postId") Long postId) {

        postService.deletePost(postId);
    }
}
