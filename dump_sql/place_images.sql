create table place_images
(
    id             bigint auto_increment
        primary key,
    place_id       bigint                             not null,
    name           varchar(255)                       not null,
    image_url      varchar(2000)                      null,
    image_position int      default 1                 null,
    created_at     datetime default CURRENT_TIMESTAMP null,
    updated_at     datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint place_images_ibfk_1
        foreign key (place_id) references places (id)
);

create index place_id
    on place_images (place_id);

INSERT INTO withdog.place_images (id, place_id, name, image_url, image_position, created_at, updated_at) VALUES (3, 1, 'thumb_1000_2650DS2qwvcUlovR0vl66UHm.jpg', '/uploads/c0af1656-a285-4957-853f-54c45bf280d7_thumb_1000_2650DS2qwvcUlovR0vl66UHm.jpg', 3, '2024-12-05 04:29:49', '2024-12-05 04:29:49');
INSERT INTO withdog.place_images (id, place_id, name, image_url, image_position, created_at, updated_at) VALUES (4, 1, 'thumb_1000_3027EEC5AnGTIjnxzRjmm0gE.jpg', '/uploads/c066ad89-10c4-4b40-bf9a-ab39c0958eb3_thumb_1000_3027EEC5AnGTIjnxzRjmm0gE.jpg', 4, '2024-12-05 04:29:49', '2024-12-05 04:29:49');
INSERT INTO withdog.place_images (id, place_id, name, image_url, image_position, created_at, updated_at) VALUES (5, 2, 'thumb_1000_2650DS2qwvcUlovR0vl66UHm.jpg', '/uploads/87446640-e433-4977-8050-59c5acbb55e0_thumb_1000_2650DS2qwvcUlovR0vl66UHm.jpg', 1, '2024-12-06 04:52:10', '2024-12-06 04:52:10');
INSERT INTO withdog.place_images (id, place_id, name, image_url, image_position, created_at, updated_at) VALUES (6, 2, 'thumb_1000_75604VQopXadvXDP3tjL7Ybq.jpg', '/uploads/39bf2b28-c1cd-4ed3-b269-3d7efa2143e2_thumb_1000_75604VQopXadvXDP3tjL7Ybq.jpg', 2, '2024-12-06 04:52:10', '2024-12-06 04:52:10');
INSERT INTO withdog.place_images (id, place_id, name, image_url, image_position, created_at, updated_at) VALUES (7, 2, 'thumb_1000_5191swnPmRH6RppQ6oEWOpxC.jpg', '/uploads/d5e0ee35-d682-4c02-a22e-eb4228fc9830_thumb_1000_5191swnPmRH6RppQ6oEWOpxC.jpg', 3, '2024-12-06 04:52:10', '2024-12-06 04:52:10');
INSERT INTO withdog.place_images (id, place_id, name, image_url, image_position, created_at, updated_at) VALUES (8, 3, 'thumb_1000_2820wxneP6GTOin3L21l3DK1.jpg', '/uploads/6f4a5f72-3522-4289-820d-f4ced776b078_thumb_1000_2820wxneP6GTOin3L21l3DK1.jpg', 1, '2024-12-06 17:02:55', '2024-12-06 17:02:55');
INSERT INTO withdog.place_images (id, place_id, name, image_url, image_position, created_at, updated_at) VALUES (9, 3, 'thumb_1000_75604VQopXadvXDP3tjL7Ybq.jpg', '/uploads/b2c367d1-edb8-42f8-96e6-c0a03b97cfac_thumb_1000_75604VQopXadvXDP3tjL7Ybq.jpg', 2, '2024-12-06 17:02:55', '2024-12-06 17:02:55');
INSERT INTO withdog.place_images (id, place_id, name, image_url, image_position, created_at, updated_at) VALUES (10, 3, 'thumb_1000_304628WIKkWtQj5AdG5RdYEl.jpg', '/uploads/aecb4a02-24a2-4b24-9ab1-e3a4ffefa458_thumb_1000_304628WIKkWtQj5AdG5RdYEl.jpg', 3, '2024-12-06 17:02:55', '2024-12-06 17:02:55');
