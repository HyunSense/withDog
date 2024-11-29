package withdog.service;

import withdog.dto.TokenDto;
import withdog.dto.request.SignUpRequestDto;
import withdog.dto.response.DataResponseDto;
import withdog.dto.response.ResponseDto;
import withdog.dto.response.ResponseMemberInfoDto;

public interface MemberService {

    ResponseDto save(SignUpRequestDto signUpRequestDto);

    DataResponseDto<ResponseMemberInfoDto> findMemberByToken(String token);

    //TODO: 수정필요
    DataResponseDto<TokenDto> refreshAccessToken(String refreshToken);

}
