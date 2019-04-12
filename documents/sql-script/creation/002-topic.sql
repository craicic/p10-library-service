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

