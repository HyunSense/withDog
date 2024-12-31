package withdog.domain.bookmark.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withdog.domain.member.entity.Member;
import withdog.domain.place.entity.Place;

@Entity
@Getter
@Table(name = "bookmarks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BookmarkId.class)
public class Bookmark {

    @Id
    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Id
    @JoinColumn(name = "place_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @Builder
    public Bookmark(Member member, Place place) {
        this.member = member;
        this.place = place;
    }
}
