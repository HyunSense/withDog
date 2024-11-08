package withdog.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
public class BookmarkId implements Serializable {
    private Long member;
    private Long place;
}
