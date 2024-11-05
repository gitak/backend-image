package crowdworks.image.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PresignedUrlRequest {
    @NotBlank(message = "File extension is required")
    private String fileExtension;

    @NotBlank(message = "Content type is required")
    private String contentType;

    @NotBlank(message = "Path is required")
    private String path;
}

