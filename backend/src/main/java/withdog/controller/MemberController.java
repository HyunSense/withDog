package withdog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import withdog.dto.response.ResponseDto;
import withdog.dto.request.SignUpRequestDto;
import withdog.jwt.JwtTokenProvider;
import withdog.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {

        ResponseDto responseBody = memberService.save(signUpRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

//    @PostMapping("/refresh-token")
//    public ResponseEntity<ResponseDto> refreshAccessToken(String accessToken) {
//
//        jwtTokenProvider.
//
//
//    }

}
