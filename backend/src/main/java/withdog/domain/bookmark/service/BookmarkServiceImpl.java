package withdog.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.common.constant.ApiResponseCode;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.common.exception.CustomException;
import withdog.domain.bookmark.dto.request.DeleteBookmarksRequestDto;
import withdog.domain.bookmark.dto.response.BookmarkStatusDto;
import withdog.domain.bookmark.entity.Bookmark;
import withdog.domain.bookmark.repository.BookmarkRepository;
import withdog.domain.member.entity.Member;
import withdog.domain.member.repository.MemberRepository;
import withdog.domain.place.dto.response.BookmarkedPlaceResponseDto;
import withdog.domain.place.entity.Place;
import withdog.domain.place.repository.PlaceRepository;
import withdog.domain.stats.entity.PlaceWeeklyStats;
import withdog.domain.stats.repository.PlaceWeeklyStatsRepository;
import withdog.domain.stats.service.PlaceWeeklyStatsService;
import withdog.domain.stats.service.PlaceWeeklyStatsServiceImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final PlaceWeeklyStatsService placeWeeklyStatsService;

    @Transactional(readOnly = true)
    @Override
    public ResponseDto checkBookmark(Long memberId, Long placeId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_MEMBER));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        boolean isBookmarked = bookmarkRepository.existsByMemberAndPlace(member, place);
        BookmarkStatusDto dto = BookmarkStatusDto.builder().bookmarked(isBookmarked).build();

        return DataResponseDto.success(dto);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseDto findAllBookmarkedPlace(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_MEMBER));

        List<Place> places = bookmarkRepository.findAllBookmarkedPlacesByMember(member);
        List<BookmarkedPlaceResponseDto> dtos = places.stream().map((place -> BookmarkedPlaceResponseDto.builder()
                .id(place.getId())
                .category(place.getCategory().getName())
                .name(place.getName())
                .address(place.getAddressPart1())
                .thumbnailUrl(place.getThumbnailUrl())
                .build()
        )).collect(Collectors.toList());

        return DataResponseDto.success(dtos);
    }

    @Override
    public ResponseDto addBookmark(Long memberId, Long placeId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_MEMBER));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        Bookmark bookmark = Bookmark.builder().member(member).place(place).build();
        bookmarkRepository.save(bookmark);

        placeWeeklyStatsService.increaseBookmarkCount(place);

        log.info("Bookmark added for memberId: {}, placeId: {}", memberId, placeId);
        return ResponseDto.success();
    }

    @Override
    public ResponseDto deleteBookmark(Long memberId, Long placeId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_MEMBER));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        bookmarkRepository.deleteByMemberAndPlace(member, place);

        placeWeeklyStatsService.decreaseBookmarkCount(place);

        log.info("Bookmark deleted for memberId: {}, placeId: {}", memberId, placeId);
        return ResponseDto.success();
    }

    @Override
    public ResponseDto deleteAllBookmarks(Long memberId, DeleteBookmarksRequestDto dto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_MEMBER));

        List<Place> places = placeRepository.findAllById(dto.getBookmarkPlaceIds());
        if (places.size() != dto.getBookmarkPlaceIds().size()) {
            throw new CustomException(ApiResponseCode.NOT_EXIST_PLACE);
        }

        bookmarkRepository.deleteAllByMemberAndPlaces(member, places);

        log.info("All Bookmarks deleted for memberId: {}, placeId: {}", memberId, dto.getBookmarkPlaceIds());
        return ResponseDto.success();
    }
}
