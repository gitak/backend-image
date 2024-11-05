package crowdworks.image.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageFile {
    private String fileName;
    private MultipartFile file;

    // 파일 크기 검증 (5MB)
    public boolean isValidSize() {
        return file != null && file.getSize() <= 5 * 1024 * 1024; // 5MB in bytes
    }

    // 이미지 파일 타입 검증
    public boolean isImageFile() {
        return file != null && file.getContentType() != null &&
                file.getContentType().startsWith("image/");
    }
}
