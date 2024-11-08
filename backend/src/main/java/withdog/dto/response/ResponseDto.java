package withdog.dto.response;

import lombok.Getter;
import withdog.common.ApiResponseCode;

@Getter
public class ResponseDto {

    private String code;
    private String message;


    protected ResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseDto success() {
        return new ResponseDto(ApiResponseCode.SUCCESS.getCode(), ApiResponseCode.SUCCESS.getMessage());
    }

    public static ResponseDto failure(String code, String message) {
        return new ResponseDto(code, message);
    }
}
