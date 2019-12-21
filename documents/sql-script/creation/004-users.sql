create table users
(
    id              serial       not null
        constraint users_pkey
            primary key,
    address         varchar(255) not null,
    city            varchar(255) not null,
    first_name      varchar(255) not null,
    last_connection timestamp    not null,
    last_name       varchar(255) not null,
    mail_address    varchar(255) not null
        constraint users_idx1
            unique,
    password_hash   varchar(255) not null,
    phone_number    varchar(255) not null,
    postal_code     integer      not null,
    pseudo          varchar(255) not null
        constraint users_idx
            unique,
    register_date   date         not null
);

alter table users
    owner to "library-service";

