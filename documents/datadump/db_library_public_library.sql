UPDATE public.library
SET address      = 'Les Champs libres',
    city         = 'Rennes',
    name         = 'LCL bibli',
    phone_number = '0299999999',
    postal_code  = 35000
WHERE id = 0;
UPDATE public.library
SET address      = 'Rennes Nord',
    city         = 'Rennes',
    name         = 'RN bibli',
    phone_number = '0299887766',
    postal_code  = 35000
WHERE id = 1;