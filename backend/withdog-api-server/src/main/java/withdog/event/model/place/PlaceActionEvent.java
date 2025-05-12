package withdog.event.model.place;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withdog.event.model.UserEvent;

@Getter
@NoArgsConstructor
public class PlaceActionEvent extends UserEvent {

    private Long placeId;

    @Builder
    public PlaceActionEvent(String eventType, String sessionId, Long memberId, Long placeId) {
        super(eventType, sessionId, memberId, System.currentTimeMillis());
        this.placeId = placeId;
    }

    // 유저 분석 기반 서비스
    // 조회수 + 북마크를 user-analytics 에서처리
    // 문제: redis 그리고 dailyStat에 저장된 이 데이터를 어떻게 producer에서 사용할 것인지?
    // 어떠한 필터를 기준으로 일단 장소를 조회 한다.
    // 이 목록들의 결과에서 인기순으로 정렬을 한다.
    // 문제는 전체 목록들을 인기순으로 쿼리 조회를 해야된다는 것이다.
    // 그래야 paging 처리가 가능하다. 이 방법을 쓰려면 결국 view_count, bookmark_count 가 필요한것이지 않나?
    // 그럼 consumer에서 처리하는것이 힘들다. -> producer에서 그냥 처리하는것이 나을 수가 있다.
    // 그렇다면 consumer의 user-analytics라는 서비스가 의미가 있나?
}
