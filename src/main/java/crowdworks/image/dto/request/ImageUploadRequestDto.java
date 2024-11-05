package crowdworks.image.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageUploadRequestDto {
    private String fileName;
    private MultipartFile file;
}
