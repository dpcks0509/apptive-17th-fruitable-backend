package apptive.fruitable.board.service;

import apptive.fruitable.board.domain.post.Photo;
import apptive.fruitable.board.domain.post.QPhoto;
import apptive.fruitable.board.dto.PhotoDto;
import apptive.fruitable.board.dto.PhotoResponseDto;
import apptive.fruitable.board.repository.PhotoRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service @Component
@RequiredArgsConstructor
@Transactional
public class PhotoService {

    private static String fileLocation;

    @Value("${img.location}")
    public void setFileLocation(String path) {
        fileLocation = path;
    }

    private final PhotoRepository photoRepository;
    private final FileHandler fileHandler;

    @PersistenceContext
    private EntityManager em;

    /**
     * 이미지 저장
     */
    @Transactional
    public void saveFile(Photo photo, MultipartFile photoFile) throws Exception {

        String oriImgName = photoFile.getOriginalFilename();
        String imgName = "";
        String filePath = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)) {

            imgName = fileHandler.uploadFile(fileLocation, oriImgName, photoFile.getBytes());
            filePath = "/img/post/" + imgName;
        }

        //상품 이미지 정보 저장
        photo.updatePhoto(oriImgName, imgName, filePath);
        photoRepository.save(photo);
    }


    /**
     * 이미지 삭제
     */
    public void deletePhoto(String filepath) {

        //photoRepository.deleteById(id);
    }

    /**
     * 이미지 업데이트
     */
    public void updatePhoto(Long id, MultipartFile photoFile) throws Exception {

        if(!photoFile.isEmpty()) {
            Photo savedFile = photoRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedFile.getImgName())) {
                fileHandler.deleteFile(fileLocation + "/" + savedFile.getImgName());
            }

            String oriImgName = photoFile.getOriginalFilename();
            String fileName = fileHandler.uploadFile(fileLocation, oriImgName, photoFile.getBytes());
            String filePath = "/img/post/" + fileName;

            savedFile.updatePhoto(oriImgName, fileName, filePath);
        }
    }

    /**
     * 이미지 전체 조회
     */
    @Transactional(readOnly = true)
    public List<PhotoResponseDto> findAllByPost(Long id) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QPhoto photo = QPhoto.photo;

        List<Photo> photoList = queryFactory
                .selectFrom(photo)
                .where(photo.post.id.eq(id))
                .fetch();

        return photoList.stream()
                .map(PhotoResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 개별 이미지 조회
     */
    @Transactional(readOnly = true)
    public PhotoDto findByFileId(Long id) {

        Photo entity = photoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

        PhotoDto photoDto = new PhotoDto();

        return photoDto;
    }
}
