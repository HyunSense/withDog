package withdog.domain.bookmark.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import withdog.domain.member.entity.Member;
import withdog.domain.place.entity.Place;

@Entity
@Getter
@Table(name = "bookmarks")
@NoArgsConstructor
@IdClass(BookmarkId.class)
public class Bookmark {

    @Id
    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Id
    @JoinColumn(name = "place_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Place place;

    @Builder
    public Bookmark(Member member, Place place) {
        this.member = member;
        this.place = place;
    }
}
