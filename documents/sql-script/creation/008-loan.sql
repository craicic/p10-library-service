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

