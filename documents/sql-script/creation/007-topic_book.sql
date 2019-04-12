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

