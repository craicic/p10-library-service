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

