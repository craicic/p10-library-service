INSERT INTO public.language (id, name)
VALUES (4, 'Allemand');
INSERT INTO public.language (id, name)
VALUES (1, 'Français');
INSERT INTO public.language (id, name)
VALUES (2, 'Anglais');
INSERT INTO public.language (id, name)
VALUES (3, 'Russe');
INSERT INTO public.library (id, address, city, name, phone_number, postal_code)
VALUES (1, 'Rue de l''Alma', 'Rennes', 'Médiathèque de l''Alma', '0299115544', 35000);
INSERT INTO public.library (id, address, city, name, phone_number, postal_code)
VALUES (2, '15 champ de Mars', 'Rennes', 'Médiathèque du champ de Mars', '0299115533', 35000);
INSERT INTO public.library (id, address, city, name, phone_number, postal_code)
VALUES (3, '15 rue de Bordeaux', 'Rennes', 'Bibliothèque du Nord', '0299115522', 35000);
INSERT INTO public.topic (id, name)
VALUES (1, 'Fantasy');
INSERT INTO public.topic (id, name)
VALUES (3, 'BD');
INSERT INTO public.topic (id, name)
VALUES (4, 'Thriller');
INSERT INTO public.topic (id, name)
VALUES (2, 'Horreur');
INSERT INTO public.topic (id, name)
VALUES (5, 'Space opéra');
INSERT INTO public.topic (id, name)
VALUES (6, 'Policier');
INSERT INTO public.topic (id, name)
VALUES (7, 'SF');
INSERT INTO public.topic (id, name)
VALUES (9, 'Autobiographie');
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (1, 'Berberian', '', '2015-03-21 21:20:30.257000', 5, 'Une histoire du rock', 'jukebox', 1, 1);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (3, 'Micheal Moorcock', null, '1972-04-24 09:47:06.703000', 3, 'Le premier tome du cycle d''Elric',
        'Elric des Dragons', 1, 3);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (4, 'Micheal Moorcock', null, '1972-04-24 09:47:06.703000', 1, 'Le premier tome du cycle d''Elric',
        'Elric des Dragons', 2, 1);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (5, 'Micheal Moorcock', null, '1989-01-01 00:00:00.001000', 1, 'Le deuxième tome du cycle d''Elric',
        'La Forteresse de la perle', 2, 2);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (7, 'Micheal Moorcock', null, '1971-04-24 09:52:16.744000', 1, 'Le cinquième tome du cycle d''Elric',
        'La Sorcière dormante', 2, 2);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (26, 'Jodorowsky', null, '1992-04-30 14:24:51.984000', 0,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce bibendum elit risus, id fringilla nisi gravida vel. Phasellus a dictum ex. Integer mattis sapien ac odio convallis, ut interdum felis euismod.',
        'La Caste des Métabarons : Aghnar le Bisaïeul', 1, 3);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (2, 'Charles Berberian', '', '2000-01-01 00:00:00.000000', 9, 'Une BD sur l''histoire du rock''n''roll',
        'Jukebox', 1, 1);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (6, 'Micheal Moorcock', null, '1976-04-24 09:50:51.718000', 1, 'Le troisième tome du cycle d''Elric',
        'Le Navigateur sur les mers du destin', 2, 2);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (24, 'Jodorowsky', null, '1992-04-30 14:24:51.984000', 1,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce bibendum elit risus, id fringilla nisi gravida vel. Phasellus a dictum ex. Integer mattis sapien ac odio convallis, ut interdum felis euismod.',
        'La Caste des Métabarons : Othon le Trisaïeul', 1, 3);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (27, 'Jodorowsky', null, '1992-04-30 14:24:51.984000', 1,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce bibendum elit risus, id fringilla nisi gravida vel. Phasellus a dictum ex. Integer mattis sapien ac odio convallis, ut interdum felis euismod.',
        'La Caste des Métabarons : Oda la Bisaïeule', 1, 3);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (28, 'Jodorowsky', null, '1992-04-30 14:24:51.984000', 1,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce bibendum elit risus, id fringilla nisi gravida vel. Phasellus a dictum ex. Integer mattis sapien ac odio convallis, ut interdum felis euismod.',
        'La Caste des Métabarons : Tête d''Acier l''Aïeul', 1, 3);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (29, 'Jodorowsky', null, '1992-04-30 14:24:51.984000', 1,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce bibendum elit risus, id fringilla nisi gravida vel. Phasellus a dictum ex. Integer mattis sapien ac odio convallis, ut interdum felis euismod.',
        'La Caste des Métabarons : Doña Vicenta Gabriela de Rhoka l''aïeule', 1, 3);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (30, 'Jodorowsky', null, '1992-04-30 14:24:51.984000', 1,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce bibendum elit risus, id fringilla nisi gravida vel. Phasellus a dictum ex. Integer mattis sapien ac odio convallis, ut interdum felis euismod.',
        'La Caste des Métabarons : Aghora le père-mère', 1, 3);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (31, 'Jodorowsky', null, '1992-04-30 14:24:51.984000', 1,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce bibendum elit risus, id fringilla nisi gravida vel. Phasellus a dictum ex. Integer mattis sapien ac odio convallis, ut interdum felis euismod.',
        'La Caste des Métabarons : Sans-Nom le dernier Méta-Baron', 1, 3);
INSERT INTO public.book (id, author, isbn, publication_date, quantity, summary, title, language_id, library_id)
VALUES (25, 'Jodorowsky', null, '1992-04-30 14:24:51.984000', 1,
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce bibendum elit risus, id fringilla nisi gravida vel. Phasellus a dictum ex. Integer mattis sapien ac odio convallis, ut interdum felis euismod.',
        'La Caste des Métabarons : Honorata la Trisaïeule', 1, 3);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (3, 1);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (4, 1);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (5, 1);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (6, 1);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (7, 1);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (2, 2);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (1, 2);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (25, 3);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (25, 5);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (26, 5);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (26, 3);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (27, 5);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (27, 3);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (28, 5);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (28, 3);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (29, 5);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (29, 3);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (30, 5);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (30, 3);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (31, 5);
INSERT INTO public.topic_book (book_id, topic_id)
VALUES (31, 3);
INSERT INTO public.users (id, address, city, first_name, last_connection, last_name, mail_address, password_hash,
                          phone_number, postal_code, pseudo, register_date)
VALUES (1, 'Rue du parc', 'Rennes', 'René', '2019-03-21 22:19:43.699000', 'Chassain', 'rouzic.jeremie@gmail.com',
        '86b0c77f3c31c8299e9661110a23873696b01770e758c868865f88ac179bab258c1ce39febf87107', '0011225544', 35000, 'RRee',
        '2019-03-21');
INSERT INTO public.users (id, address, city, first_name, last_connection, last_name, mail_address, password_hash,
                          phone_number, postal_code, pseudo, register_date)
VALUES (2, 'Avenue de Mars', 'Rennes', 'Jérémie', '2019-04-24 12:01:35.433000', 'Roux', 'excavationnn@protonmail.com',
        'aac2c71fcb883af99cc9692859ca0f748cd454b21570c20a0c7af29ecbeac1ef7642e24f0dc9c4fa', '0622334455', 35000, 'GG',
        '2019-04-24');
INSERT INTO public.loan (id, closed, extended, loan_end_date, loan_start_date, book_id, user_id)
VALUES (1, false, true, '2019-04-30', '2019-04-01', 2, 2);
INSERT INTO public.loan (id, closed, extended, loan_end_date, loan_start_date, book_id, user_id)
VALUES (4, false, true, '2019-06-25', '2019-04-30', 25, 2);
INSERT INTO public.loan (id, closed, extended, loan_end_date, loan_start_date, book_id, user_id)
VALUES (5, false, false, '2019-04-26', '2019-03-30', 26, 2);
INSERT INTO public.loan (id, closed, extended, loan_end_date, loan_start_date, book_id, user_id)
VALUES (3, true, true, '2019-06-21', '2019-04-26', 6, 2);
INSERT INTO public.loan (id, closed, extended, loan_end_date, loan_start_date, book_id, user_id)
VALUES (9, false, true, '2020-02-11', '2019-12-17', 2, 2);
INSERT INTO public.token (id, expiration_date, token_uuid, user_id)
VALUES (1, '2019-04-11', 'ffd868f8-6c35-4439-bcf7-c2f744d32070', 1);
INSERT INTO public.token (id, expiration_date, token_uuid, user_id)
VALUES (2, '2019-05-09', 'a00a38d8-b98d-400c-821b-4a1ddd21aa17', 1);
INSERT INTO public.token (id, expiration_date, token_uuid, user_id)
VALUES (4, '2019-05-15', '3a66484c-515d-4da3-9a62-de290dded036', 2);
INSERT INTO public.token (id, expiration_date, token_uuid, user_id)
VALUES (5, '2020-01-03', 'a3be600f-cf55-4c47-9acc-51714889d7de', 2);