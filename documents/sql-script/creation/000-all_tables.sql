create table library
(
    id serial not null
        constraint library_pkey
            primary key,
    address varchar(255) not null,
    city varchar(255) not null,
    name varchar(255) not null
        constraint library_idx
            unique,
    phone_number varchar(255) not null,
    postal_code integer not null
);

alter table library owner to "library-service";

create table topic
(
    id serial not null
        constraint topic_pkey
            primary key,
    name varchar(255) not null
        constraint topic_idx
            unique
);

alter table topic owner to "library-service";

create table language
(
    id serial not null
        constraint language_pkey
            primary key,
    name varchar(255) not null
        constraint language_idx
            unique
);

alter table language owner to "library-service";

create table users
(
    id serial not null
        constraint users_pkey
            primary key,
    address varchar(255) not null,
    city varchar(255) not null,
    first_name varchar(255) not null,
    last_connection timestamp not null,
    last_name varchar(255) not null,
    mail_address varchar(255) not null
        constraint users_idx1
            unique,
    password_hash varchar(255) not null,
    phone_number varchar(255) not null,
    postal_code integer not null,
    pseudo varchar(255) not null
        constraint users_idx
            unique,
    register_date date not null
);

alter table users owner to "library-service";

create table book
(
    id serial not null
        constraint book_pkey
            primary key,
    author varchar(255) not null,
    isbn varchar(255),
    publication_date timestamp not null,
    quantity integer not null,
    summary varchar(255),
    title varchar(255) not null,
    language_id integer
        constraint fkmrhfp9wfi5dy4bwl87bx8ivua
            references language,
    library_id integer
        constraint fkaojxagnfmppd09k35kye5eph5
            references library
);

alter table book owner to "library-service";

create table token
(
    id serial not null
        constraint token_pkey
            primary key,
    expiration_date date,
    token_uuid uuid,
    user_id integer not null
        constraint fkj8rfw4x0wjjyibfqq566j4qng
            references users
);

alter table token owner to "library-service";

create table topic_book
(
    book_id integer not null
        constraint fkfk1x6hrtcn5rifaxvm1145ajr
            references book,
    topic_id integer not null
        constraint fk1o2p9ouikj5q45h1788hlwq3s
            references topic,
    constraint topic_book_idx
        unique (book_id, topic_id)
);

alter table topic_book owner to "library-service";

create table loan
(
    id serial not null
        constraint loan_pkey
            primary key,
    closed boolean not null,
    extended boolean not null,
    loan_end_date date not null,
    loan_start_date date not null,
    book_id integer not null
        constraint fk88c0ydlo57pcgp137tntrgqx1
            references book,
    user_id integer not null
        constraint fkkv6kl0xa6lu02a89nlowpapnv
            references users
);

alter table loan owner to "library-service";

