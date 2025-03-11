package withdog.common.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.StringUtils;
import withdog.common.constant.ApiResponseCode;
import withdog.common.exception.CustomException;
import withdog.domain.place.dto.BlogExtractDto;

import java.io.IOException;
import java.net.UnknownHostException;

@Slf4j
public class BlogMetaDataExtractor {

    private static final int MAX_DESCRIPTION_LENGTH = 255;

    public static BlogExtractDto extract(String url) {

        try {
            Document doc = Jsoup.connect(url).get();

            if (url.contains("naver")) {

                String src = doc.select("iframe#mainFrame").attr("src");
                String naverUrl = "https://blog.naver.com" + src;
                doc = Jsoup.connect(naverUrl).get();
            }

            String title = doc.select("meta[property=og:title]").attr("content");
            if (!StringUtils.hasText(title)) {
                title = doc.title();
            }

            String description = doc.select("meta[property=og:description]").attr("content");

            //TODO: 이미지없을때 기본이미지 필요?
            String imageUrl = doc.select("meta[property=og:image]").attr("content");


            //TODO: NULL 처리 모두필요?
            return BlogExtractDto.builder()
                    .title(title)
                    .description(description)
                    .imageUrl(imageUrl)
                    .build();

        } catch (UnknownHostException e) {
            throw new CustomException(ApiResponseCode.SERVER_ERROR, "유효하지 않은 URL : " + e.getMessage());

        } catch (Exception e) {
            throw new CustomException(ApiResponseCode.SERVER_ERROR, e.getMessage());
        }
    }
}
