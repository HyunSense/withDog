package withdog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.dto.request.SignUpRequestDto;
import withdog.dto.response.ResponseDto;
import withdog.entity.Member;
import withdog.exception.CustomException;
import withdog.jwt.JwtTokenProvider;
import withdog.repository.MemberRepository;

import static withdog.common.ApiResponseCode.*;

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

        if (memberRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new CustomException(DUPLICATE_EMAIL);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequestDto.getPassword());
        Member member = signUpRequestDto.toEntity(encodedPassword);

        memberRepository.save(member);

        return ResponseDto.success();
    }

    @Override
    public ResponseDto refreshAccessToken(String refreshToken) {

        jwtTokenProvider.tokenValidation(refreshToken);
        Long id = jwtTokenProvider.getId(refreshToken);



        return null;
    }
}
