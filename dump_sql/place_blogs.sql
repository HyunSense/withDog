create table place_blogs
(
    id          bigint auto_increment
        primary key,
    title       varchar(150)                       not null,
    place_id    bigint                             not null,
    blog_url    varchar(2000)                      null,
    image_url   varchar(2000)                      null,
    description varchar(255)                       null,
    created_at  datetime default CURRENT_TIMESTAMP null,
    updated_at  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint place_blogs_ibfk_1
        foreign key (place_id) references places (id)
);

create index place_id
    on place_blogs (place_id);

INSERT INTO withdog.place_blogs (id, title, place_id, blog_url, image_url, description, created_at, updated_at) VALUES (1, '강원도 홍천 캠핑장 소구니캠핑장 아이와 함께한 산속 오토캠핑장', 1, 'https://blog.naver.com/jungae0104/223607358570', 'https://blogthumb.pstatic.net/MjAyNDEwMDVfMjY3/MDAxNzI4MDU4NzMwOTU2.9XplUJZPkf2TR6YU4xm5x15c8CfxRTJ84LAhaRn6z1gg.HNI50VXs5n9sqFCBf02BXY8Dw64DaYIe4S7fT8zLx4Yg.PNG/%C0%C7_%BB%E7%BA%BB%C0%C7_%BB%E7%BA%BB%C0%C7_%BB%E7%BA%BB%C0%C7_%BB%E7%BA%BB%C0%C7_%BB%E7%BA%BB%C0%C7_%BB%E7%BA%BB%C0%C7_%BB%E7%BA%BB.png?type=w2', '오랜만에 우리 가족끼리 강원도 홍천에 위치한 소구니캠핑장으로 2박 3일 가족캠핑을 다녀왔어요. 홍천은 ...', null, null);
INSERT INTO withdog.place_blogs (id, title, place_id, blog_url, image_url, description, created_at, updated_at) VALUES (2, '가을 캠핑 시작 (홍천 소구니 캠핑장 , 홍천 성산왕짜장 ) 찐 내돈내산 리뷰', 1, 'https://blog.naver.com/on__shine/223620510985', 'https://blogthumb.pstatic.net/MjAyNDEwMTVfMjgx/MDAxNzI5MDAyMTg5NjYy.Iwhau6Tvji35rGQ_TGOcf_RrOJJ3rYZrIQ9SkJBXmMkg.Z-KIAtMm_z9gfz7VcFYMdUTHVgHgmN8iEsb2F-lO7k4g.JPEG/IMG_2849.JPG?type=w2', '온누이 캠핑 이야기 홍천 소구니 캠핑장 홍천 성산왕짜장 어느덧 5년차 캠핑러가 되었다.. 맨처음 시작할땐...', null, null);
INSERT INTO withdog.place_blogs (id, title, place_id, blog_url, image_url, description, created_at, updated_at) VALUES (3, '홍천 소구니캠핑장', 1, 'https://blog.naver.com/yjchoi83/223667662525', 'https://blogthumb.pstatic.net/MjAyNDExMjBfMjgy/MDAxNzMyMDk5NzIyNzMy.3CiMliRenVsEdLu5Ggq6PTQwm_Gva4FAe_r-PBtbqo4g.P7ML_SX-_WXbCP7B8QfvKxLSduhObZlrhmJTgvhIS9Qg.JPEG/IMG_3702.jpg?type=w2', '얼마만에 가는 캠핑인지 ㅎㅎ 지호가 금토 친구네집가서 잔다길래 둘이 오랫만에 캠핑장 예약해서 가기로 ...', null, null);
INSERT INTO withdog.place_blogs (id, title, place_id, blog_url, image_url, description, created_at, updated_at) VALUES (4, '몽산포 오션캠핑장 정보', 2, 'https://blog.naver.com/kyjzzang_kr/222131558256', 'https://blogthumb.pstatic.net/MjAyMDEwMzFfMjYw/MDAxNjA0MDcxNDAzNzUy.5-yunLVQAfLRP88pIP04miCtOb4HkwUNt9UDScPS5-gg.nS_XLiqZXLWc_fl_YyLq-8PDKKzxo42J2YGp4GV7gZgg.JPEG.kyjzzang_kr/2.jpg?type=w2', '충청남도 태안 몽산포 오션 캠핑장 안녕하세요. 캠핑과 여행, 맛있고 이쁜 것을 좋아하는 힐캠입니다. 오늘...', null, null);
INSERT INTO withdog.place_blogs (id, title, place_id, blog_url, image_url, description, created_at, updated_at) VALUES (5, '몽산포오션캠핑장에서 1박하기  ', 2, 'https://blog.naver.com/jjpure9999/220732861001', 'https://blogthumb.pstatic.net/20160610_162/jjpure9999_1465546072508vIqEv_JPEG/main.jpg?type=w2', '? ? 캠핑 ? 몽산포 오션캠핑장 ? &nbsp; 요즘 날 좋아 다들 많이 다니시죠~~ 여행하기 너무너무 좋은 날이...', null, null);
INSERT INTO withdog.place_blogs (id, title, place_id, blog_url, image_url, description, created_at, updated_at) VALUES (6, '몽산포 오션캠핑장 정보', 3, 'https://blog.naver.com/kyjzzang_kr/222131558256', 'https://blogthumb.pstatic.net/MjAyMDEwMzFfMjYw/MDAxNjA0MDcxNDAzNzUy.5-yunLVQAfLRP88pIP04miCtOb4HkwUNt9UDScPS5-gg.nS_XLiqZXLWc_fl_YyLq-8PDKKzxo42J2YGp4GV7gZgg.JPEG.kyjzzang_kr/2.jpg?type=w2', '충청남도 태안 몽산포 오션 캠핑장 안녕하세요. 캠핑과 여행, 맛있고 이쁜 것을 좋아하는 힐캠입니다. 오늘...', null, null);
INSERT INTO withdog.place_blogs (id, title, place_id, blog_url, image_url, description, created_at, updated_at) VALUES (7, '몽산포 오션캠핑장 정보', 3, 'https://blog.naver.com/kyjzzang_kr/222131558256', 'https://blogthumb.pstatic.net/MjAyMDEwMzFfMjYw/MDAxNjA0MDcxNDAzNzUy.5-yunLVQAfLRP88pIP04miCtOb4HkwUNt9UDScPS5-gg.nS_XLiqZXLWc_fl_YyLq-8PDKKzxo42J2YGp4GV7gZgg.JPEG.kyjzzang_kr/2.jpg?type=w2', '충청남도 태안 몽산포 오션 캠핑장 안녕하세요. 캠핑과 여행, 맛있고 이쁜 것을 좋아하는 힐캠입니다. 오늘...', null, null);
INSERT INTO withdog.place_blogs (id, title, place_id, blog_url, image_url, description, created_at, updated_at) VALUES (8, '국립 영암 캠핑장 천황야영장 혼자캠핑', 4, 'https://blog.naver.com/kooni/223667744463?isInf=true&trackingCode=nx', 'https://blogthumb.pstatic.net/MjAyNDExMjBfMTQ4/MDAxNzMyMTA0MDAxNTcy.NJTeXYNKEWzofva3pJsWFOkYkwm2R7gyti37zvQmO6Ig.S1-EugLyVBYQWn2SCsUUw_GdeUqSP1BuzQ4UW_rlmeog.JPEG/%C3%B5%C8%B2.jpg?type=w2', '전라남도 영암군에 있는 천황야영장은 월출산 국립공원으로 산행하는 분들이 많이 찾는 국립 영암 캠핑장입...', null, null);
