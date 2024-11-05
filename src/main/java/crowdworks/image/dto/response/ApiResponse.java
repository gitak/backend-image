package crowdworks.image.dto.response;

import crowdworks.image.common.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(ResultCode resultCode, T data) {
        return new ApiResponse<>(
                resultCode.getStatus(),
                resultCode.getCode(),
                resultCode.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> error(ErrorResponse errorResponse) {
        return new ApiResponse<>(
                errorResponse.getStatus(),
                errorResponse.getCode(),
                errorResponse.getMessage(),
                null
        );
    }
}

