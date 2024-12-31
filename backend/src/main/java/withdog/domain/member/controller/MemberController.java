package withdog.domain.member.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import withdog.common.constant.TokenType;
import withdog.common.dto.TokenDto;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.domain.member.dto.request.SignUpRequestDto;
import withdog.domain.member.dto.response.ResponseMemberInfoDto;
import withdog.domain.member.service.MemberService;

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

    //TODO: 리펙토링 필요 (프론트에서 사용 X)
    @GetMapping("/members/info")
    public ResponseEntity<ResponseDto> getMemberInfo(@RequestHeader(name = "Authorization") String authorization) {

        String token = authorization.substring("Bearer ".length());
        DataResponseDto<ResponseMemberInfoDto> responseBody = memberService.findMemberByToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<ResponseDto> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        log.info("Refresh access token");
        // refresh token 비교
        // 일치하면 access token 발급
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            log.info("cookies are not null");
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TokenType.REFRESH.name())) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        log.info("Refresh token is : {} ", refreshToken);
        DataResponseDto<TokenDto> dataResponseDto = memberService.refreshAccessToken(refreshToken);
        log.info("dataResponseDto = {}", dataResponseDto.getData());
        response.setHeader("Authorization", dataResponseDto.getData().getToken());
        return ResponseEntity.status(HttpStatus.OK).body(dataResponseDto);
    }

}
