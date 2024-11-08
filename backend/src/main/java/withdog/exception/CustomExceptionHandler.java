package withdog.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import withdog.common.ApiResponseCode;
import withdog.dto.response.ResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

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
