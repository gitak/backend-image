package crowdworks.image.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "C001", "Invalid input value"),
    METHOD_NOT_ALLOWED(405, "C002", "Invalid method"),
    ENTITY_NOT_FOUND(404, "C003", "Entity not found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server error"),

    // Image
    INVALID_FILE_TYPE(400, "I001", "Invalid file type"),
    FILE_SIZE_EXCEED(400, "I002", "File size exceeds limit"),
    IMAGE_NOT_FOUND(404, "I003", "Image not found"),
    S3_UPLOAD_ERROR(500, "I004", "Failed to generate pre-signed URL"),
    S3_DELETE_ERROR(404,"I005" ,"Failed to delete image from S3"),

    ;



    private final int status;
    private final String code;
    private final String message;
}
