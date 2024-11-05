package crowdworks.image.dto.response;

import crowdworks.image.domain.Image;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ImageResponse {
    private Long id;
    private String originalFileName;
    private String storedFileName;
    private String url;
    private String contentType;
    private long fileSize;
    private LocalDateTime uploadedAt;
    private String uploadPath;

    public static ImageResponse from(Image image) {
        return ImageResponse.builder()
                .id(image.getId())
                .originalFileName(image.getOriginalFileName())
                .storedFileName(image.getStoredFileName())
                .url(image.getS3Url())
                .contentType(image.getContentType())
                .fileSize(image.getFileSize())
                .uploadedAt(image.getUploadedAt())
                .uploadPath(image.getUploadPath())
                .build();
    }
}
