package withdog.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import withdog.common.constant.ApiResponseCode;
import withdog.common.dto.response.ResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseDto> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(ApiResponseCode.SERVER_ERROR.getStatus()).body(ResponseDto.failure(ApiResponseCode.SERVER_ERROR.getCode(), e.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseDto> handleNoHandlerFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(ApiResponseCode.SERVER_ERROR.getStatus()).body(ResponseDto.failure(ApiResponseCode.NOT_FOUND.getCode(), ApiResponseCode.NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto> handleCustomException(CustomException e) {

        return ResponseEntity.status(e.getStatus()).body(ResponseDto.failure(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleValidationException(MethodArgumentNotValidException e) {

        List<String> ErrorMessageList = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.toList());

        String combinedMessage = String.join(", ", ErrorMessageList);

        return ResponseEntity.status(ApiResponseCode.CONTENTS_ERROR.getStatus())
        .body(ResponseDto.failure(ApiResponseCode.CONTENTS_ERROR.getCode(), combinedMessage));
    }

}
