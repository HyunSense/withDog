package withdog.domain.member.service;

import withdog.common.dto.RefreshTokenDto;
import withdog.domain.member.dto.request.SignUpRequestDto;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.domain.member.dto.response.ResponseMemberInfoDto;

public interface MemberService {

    ResponseDto save(SignUpRequestDto signUpRequestDto);

    DataResponseDto<ResponseMemberInfoDto> findMemberByToken(String token);

    DataResponseDto<RefreshTokenDto> refreshAccessToken(String clientRefreshToken);

}
