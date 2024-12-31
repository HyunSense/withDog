package withdog.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {

    ACCESS(1000 * 60 * 60), // 1 h
    REFRESH(1000 * 60 * 60 * 24); // 1 day (24h)
//    ACCESS(1000 * 5), //TEST용 5 sec
//    REFRESH(1000 * 15); // 2m 테스트용

    private final int expiration;
}
