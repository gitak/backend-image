package crowdworks.image.dto.response;

import lombok.Builder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PresignedUrlResponse {
    private String presignedUrl;
    private String fileName;
    private String fullPath;
    private String originalFileName;
    private long fileSize;
    private String contentType;
}
