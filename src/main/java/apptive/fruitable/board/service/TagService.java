package apptive.fruitable.board.service;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.board.domain.tag.Tag;
import apptive.fruitable.board.dto.post.PostDto;
import apptive.fruitable.board.dto.tag.TagDto;
import apptive.fruitable.board.exception.NoTagsException;
import apptive.fruitable.board.repository.PostRepository;
import apptive.fruitable.board.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {

    private final TagRepository tagRepository;
    private final PostRepository postRepository;

    @Transactional
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

    @Transactional
    public void deleteTag(Post post) {

        tagRepository.deleteAllByPost(post);
    }

    public List<PostDto> findTags(String tagContent) {

        try {

            List<Tag> tagList = tagRepository.findAllByTagContent(tagContent);
            List<PostDto> postDtoList = new ArrayList<>();

            for (Tag tag : tagList) {

                Long postId = tag.getPost().getId();

                PostDto postDto = PostDto.of(postRepository.findById(postId)
                        .orElseThrow(EntityNotFoundException::new));
                postDtoList.add(postDto);
            }

            return postDtoList;

        } catch (NoTagsException e) {
            throw new NoTagsException(tagContent);
        }
    }
}
