package apptive.fruitable.board.controller;

import apptive.fruitable.board.dto.post.PostDto;
import apptive.fruitable.board.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
@Slf4j
public class TagController {

    private final TagService tagService;

    @GetMapping("/{tagContent}")
    public List<PostDto> findTags(@PathVariable("tagContent") String tagContent) {

        return tagService.findTags(tagContent);
    }
}
