package withdog.common.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.common.config.auth.provider.GoogleUserInfo;
import withdog.common.config.auth.provider.KakaoUserInfo;
import withdog.common.config.auth.provider.Oauth2UserInfo;
import withdog.domain.member.entity.Member;
import withdog.domain.member.repository.MemberRepository;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Oauth2UserInfo oauth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            log.info("registrationId: google");
            oauth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            log.info("registrationId: kakao");
            oauth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());

        } else {
            log.info("This provider is not supported.");
        }

        String provider = oauth2UserInfo.getProvider();
        String providerId = oauth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String password = passwordEncoder.encode(UUID.randomUUID().toString());
        String name = oauth2UserInfo.getName();
        String email = oauth2UserInfo.getEmail();

        log.info("provider: {}, providerId: {}, username: {}, email: {}", provider, providerId, username, email);

        Member member = memberRepository.findByUsername(username)
                .orElseGet(() -> {
                            Member newMember = Member.builder()
                                    .username(username)
                                    .password(password)
                                    .name(name)
                                    .email(email)
                                    .role("ROLE_USER")
                                    .build();
                            return memberRepository.save(newMember);
                        }
                );

        return new CustomUserDetails(member, oAuth2User.getAttributes());
//        return super.loadUser(userRequest);
    }
}
