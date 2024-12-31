package withdog.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class NotBlankElementsValidator implements ConstraintValidator<NotBlankElements, List<String>> {

    //TODO: 잘못된 메서드
    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext context) {

        log.info("list.size = {}", list.size());
        if (list.isEmpty()) {
            return true;
        }

        for (String l : list) {
            if (l == null || l.trim().isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
