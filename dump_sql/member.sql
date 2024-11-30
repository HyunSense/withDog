create table member
(
    id         bigint auto_increment
        primary key,
    username   varchar(50)                        not null,
    password   varchar(100)                       not null,
    name       varchar(50)                        not null,
    email      varchar(100)                       not null,
    role       varchar(50)                        not null,
    created_at datetime default CURRENT_TIMESTAMP null,
    constraint username
        unique (username)
);

INSERT INTO withdog.member (id, username, password, name, email, role, created_at) VALUES (1, 'test1234', '$2a$10$MRNLHTOxeEK1aiucmk68LOHXpTFr.Ep9m66Y83lrjBO3k5sKloWUO', 'hyunjaehoon', '123gtgt@naver.com', 'ROLE_USER', '2024-10-29 22:13:35');
INSERT INTO withdog.member (id, username, password, name, email, role, created_at) VALUES (2, 'jaehoon', '$2a$10$c4olM/7SvW6ssOoViMI3QOMhd79dmOlXFjQeE1t8Kt7HZyUjhdDc.', 'hyunjaehoon', '123gtgt@naver.com', 'ROLE_USER', '2024-10-29 22:15:35');
INSERT INTO withdog.member (id, username, password, name, email, role, created_at) VALUES (3, 'admin', '$2a$10$ogako7AkjuZ7XQd44MMcKeGNt/yRWEw0gK3BpcWcSvvZMRl//OFSq', 'adminname', 'admin@naver.com', 'ROLE_ADMIN', '2024-11-12 21:59:00');
INSERT INTO withdog.member (id, username, password, name, email, role, created_at) VALUES (4, 'jaehoon1022', '$2a$10$2R7N.zoq6kk7jzqNySkk6O/i9msumAvcwfyy35ZIJX6jDNTc3/Ak2', '현재훈', 'jaehoon1022@naver.com', 'ROLE_USER', '2024-11-28 17:04:17');
