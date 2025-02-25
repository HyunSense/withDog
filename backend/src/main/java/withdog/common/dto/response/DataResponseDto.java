package withdog.common.dto.response;

import lombok.Getter;
import withdog.common.constant.ApiResponseCode;

@Getter
public class DataResponseDto<T> extends ResponseDto{

    T data;

    private DataResponseDto(String code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public static <T> DataResponseDto<T> success(T data) {

        return new DataResponseDto<>(ApiResponseCode.SUCCESS.getCode(), ApiResponseCode.SUCCESS.getMessage(), data);
    }

    public static <T> DataResponseDto<T> failure(String code, String message, T data) {
        return new DataResponseDto<>(code, message, data);
    }
}
