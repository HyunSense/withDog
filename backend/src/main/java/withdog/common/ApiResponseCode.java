package withdog.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode {

    // 200
    SUCCESS("SU", "Success", 200),

    // 400
    DUPLICATE_USERNAME("DUP", "Duplicate Username", 400),
    DUPLICATE_EMAIL("DUPE", "Duplicate Email", 400),
    NOT_EXIST_MEMBER("NME", "Not Exist Member", 400),
    NOT_EXIST_PLACE("NP", "Not Exist Place", 400),
    NOT_EXIST_CATEGORY("NC", "Not Exist Category", 400),
    NOT_EXIST_IMAGE("NI", "Not Exist Image", 400),
    CONTENTS_ERROR("CE", "Contents Error", 400),
    NOT_FOUND_MEMBER("NFM", "Not Found Member", 400),
    NOT_FOUND_TOKEN("NT", "Not Found Token", 400),

    // 401
    LOGIN_FAILED("LF", "Login Failed", 401),
    TOKEN_EXPIRED("TE", "Token Expired", 401),
    TOKEN_INVALID("TI", "Token Invalid", 401),

    // 403
    PERMISSION_DENIED("PR", "Permission Denied", 403),

    // 500
    DATABASE_ERROR("DB", "Database Error", 500),
    SERVER_ERROR("SE", "Internal Server Error", 500);

    private final String code;
    private final String message;
    private final int status;
}
