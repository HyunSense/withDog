create table bookmarks
(
    member_id bigint not null,
    place_id  bigint not null,
    primary key (member_id, place_id),
    constraint bookmarks_ibfk_1
        foreign key (member_id) references member (id),
    constraint bookmarks_ibfk_2
        foreign key (place_id) references places (id)
);

create index place_id
    on bookmarks (place_id);

