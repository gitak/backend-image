package crowdworks.image.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    // Common
    SUCCESS(200, "S000", "Success"),

    // Image
    IMAGE_UPLOAD_URL_GENERATED(200, "S001", "Pre-signed URL generated successfully"),
    IMAGE_UPLOADED(200, "S002", "Image uploaded successfully"),
    IMAGE_DELETED(200, "S003", "Image deleted successfully");

    private final int status;
    private final String code;
    private final String message;
}
