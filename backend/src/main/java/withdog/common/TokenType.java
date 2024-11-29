package withdog.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {

//    ACCESS(1000 * 5), //TEST용 5 sec
    ACCESS(1000 * 60 * 60), // 1 h
    REFRESH(1000 * 60 * 60 * 24 * 3); // 3 day

    private final int expiration;
}
