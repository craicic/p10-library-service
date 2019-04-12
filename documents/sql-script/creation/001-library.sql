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

