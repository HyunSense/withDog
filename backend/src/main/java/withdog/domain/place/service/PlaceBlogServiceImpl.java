package withdog.domain.place.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.common.util.BlogMetaDataExtractor;
import withdog.domain.place.dto.BlogExtractDto;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.PlaceBlog;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PlaceBlogServiceImpl implements PlaceBlogService{

    @Override
    public void save(Place place, List<String> blogUrls) {

        for (String blogUrl : blogUrls) {
            BlogExtractDto extractDto = BlogMetaDataExtractor.extract(blogUrl);
            PlaceBlog placeBlog = extractDto.toEntity(place, blogUrl);
            place.addBlog(placeBlog);
        }
    }

    @Override
    public void deleteAll(Place place) {
        place.getPlaceBlogs().clear();
    }
}
