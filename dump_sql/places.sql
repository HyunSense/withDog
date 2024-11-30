create table places
(
    id               bigint auto_increment
        primary key,
    category_id      tinyint                            not null,
    name             varchar(100)                       not null,
    thumbnail_url    varchar(255)                       null,
    price            int                                not null,
    address_part1    varchar(255)                       not null,
    address_part2    varchar(255)                       not null,
    phone            varchar(50)                        null,
    reservation_link varchar(255)                       null,
    description      text                               null,
    created_at       datetime default CURRENT_TIMESTAMP null,
    updated_at       datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint places_ibfk_1
        foreign key (category_id) references categories (id)
);

create index category_id
    on places (category_id);

INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (1, 1, '캠핑장테스트1', 'http://192.168.0.5:8080/uploads/05c09ad7-99bf-44bb-9fcd-7e2ac70c4ea0_campingImgTest.png', 10000, '강원 홍천군', '내촌면 용포길 95-48', '010-4394-0571', 'https://camfit.co.kr/camp/6528b4cb2ce90e001e2fc4e7', null, '2024-11-25 00:08:51', '2024-11-25 18:03:44');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (2, 1, '몽산포 오션캠핑장', 'http://192.168.0.5:8080/uploads/47caf301-36fd-41a2-9e34-75e927de1f13_mongsan1.jpg', 50000, '충남 태안군', '남면 몽산포길 63-73', '010-4394-0571', 'https://camfit.co.kr/camp/6678eb07530965001e4c4a75', null, '2024-11-25 19:52:50', '2024-11-25 19:56:03');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (3, 1, '테스트 캠핑장2', 'http://192.168.0.5:8080/uploads/9a4537eb-d103-4bd1-8ab8-b391c0beda3b_mongsan3.jpg', 60000, '경기 성남시 분당구', '판교역로 166', '010-4394-0571', 'https://camfit.co.kr/camp/6528b4cb2ce90e001e2fc4e7', null, '2024-11-25 20:00:15', '2024-11-25 20:00:15');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (4, 1, 'detailtest', 'http://192.168.0.5:8080/uploads/c7364362-37e9-48f3-9664-2e79da1f3659_detail-img.jpg', 100000, '인천 연수구', '랜드마크로 20', '010-4394-0571', 'https://camfit.co.kr/camp/6528b4cb2ce90e001e2fc4e7', null, '2024-11-28 17:00:35', '2024-11-28 17:00:35');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (5, 1, '캠핑장테스트001', 'http://192.168.0.5:8080/uploads/9c7093d5-d314-44fa-9171-9e22b0887036_thumb_1000_1328QG8Nl53hnkzq1GYp3dyZ.jpg', 50000, '충남 보령시', '남포면 열린바다로 6', '010-1234-5678', 'test.com', null, '2024-11-28 19:03:39', '2024-11-28 19:03:39');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (6, 1, '캠핑장테스트001', 'http://192.168.0.5:8080/uploads/f215e652-8512-4112-9486-9e64ffe25683_thumb_1000_2650DS2qwvcUlovR0vl66UHm.jpg', 50000, '충남 보령시', '남포면 열린바다로 6', '010-1234-5678', 'test.com', null, '2024-11-28 19:03:48', '2024-11-28 19:03:48');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (7, 1, '캠핑장테스트002', 'http://192.168.0.5:8080/uploads/aeff8f70-436b-4b5d-ba66-bfa7f342c7e6_thumb_1000_3027EEC5AnGTIjnxzRjmm0gE.jpg', 50000, '충남 보령시', '남포면 열린바다로 6', '010-1234-5678', 'test.com', null, '2024-11-28 19:03:58', '2024-11-28 19:03:58');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (8, 1, '캠핑장테스트004', 'http://192.168.0.5:8080/uploads/3acde4ef-772a-440e-996a-191feb076861_thumb_1000_5963OzU0B1OLAFutaZorOicn.jpg', 50000, '충남 보령시', '남포면 열린바다로 6', '010-1234-5678', 'test.com', null, '2024-11-28 19:04:05', '2024-11-28 19:04:05');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (9, 1, '캠핑장테스트005', 'http://192.168.0.5:8080/uploads/c07c0ac6-99f4-45e0-9be5-6244ea206999_thumb_1000_8451Nc0wPE4UgVY21MMfd5nR.jpg', 50000, '충남 보령시', '남포면 열린바다로 6', '010-1234-5678', 'test.com', null, '2024-11-28 19:04:14', '2024-11-28 19:04:14');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (10, 1, '캠핑장테스트006', 'http://192.168.0.5:8080/uploads/20e09c5c-c99d-4067-a864-3fac99b9a1ad_thumb_1000_5191swnPmRH6RppQ6oEWOpxC.jpg', 50000, '충남 보령시', '남포면 열린바다로 6', '010-1234-5678', 'test.com', null, '2024-11-28 19:04:58', '2024-11-28 19:04:58');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (11, 1, '캠핑장테스트007', 'http://192.168.0.5:8080/uploads/4886e86f-381d-4484-82e2-11fb1f298e89_thumb_1000_5963OzU0B1OLAFutaZorOicn.jpg', 50000, '충남 보령시', '남포면 열린바다로 6', '010-1234-5678', 'test.com', null, '2024-11-28 19:05:07', '2024-11-28 19:05:07');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (12, 1, '캠핑장테스트008', 'http://192.168.0.5:8080/uploads/b1896117-4760-44ff-9830-ea6970d0f879_thumb_1000_6907eu0HkmshCbMgeKoubLzF.jpg', 50000, '충남 보령시', '남포면 열린바다로 6', '010-1234-5678', 'test.com', null, '2024-11-28 19:05:14', '2024-11-28 19:05:14');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (13, 1, '캠핑장테스트010', 'http://192.168.0.5:8080/uploads/048a1a7a-89bd-4257-bb62-2b39c6c7b03c_thumb_1000_86493wjDlAh6zcaKQW2npbLc.jpg', 50000, '충남 보령시', '남포면 열린바다로 6', '010-1234-5678', 'test.com', null, '2024-11-28 19:05:23', '2024-11-28 19:05:23');
INSERT INTO withdog.places (id, category_id, name, thumbnail_url, price, address_part1, address_part2, phone, reservation_link, description, created_at, updated_at) VALUES (14, 1, '캠핑장테스트011', 'http://192.168.0.5:8080/uploads/a97d306c-2787-4bd5-81d3-818d4ef95b69_thumb_1000_304628WIKkWtQj5AdG5RdYEl.jpg', 50000, '충남 보령시', '남포면 열린바다로 6', '010-1234-5678', 'test.com', null, '2024-11-28 19:05:30', '2024-11-28 19:05:30');
