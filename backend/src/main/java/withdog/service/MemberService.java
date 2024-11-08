package withdog.service;

import withdog.dto.request.SignUpRequestDto;
import withdog.dto.response.ResponseDto;

public interface MemberService {

    ResponseDto save(SignUpRequestDto signUpRequestDto);

    ResponseDto refreshAccessToken(String refreshToken);
}
