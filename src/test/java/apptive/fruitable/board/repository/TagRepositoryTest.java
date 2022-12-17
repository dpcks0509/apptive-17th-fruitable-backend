package apptive.fruitable.board.repository;

import apptive.fruitable.board.domain.tag.Tag;
import apptive.fruitable.board.dto.TagDto;
import apptive.fruitable.board.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagService tagService;

    @Test
    public void 태그저장() throws Exception {

        TagDto tagDto = new TagDto();
        tagDto.setTagContent("hello world");

        Tag tag = new Tag();
        tag.updateTag(tagDto);

        tagRepository.save(tag);

        Tag tag1 = new Tag();
        tag1 = tagRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

        System.out.println(tag1.getTagContent());
    }
}
