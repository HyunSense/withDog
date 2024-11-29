package withdog.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringFilter {


    public static String addressSpecialWord(String addressPart1) {

        return addressPart1.replace("특별자치도", "");
    }
}
