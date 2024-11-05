package crowdworks.image.exception;

import crowdworks.image.common.ErrorCode;

public class ImageNotFoundException extends BusinessException {
    public ImageNotFoundException() {
        super(ErrorCode.IMAGE_NOT_FOUND);
    }

    public ImageNotFoundException(String message) {
        super(ErrorCode.IMAGE_NOT_FOUND, message);
    }

    public ImageNotFoundException(String message, Throwable cause) {
        super(ErrorCode.IMAGE_NOT_FOUND, message, cause);
    }
}
