package withdog.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UrlListValidator implements ConstraintValidator<ValidUrl, List<String>> {

    //TODO: 잘못된 메서드
    @Override
    public boolean isValid(List<String> urlList, ConstraintValidatorContext context) {

        if (urlList == null || urlList.isEmpty()) {
            return true;
        }

        for (String url : urlList) {
            if (url == null || url.isBlank()) {
                return false;
            }
            // 둘다 포함하지 않는다면 (true)
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                return false;
            }
        }

        // 모든 검증 통과시 true
        return true;
    }
}
