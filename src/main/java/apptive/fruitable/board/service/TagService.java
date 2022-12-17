package apptive.fruitable.board.service;

import apptive.fruitable.board.domain.tag.Tag;
import apptive.fruitable.board.dto.TagDto;
import apptive.fruitable.board.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {

    private TagRepository tagRepository;

    @Transactional
    public void saveTag(List<String> tags) {

        for(String content : tags) {

            Tag tag = new Tag();
            TagDto tagDto = new TagDto();
            tagDto.setTagContent(content);

            tag.updateTag(tagDto);

            tagRepository.save(tag);
        }
    }
}
