create table book
(
    id               serial       not null
        constraint book_pkey
            primary key,
    author           varchar(255) not null,
    isbn             varchar(255),
    publication_date timestamp    not null,
    quantity         integer      not null,
    summary          varchar(255),
    title            varchar(255) not null,
    language_id      integer
        constraint fkmrhfp9wfi5dy4bwl87bx8ivua
            references language,
    library_id       integer
        constraint fkaojxagnfmppd09k35kye5eph5
            references library
);

alter table book
    owner to "library-service";

