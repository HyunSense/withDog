package withdog.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.domain.bookmark.dto.response.BookmarkStatusDto;
import withdog.domain.bookmark.repository.BookmarkRepository;
import withdog.domain.member.entity.Member;
import withdog.domain.place.dto.response.BookmarkedPlaceResponseDto;
import withdog.domain.place.entity.Place;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkQueryService {


    private final BookmarkRepository bookmarkRepository;

    @Cacheable(value = "bookmarkStatus", key = "#member.id + '::' + #place.id")
    @Transactional(readOnly = true)
    public BookmarkStatusDto checkBookmarkCached(Member member, Place place) {

        boolean isBookmarked = bookmarkRepository.existsByMemberAndPlace(member, place);

        return BookmarkStatusDto.builder().bookmarked(isBookmarked).build();
    }

    @Cacheable(value = "memberBookmarks", key = "#member.id")
    @Transactional(readOnly = true)
    public List<BookmarkedPlaceResponseDto> findAllBookmarkedPlaceCached(Member member) {

        List<Place> places = bookmarkRepository.findAllBookmarkedPlacesByMember(member);
        List<BookmarkedPlaceResponseDto> dtos = places.stream().map(p -> BookmarkedPlaceResponseDto.builder()
                .id(p.getId())
                .name(p.getName())
                .address(p.getAddressPart1())
                .thumbnailUrl(p.getThumbnailUrl())
                .build()
        ).collect(Collectors.toList());

        return dtos;
    }
}
