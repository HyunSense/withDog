create table categories
(
    id   tinyint auto_increment
        primary key,
    name varchar(50) not null
);

INSERT INTO withdog.categories (id, name) VALUES (1, 'camp');
INSERT INTO withdog.categories (id, name) VALUES (2, 'park');
