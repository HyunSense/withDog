package withdog.exception;

import lombok.Getter;
import withdog.common.ApiResponseCode;

@Getter
public class PlaceException extends RuntimeException {

    private final String code;

    public PlaceException(ApiResponseCode apiResponseCode) {
        super(apiResponseCode.getMessage());
        this.code = apiResponseCode.getCode();
    }
}
