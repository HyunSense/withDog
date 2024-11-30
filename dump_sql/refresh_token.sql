create table refresh_token
(
    id          bigint auto_increment
        primary key,
    member_id   bigint       not null,
    token       varchar(255) not null,
    expiry_date datetime     not null,
    constraint refresh_token_ibfk_1
        foreign key (member_id) references member (id)
);

create index member_id
    on refresh_token (member_id);

