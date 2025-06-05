package withdog.common.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import withdog.common.constant.ApiResponseCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
