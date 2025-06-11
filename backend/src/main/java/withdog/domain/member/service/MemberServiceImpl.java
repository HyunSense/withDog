package withdog.domain.member.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.common.constant.ApiResponseCode;
import withdog.common.constant.TokenType;
import withdog.common.dto.RefreshTokenDto;
import withdog.domain.member.dto.request.SignUpRequestDto;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.domain.member.dto.response.ResponseMemberInfoDto;
import withdog.domain.member.entity.Member;
import withdog.common.exception.CustomException;
import withdog.common.jwt.JwtTokenProvider;
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
    private final RedisTemplate<String, String> redisTemplate;
    private final String REFRESH_TOKEN_KEY_PREFIX = "refreshToken:";


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
    public DataResponseDto<RefreshTokenDto> refreshAccessToken(String clientRefreshToken) {

        if (clientRefreshToken == null) {
            throw new CustomException(NOT_FOUND_TOKEN);
        }

        try {

            Long memberId = jwtTokenProvider.getId(clientRefreshToken);

            String serverRefreshToken = redisTemplate.opsForValue().get(REFRESH_TOKEN_KEY_PREFIX + memberId);
            if (serverRefreshToken == null || !serverRefreshToken.equals(clientRefreshToken)) {
                throw new CustomException(TOKEN_INVALID);
            }

            log.info("Refresh token is valid. Reissue new access token.");
            String username = jwtTokenProvider.getUsername(clientRefreshToken);
            String role = jwtTokenProvider.getRole(clientRefreshToken);

            String newAccessToken = jwtTokenProvider.createToken(memberId, username, role, TokenType.ACCESS);
            long expired = jwtTokenProvider.getExpired(newAccessToken);
            RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
                    .id(memberId)
                    .username(username)
                    .role(role)
                    .token(newAccessToken)
                    .expired(expired)
                    .build();

            return DataResponseDto.success(refreshTokenDto);

        } catch (TokenExpiredException e) {
            log.warn("Refresh token expired: {}", e.getMessage());

            Long idFromExpiredToken = jwtTokenProvider.getIdFromExpiredToken(clientRefreshToken);
            if (idFromExpiredToken != null) {
                redisTemplate.delete(REFRESH_TOKEN_KEY_PREFIX + idFromExpiredToken);
            }

            throw new CustomException(REFRESH_TOKEN_EXPIRED);

        } catch (JWTVerificationException e) {
            log.error("Refresh token validation failed: {}", e.getMessage());
            throw new CustomException(TOKEN_INVALID);
        }
    }
}
