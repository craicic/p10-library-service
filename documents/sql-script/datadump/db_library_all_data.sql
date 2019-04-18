INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id) VALUES (1, 'Berberian', '', '2015-03-21 21:20:30.257000', 5, 'Une histoire du rock', 'jukebox', 1, 1);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id) VALUES (2, 'Charles Berberian', '', '2000-01-01 00:00:00.000000', 10, 'Une BD sur l''histoire du rock''n''roll', 'Jukebox', 1, 1);

INSERT INTO public.language (id, name) VALUES (1, 'French');
INSERT INTO public.language (id, name) VALUES (2, 'English');
INSERT INTO public.language (id, name) VALUES (3, 'Russian');
INSERT INTO public.language (id, name) VALUES (4, 'German');

INSERT INTO public.library (id, address, city, name, phone_number, postal_code) VALUES (1, 'Rue de l''Alma', 'Rennes', 'Médiathèque de l''Alma', '0299115544', 35000);

INSERT INTO public.users (id, address, city, first_name, last_connection, last_name, mail_address, password_hash, phone_number, postal_code, pseudo, register_date) VALUES (1, 'Rue du parc', 'Rennes', 'René', '2019-03-21 22:19:43.699000', 'Chassain', 'placeholdermail@gmail.com', '86b0c77f3c31c8299e9661110a23873696b01770e758c868865f88ac179bab258c1ce39febf87107', '0011225544', 35000, 'RRee', '2019-03-21');

INSERT INTO public.token (id, expiration_date, token_uuid, user_id) VALUES (1, '2019-04-11', 'ffd868f8-6c35-4439-bcf7-c2f744d32070', 1);

INSERT INTO public.topic (id, name) VALUES (1, 'Fantasy');
INSERT INTO public.topic (id, name) VALUES (2, 'Horror');
INSERT INTO public.topic (id, name) VALUES (3, 'BD');
INSERT INTO public.topic (id, name) VALUES (4, 'Thriller');

INSERT INTO public.topic_book (book_id, topic_id) VALUES (2, 1);

INSERT INTO public.loan (id, closed, extended, loan_end_date, loan_start_date, book_id, user_id) VALUES (1, false, false, '2019-04-02', '2019-04-01', 2, 1);
