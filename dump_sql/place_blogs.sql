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

INSERT INTO withdog.place_blogs (id, title, place_id, blog_url, image_url, description, created_at, updated_at) VALUES (1, '괴산 개차반 캠핑장 / 충북 애견동반 캠핑장', 1, 'https://blog.naver.com/jeon_hyeji/223441849162', 'https://blogthumb.pstatic.net/MjAyNDA1MDdfMjQx/MDAxNzE1MDc4OTYwMDEw.82cfTULy7fI8dneffyc-rS8oTiNfwXxUUp-T1Gp5txcg.frucv59uyp-Bltj6A48ULb4ivGbUFvDO6cZBfjg4LFYg.JPEG/1715078598744.jpg?type=w2', '캠핑가기 정말 좋은 봄과 여름 사이 팡이 데리고 캠핑을 다녀왔습니다. 애견동반 캠핑은 처음인데 이곳에서...', null, null);
INSERT INTO withdog.place_blogs (id, title, place_id, blog_url, image_url, description, created_at, updated_at) VALUES (2, '[충북 괴산] 애견전용 캠핑장 개차반캠핑장⛺ / 생애 첫 설중캠🤍', 1, 'https://blog.naver.com/neulbong9-_-/223354848473', 'https://blogthumb.pstatic.net/MjAyNDAyMDhfMjky/MDAxNzA3MzkxMDg4Mjk4.sD6loa3jykwvpoixACSdeCAIxUbaWqPd2XhOptUd4bog.A3jG1pagl4rvCdh0FwuY5vNDiK_Hx9Nuf_1tZI22JdEg.JPEG.gksmf0932/IMG_0836.jpg?type=w2', '2024.02.04 (일) 몇 달 전부터 가고 싶었지만 항상 예약에 실패해 못 갔던 &quot;개차반 캠핑장&quot; 리뷰...', null, null);
INSERT INTO withdog.place_blogs (id, title, place_id, blog_url, image_url, description, created_at, updated_at) VALUES (3, '충북 아이와 반려견과 함께하기 좋은 캠핑장 [개차반캠핑장]', 1, 'https://blog.naver.com/pingors/223404731874', 'https://blogthumb.pstatic.net/MjAyNDA0MDNfMjMz/MDAxNzEyMTM5MjE5OTky.LxhCPneDWEzm8yYTo8l9GQNnWMdvaSuojHML-tZAsZQg.vi8qawOuInoMt9Z3BnSLrpxb5ZC0FO0qrcTH906KHusg.PNG/IMG_8365.PNG?type=w2', '안녕하세요! 오늘은 충북에 위치해있는 아이와 반려견 모두가 함께하기 좋은 캠핑장 소개해드리려고 해요! ...', null, null);
INSERT INTO withdog.place_blogs (id, title, place_id, blog_url, image_url, description, created_at, updated_at) VALUES (4, '[캠핑 일기] 1. 개차반 캠핑장', 1, 'https://blog.naver.com/boderbubu/223562811751', 'https://blogthumb.pstatic.net/MjAyNDA4MTVfMTc3/MDAxNzIzNzMwMjAxMzcx.n76DLT87HumdDsaohwyzvPhBx_kSc2K2G9ivKqR_6Icg.r4kxVS0GS_fCHT6p6xuTc4wI61ZoMZ1su0jFlPYraq4g.JPEG/KakaoTalk_20240729_001551222_22.jpg?type=w2', '여기는 충북 괴산에 있는 개차반 캠핑장 애견 캠핑장이고 소문 듣고 부리나 캐 달려왔어!! 캠핑사이트 입실...', null, null);
