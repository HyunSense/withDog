create table places
(
    id              bigint auto_increment
        primary key,
    category_id     tinyint                            not null,
    name            varchar(100)                       not null,
    thumbnail_url   varchar(255)                       null,
    price           int                                not null,
    address_part1   varchar(255)                       not null,
    address_part2   varchar(255)                       not null,
    phone           varchar(50)                        null,
    reservation_url varchar(255)                       null,
    description     text                               null,
    created_at      datetime default CURRENT_TIMESTAMP null,
    updated_at      datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint places_ibfk_1
        foreign key (category_id) references categories (id)
);

create index category_id
    on places (category_id);

INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_url, description, created_at, updated_at) VALUES (1, 1, '테스트중 캠핑장', '/uploads/5bc0b9a7-10b2-4989-9995-5160c8e9e312_detail-img.jpg', 120000, '인천 연수구', '랜드마크로 20', '010-4394-0571', 'https://camfit.co.kr/camp/6528b4cb2ce90e001e2fc4e7?longTermTypes=longTerm', null, '2024-12-05 04:29:49', '2024-12-05 04:29:49');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_url, description, created_at, updated_at) VALUES (2, 1, '테스트중 캠핑장2', '/uploads/87446640-e433-4977-8050-59c5acbb55e0_thumb_1000_2650DS2qwvcUlovR0vl66UHm.jpg', 1234, '인천 연수구', '랜드마크로 20', '010-4394-0571', 'text.com', null, '2024-12-06 04:52:10', '2024-12-06 04:52:10');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_url, description, created_at, updated_at) VALUES (3, 1, 'test', '/uploads/6f4a5f72-3522-4289-820d-f4ced776b078_thumb_1000_2820wxneP6GTOin3L21l3DK1.jpg', 80000, '경기 성남시 분당구', '판교역로 4', '01043940571', 'https://camfit.co.kr/camp/6528b4cb2ce90e001e2fc4e7?longTermTypes=longTerm', null, '2024-12-06 17:02:55', '2024-12-06 17:02:55');
