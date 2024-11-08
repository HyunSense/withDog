package withdog.exception;

import lombok.Getter;
import withdog.common.ApiResponseCode;

@Getter
public class CustomException extends RuntimeException {

    private final int status;
    private final String code;

    public CustomException(ApiResponseCode apiResponseCode) {
        super(apiResponseCode.getMessage());
        this.code = apiResponseCode.getCode();
        this.status = apiResponseCode.getStatus();
    }
}
