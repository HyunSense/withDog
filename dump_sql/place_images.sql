create table place_images
(
    id             bigint auto_increment
        primary key,
    place_id       bigint                             not null,
    image_url      varchar(2000)                      null,
    image_position int      default 1                 null,
    created_at     datetime default CURRENT_TIMESTAMP null,
    updated_at     datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint place_images_ibfk_1
        foreign key (place_id) references places (id)
);

create index place_id
    on place_images (place_id);

INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (1, 1, 'http://192.168.0.5:8080/uploads/05c09ad7-99bf-44bb-9fcd-7e2ac70c4ea0_campingImgTest.png', 1, '2024-11-25 00:08:51', '2024-11-25 03:44:09');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (2, 1, 'http://192.168.0.5:8080/uploads/1389e43c-349c-4a83-9f55-72fdf713df52_placeTestImg.png', 2, '2024-11-25 00:08:51', '2024-11-25 03:45:10');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (3, 2, 'http://192.168.0.5:8080/uploads/47caf301-36fd-41a2-9e34-75e927de1f13_mongsan1.jpg', 1, '2024-11-25 19:52:50', '2024-11-25 19:55:31');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (4, 2, 'http://192.168.0.5:8080/uploads/74323f5b-638f-43b8-83a5-2185aa58cb45_mongsan2.jpg', 2, '2024-11-25 19:52:50', '2024-11-25 19:55:31');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (5, 2, 'http://192.168.0.5:8080/uploads/30e24801-2eef-46f1-a112-33f2ece0ce48_mongsan3.jpg', 3, '2024-11-25 19:52:50', '2024-11-25 19:55:31');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (6, 3, 'http://192.168.0.5:8080/uploads/9a4537eb-d103-4bd1-8ab8-b391c0beda3b_mongsan3.jpg', 1, '2024-11-25 20:00:15', '2024-11-25 20:00:15');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (7, 4, 'http://192.168.0.5:8080/uploads/c7364362-37e9-48f3-9664-2e79da1f3659_detail-img.jpg', 1, '2024-11-28 17:00:35', '2024-11-28 17:00:35');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (8, 5, 'http://192.168.0.5:8080/uploads/9c7093d5-d314-44fa-9171-9e22b0887036_thumb_1000_1328QG8Nl53hnkzq1GYp3dyZ.jpg', 1, '2024-11-28 19:03:39', '2024-11-28 19:03:39');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (9, 6, 'http://192.168.0.5:8080/uploads/f215e652-8512-4112-9486-9e64ffe25683_thumb_1000_2650DS2qwvcUlovR0vl66UHm.jpg', 1, '2024-11-28 19:03:48', '2024-11-28 19:03:48');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (10, 7, 'http://192.168.0.5:8080/uploads/aeff8f70-436b-4b5d-ba66-bfa7f342c7e6_thumb_1000_3027EEC5AnGTIjnxzRjmm0gE.jpg', 1, '2024-11-28 19:03:58', '2024-11-28 19:03:58');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (11, 8, 'http://192.168.0.5:8080/uploads/3acde4ef-772a-440e-996a-191feb076861_thumb_1000_5963OzU0B1OLAFutaZorOicn.jpg', 1, '2024-11-28 19:04:05', '2024-11-28 19:04:05');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (12, 9, 'http://192.168.0.5:8080/uploads/c07c0ac6-99f4-45e0-9be5-6244ea206999_thumb_1000_8451Nc0wPE4UgVY21MMfd5nR.jpg', 1, '2024-11-28 19:04:14', '2024-11-28 19:04:14');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (13, 10, 'http://192.168.0.5:8080/uploads/20e09c5c-c99d-4067-a864-3fac99b9a1ad_thumb_1000_5191swnPmRH6RppQ6oEWOpxC.jpg', 1, '2024-11-28 19:04:58', '2024-11-28 19:04:58');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (14, 11, 'http://192.168.0.5:8080/uploads/4886e86f-381d-4484-82e2-11fb1f298e89_thumb_1000_5963OzU0B1OLAFutaZorOicn.jpg', 1, '2024-11-28 19:05:07', '2024-11-28 19:05:07');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (15, 12, 'http://192.168.0.5:8080/uploads/b1896117-4760-44ff-9830-ea6970d0f879_thumb_1000_6907eu0HkmshCbMgeKoubLzF.jpg', 1, '2024-11-28 19:05:14', '2024-11-28 19:05:14');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (16, 13, 'http://192.168.0.5:8080/uploads/048a1a7a-89bd-4257-bb62-2b39c6c7b03c_thumb_1000_86493wjDlAh6zcaKQW2npbLc.jpg', 1, '2024-11-28 19:05:23', '2024-11-28 19:05:23');
INSERT INTO withdog.place_images (id, place_id, image_url, image_position, created_at, updated_at) VALUES (17, 14, 'http://192.168.0.5:8080/uploads/a97d306c-2787-4bd5-81d3-818d4ef95b69_thumb_1000_304628WIKkWtQj5AdG5RdYEl.jpg', 1, '2024-11-28 19:05:30', '2024-11-28 19:05:30');
