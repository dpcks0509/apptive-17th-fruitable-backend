package apptive.fruitable.controller;

import apptive.fruitable.dto.PostDto;
import apptive.fruitable.service.PhotoService;
import apptive.fruitable.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PhotoService photoService;

    /**
     * postDtoList를 "board/list"에 postList로 전달
     * @param model
     * @return
     */
    /*@GetMapping("/")
    public String list(Model model) {

        List<PostDto> postDtoList = postService.getPostList();
        model.addAttribute("postList", postDtoList);
        return "board/list";
    }*/

    /**
     * 글쓰는 페이지로 이동
     */
    @GetMapping("/post")
    public String post(Model model) {

        model.addAttribute("postDto", new PostDto());
        return "board/post";
    }

    /**
     * Post로 받은 데이터를 데이터베이스에 추가
     * @return 원래 화면
     */
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public String write(@Valid PostDto postDto, BindingResult bindingResult,
                      Model model, @RequestParam("photoFile") List<MultipartFile> photoFileList) throws Exception {

        if(bindingResult.hasErrors()) {
            return "board/post";
        }

        if(photoFileList.get(0).isEmpty() && postDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "board/post";
        }

        try {
            postService.savePost(postDto, photoFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "board/post";
        }

        return "redirect:/";
    }

    /**
     * 각 게시글 클릭시 /post/1 과 같이 get 요청, 해당 아이디의 데이터가 view로 전달되도록 함
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {

        try {
            PostDto postDto = postService.getPost(id);
            model.addAttribute("postDto", postDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("postDto", new PostDto());
            return "board/post";
        }

        return "board/detail";
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
    @PutMapping("/post/edit/{id}")
    @CrossOrigin
    public String update(@PathVariable Long id,
                         PostDto postDto,
                         BindingResult bindingResult,
                         @RequestParam("photoFile") List<MultipartFile> photoFileList,
                         Model model) throws Exception {

        if(bindingResult.hasErrors()) return "board/post";

        if(photoFileList.get(0).isEmpty() && id == null) {
            model.addAttribute("errorMessage", "첫번재 상품 이미지는 필수 입력값 입니다.");
            return "board/post";
        }

        try {
            postService.update(id, postDto, photoFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생했습니다.");
            return "board/post";
        }
        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {

        postService.deletePost(id);
        return "redirect:/";
    }
}
