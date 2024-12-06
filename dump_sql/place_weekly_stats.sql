create table place_weekly_stats
(
    id              bigint auto_increment
        primary key,
    place_id        bigint           not null,
    week_start_date date             not null,
    hit_count       bigint default 0 not null,
    bookmark_count  bigint default 0 not null,
    constraint place_weekly_stats_ibfk_1
        foreign key (place_id) references places (id)
);

create index place_id
    on place_weekly_stats (place_id);

INSERT INTO withdog.place_weekly_stats (id, place_id, week_start_date, hit_count, bookmark_count) VALUES (1, 1, '2024-12-02', 49, 0);
INSERT INTO withdog.place_weekly_stats (id, place_id, week_start_date, hit_count, bookmark_count) VALUES (2, 2, '2024-12-02', 29, 0);
INSERT INTO withdog.place_weekly_stats (id, place_id, week_start_date, hit_count, bookmark_count) VALUES (3, 3, '2024-12-02', 10, 0);
