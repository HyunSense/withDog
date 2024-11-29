package withdog.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.common.TokenType;
import withdog.dto.TokenDto;
import withdog.dto.request.SignUpRequestDto;
import withdog.dto.response.DataResponseDto;
import withdog.dto.response.ResponseDto;
import withdog.dto.response.ResponseMemberInfoDto;
import withdog.entity.Member;
import withdog.exception.CustomException;
import withdog.jwt.JwtTokenProvider;
import withdog.repository.MemberRepository;
import withdog.repository.TokenRepository;

import static withdog.common.ApiResponseCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseDto save(SignUpRequestDto signUpRequestDto) {

        if (memberRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new CustomException(DUPLICATE_USERNAME);
        }

        if (memberRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new CustomException(DUPLICATE_EMAIL);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequestDto.getPassword());
        Member member = signUpRequestDto.toEntity(encodedPassword);

        memberRepository.save(member);

        return ResponseDto.success();
    }

    @Override
    public DataResponseDto<ResponseMemberInfoDto> findMemberByToken(String token) {

        Long id = jwtTokenProvider.getId(token);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));

        ResponseMemberInfoDto dto = ResponseMemberInfoDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .role(member.getRole())
                .build();

        return DataResponseDto.success(dto);
    }

    @Override
    public DataResponseDto<TokenDto> refreshAccessToken(String refreshToken) {

//        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
//            throw new CustomException(TOKEN_INVALID);
//        }
//
//        String token = refreshToken.substring("Bearer ".length());

        try {
            jwtTokenProvider.tokenValidation(refreshToken, TokenType.REFRESH);

        } catch (JWTVerificationException e) {
            log.error(e.getMessage());
            throw new CustomException(e instanceof TokenExpiredException ? TOKEN_EXPIRED : TOKEN_INVALID);
        }

//        String tokenType = jwtTokenProvider.getTokenType(refreshToken);
//        if (!tokenType.equals(TokenType.REFRESH.name())) {
//            throw new CustomException(TOKEN_INVALID);
//        }

        Long id = jwtTokenProvider.getId(refreshToken);
        String username = jwtTokenProvider.getUsername(refreshToken);
        String role = jwtTokenProvider.getRole(refreshToken);

        String newAccessToken = jwtTokenProvider.createToken(id, username, role, TokenType.ACCESS);
        String expired = jwtTokenProvider.getExpired(newAccessToken);
        TokenDto tokenDto = TokenDto.builder()
                .token(newAccessToken)
                .expiryDate(expired)
                .build();

        return DataResponseDto.success(tokenDto);
    }

}
