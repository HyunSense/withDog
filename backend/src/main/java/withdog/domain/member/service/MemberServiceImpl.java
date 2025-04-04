package withdog.domain.member.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.common.constant.TokenType;
import withdog.common.dto.TokenDto;
import withdog.domain.member.dto.request.SignUpRequestDto;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.domain.member.dto.response.ResponseMemberInfoDto;
import withdog.domain.member.entity.Member;
import withdog.common.exception.CustomException;
import withdog.common.filter.jwt.JwtTokenProvider;
import withdog.domain.member.repository.MemberRepository;

import static withdog.common.constant.ApiResponseCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseDto save(SignUpRequestDto signUpRequestDto) {

        if (memberRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new CustomException(DUPLICATE_USERNAME);
        }

        if (signUpRequestDto.getEmail() != null && !signUpRequestDto.getEmail().isEmpty() &&
                memberRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new CustomException(DUPLICATE_EMAIL);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequestDto.getPassword());
        Member member = signUpRequestDto.toEntity(encodedPassword);

        memberRepository.save(member);

        return ResponseDto.success();
    }

    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<ResponseMemberInfoDto> findMemberByToken(String token) {

        Long id = jwtTokenProvider.getId(token);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));

        ResponseMemberInfoDto dto = ResponseMemberInfoDto.builder()
                .username(member.getUsername())
                .role(member.getRole())
                .build();

        return DataResponseDto.success(dto);
    }

    @Override
    public DataResponseDto<TokenDto> refreshAccessToken(String refreshToken) {

        try {
            jwtTokenProvider.tokenValidation(refreshToken, TokenType.REFRESH);

        } catch (JWTVerificationException e) {
            log.error(e.getMessage());
            throw new CustomException(e instanceof TokenExpiredException ? REFRESH_TOKEN_EXPIRED : TOKEN_INVALID);
        }

        Long id = jwtTokenProvider.getId(refreshToken);
        String username = jwtTokenProvider.getUsername(refreshToken);
        String role = jwtTokenProvider.getRole(refreshToken);

        String newAccessToken = jwtTokenProvider.createToken(id, username, role, TokenType.ACCESS);
//        String expired = jwtTokenProvider.getExpired(newAccessToken);
        long expired = jwtTokenProvider.getExpired(newAccessToken);
        TokenDto tokenDto = TokenDto.builder()
                .token(newAccessToken)
                .expired(expired)
                .build();

        return DataResponseDto.success(tokenDto);
    }
}
