package withdog.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import withdog.common.constant.TokenType;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

//    @Value("${spring.jwt.secret}")
    @Value("${spring.bearer.jwt}")
    String secretKey;

    @Test
    @DisplayName("토큰 만료시간 가져오기")
    void getExpired() {
        //given
        String token = jwtTokenProvider.createToken(1L, "test", "ROLE_USER", TokenType.ACCESS);

        //when
        long expired = jwtTokenProvider.getExpired(token);

        //then
        System.out.println("expired = " + expired);
    }

}