UPDATE public.book
SET author           = 'Jules Verne',
    isbn             = null,
    publication_date = '1910-02-01 18:26:16.078000',
    quantity         = 5,
    summary          = 'An history of submarine',
    title            = 'Vingt mille lieues sous les mers',
    language_id      = 1,
    library_id       = 0
WHERE id = 0;
UPDATE public.book
SET author           = 'JRR Tolkien',
    isbn             = null,
    publication_date = '1920-02-04 14:13:18.156000',
    quantity         = 0,
    summary          = 'Gandalf and pals',
    title            = 'La communauté de l''anneau',
    language_id      = 0,
    library_id       = 0
WHERE id = 20;