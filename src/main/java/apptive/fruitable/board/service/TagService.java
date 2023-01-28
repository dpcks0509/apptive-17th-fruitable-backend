package apptive.fruitable.board.service;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.board.domain.tag.Tag;
import apptive.fruitable.board.dto.tag.TagDto;
import apptive.fruitable.board.repository.PostRepository;
import apptive.fruitable.board.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {

    private final TagRepository tagRepository;

    public List<String> saveTag(Post post, List<String> contentList) {

        List<String> tagList = new ArrayList<>();

        for(String content : contentList) {

            TagDto tagDto = new TagDto();
            tagDto.setPost(post);
            tagDto.setTagContent(content);

            Tag tag = new Tag();
            tag.makeTag(tagDto);

            tagList.add(tag.getTagContent());

            tagRepository.save(tag);
        }

        return tagList;
    }
}
