package withdog.domain.member.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withdog.common.config.auth.CustomUserDetails;
import withdog.common.constant.ApiResponseCode;
import withdog.common.constant.TokenType;
import withdog.common.dto.RefreshTokenDto;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.domain.member.dto.request.SignUpRequestDto;
import withdog.domain.member.dto.response.ResponseMemberInfoDto;
import withdog.domain.member.service.MemberService;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    /*[login, logout] SpringSecurity Filter에 위임*/
    @PostMapping("/members")
    public ResponseEntity<ResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {

        ResponseDto responseBody = memberService.save(signUpRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/members/info")
    public ResponseEntity<ResponseDto> getMemberInfo(@RequestParam String accessToken) {

        DataResponseDto<ResponseMemberInfoDto> responseBody = memberService.findMemberByToken(accessToken);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<ResponseDto> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {

        String clientRefreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TokenType.REFRESH.name())) {
                    clientRefreshToken = cookie.getValue();
                    break;
                }
            }
        }

        // 모든 검증 통과시, 토큰 재발급
        DataResponseDto<RefreshTokenDto> dataResponseDto = memberService.refreshAccessToken(clientRefreshToken);
        response.setHeader("Authorization", "Bearer " + dataResponseDto.getData().getToken());
        return ResponseEntity.status(HttpStatus.OK).body(dataResponseDto);
    }

}
