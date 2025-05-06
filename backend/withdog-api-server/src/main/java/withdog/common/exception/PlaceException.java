package withdog.common.exception;

import lombok.Getter;
import withdog.common.constant.ApiResponseCode;

@Getter
public class PlaceException extends RuntimeException {

    private final String code;

    public PlaceException(ApiResponseCode apiResponseCode) {
        super(apiResponseCode.getMessage());
        this.code = apiResponseCode.getCode();
    }
}
