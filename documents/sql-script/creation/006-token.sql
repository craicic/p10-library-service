create table token
(
    id              serial  not null
        constraint token_pkey
            primary key,
    expiration_date date,
    token_uuid      uuid,
    user_id         integer not null
        constraint fkj8rfw4x0wjjyibfqq566j4qng
            references users
);

alter table token
    owner to "library-service";

