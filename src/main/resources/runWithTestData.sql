CREATE TABLE IF NOT EXISTS users (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY (
        INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1
    ),
    name character varying(50) COLLATE pg_catalog."default",
    username character varying(50) COLLATE pg_catalog."default",
    email character varying(50) COLLATE pg_catalog."default",
    password character varying(50) COLLATE pg_catalog."default",
    created_at date NOT NULL DEFAULT CURRENT_DATE,
    updated_at date NOT NULL DEFAULT CURRENT_DATE,
    deleted_at date,
    created_by integer NOT NULL DEFAULT 1,
    updated_by integer NOT NULL DEFAULT 1,
    deleted_by integer,
    role character varying(50) COLLATE pg_catalog."default" DEFAULT 'AGENT' :: character varying,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

insert into
    users (name, username, email, password, role)
values
    (
        'ADMIN',
        'admin',
        'admin@kuray.dev',
        'admin',
        'ADMIN'
    );

insert into
    users (name, username, email, password, role)
values
    (
        'AGENT',
        'agent',
        'agent@kuray.dev',
        'agent',
        'AGENT'
    );

--------------------

CREATE TABLE IF NOT EXISTS hotels
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    address_full character varying(250) COLLATE pg_catalog."default" NOT NULL,
    address_district character varying(50) COLLATE pg_catalog."default" NOT NULL,
    address_city character varying(50) COLLATE pg_catalog."default" NOT NULL,
    address_country character varying(50) COLLATE pg_catalog."default" NOT NULL,
    star_rating integer DEFAULT 3,
    has_car_park boolean DEFAULT false,
    has_internet boolean DEFAULT false,
    has_pool boolean DEFAULT false,
    has_conciege boolean DEFAULT false,
    has_spa boolean DEFAULT false,
    has_room_service boolean DEFAULT false,
    phone character varying(50) COLLATE pg_catalog."default",
    website character varying(250) COLLATE pg_catalog."default" NOT NULL,
    email character varying(50) COLLATE pg_catalog."default",
    created_at date NOT NULL DEFAULT CURRENT_DATE,
    updated_at date NOT NULL DEFAULT CURRENT_DATE,
    deleted_at date,
    created_by integer NOT NULL DEFAULT 1,
    updated_by integer NOT NULL DEFAULT 1,
    deleted_by integer,
    CONSTRAINT hotels_pkey PRIMARY KEY (id)
);

INSERT INTO hotels (name, address_full, address_district, address_city, address_country, website) VALUES ('Swissotel The Bosphorus', 'Vişnezade Mahallesi, Acısu Sokağı No:19, 34357 Beşiktaş/İstanbul', 'Beşiktaş', 'İstanbul', 'Türkiye', 'https://www.swissotel.com/hotels/istanbul/');
INSERT INTO hotels (name, address_full, address_district, address_city, address_country, website) VALUES ('Hilton Istanbul Bosphorus', 'Cumhuriyet Caddesi, Harbiye, 34367 Şişli/İstanbul', 'Şişli', 'İstanbul', 'Türkiye', 'https://www.hilton.com/en/hotels/isthibi-hilton-istanbul-bosphorus/');
INSERT INTO hotels (name, address_full, address_district, address_city, address_country, website) VALUES ('The Marmara Taksim', 'Taksim Meydanı, Beyoğlu, 34437 Beyoğlu/İstanbul', 'Beyoğlu', 'İstanbul', 'Türkiye', 'https://www.themarmarahotels.com/tr/the-marmara-taksim/');


CREATE TABLE IF NOT EXISTS pansions
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(50) COLLATE pg_catalog."default" DEFAULT 'BED_AND_BREAKFAST'::character varying,
    breakfast boolean DEFAULT true,
    lunch boolean DEFAULT false,
    dinner boolean DEFAULT false,
    midnight_snack boolean DEFAULT false,
    soft_drinks boolean DEFAULT false,
    alcoholic_drinks boolean DEFAULT false,
    snacks boolean DEFAULT false,
    created_at date NOT NULL DEFAULT CURRENT_DATE,
    updated_at date NOT NULL DEFAULT CURRENT_DATE,
    deleted_at date,
    created_by integer NOT NULL DEFAULT 1,
    updated_by integer NOT NULL DEFAULT 1,
    deleted_by integer,
    hotel_id integer,
    CONSTRAINT pansions_pkey PRIMARY KEY (id),
    CONSTRAINT type CHECK (name::text = ANY (ARRAY['BED_AND_BREAKFAST'::character varying, 'HALF_BOARD'::character varying, 'FULL_BOARD'::character varying, 'ALL_INCLUSIVE'::character varying, 'ULTRA_ALL_INCLUSIVE'::character varying, 'BED_ONLY'::character varying, 'ALL_INCLUSIVE_NO_ALCOHOL'::character varying]::text[]))
);

INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'BED_AND_BREAKFAST', true, false, false, false, false, false, false, 1);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'HALF_BOARD', true, false, true, false, false, false, false, 1);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'FULL_BOARD', true, true, true, false, false, false, false, 1);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'ALL_INCLUSIVE', true, true, true, true, true, true, true, 1);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'ULTRA_ALL_INCLUSIVE', true, true, true, true, true, true, true, 1);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'BED_ONLY', false, false, false, false, false, false, false, 1);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'ALL_INCLUSIVE_NO_ALCOHOL', true, true, true, true, true, false, true, 1);

INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'BED_AND_BREAKFAST', true, false, false, false, false, false, false, 2);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'HALF_BOARD', true, false, true, false, false, false, false, 2);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'FULL_BOARD', true, true, true, false, false, false, false, 2);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'ALL_INCLUSIVE', true, true, true, true, true, true, true, 2);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'ULTRA_ALL_INCLUSIVE', true, true, true, true, true, true, true, 2);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'BED_ONLY', false, false, false, false, false, false, false, 2);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'ALL_INCLUSIVE_NO_ALCOHOL', true, true, true, true, true, false, true, 2);

INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'BED_AND_BREAKFAST', true, false, false, false, false, false, false, 3);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'HALF_BOARD', true, false, true, false, false, false, false, 3);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'FULL_BOARD', true, true, true, false, false, false, false, 3);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'ALL_INCLUSIVE', true, true, true, true, true, true, true, 3);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'ULTRA_ALL_INCLUSIVE', true, true, true, true, true, true, true, 3);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'BED_ONLY', false, false, false, false, false, false, false, 3);
INSERT INTO pansions (name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) VALUES ( 'ALL_INCLUSIVE_NO_ALCOHOL', true, true, true, true, true, false, true, 3);


CREATE TABLE IF NOT EXISTS seasons
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(50) COLLATE pg_catalog."default" DEFAULT 'LOW'::character varying,
    start_date date NOT NULL,
    end_date date NOT NULL,
    created_at date NOT NULL DEFAULT CURRENT_DATE,
    updated_at date NOT NULL DEFAULT CURRENT_DATE,
    deleted_at date,
    created_by integer NOT NULL DEFAULT 1,
    updated_by integer NOT NULL DEFAULT 1,
    deleted_by integer,
    hotel_id integer,
    CONSTRAINT seasons_pkey PRIMARY KEY (id)
);

INSERT INTO seasons (name, start_date, end_date, hotel_id) VALUES ( 'LOW', '2024-01-01', '2024-04-30', 1);
INSERT INTO seasons (name, start_date, end_date, hotel_id) VALUES ( 'MID', '2024-05-01', '2024-08-31', 1);
INSERT INTO seasons (name, start_date, end_date, hotel_id) VALUES ( 'HIGH', '2024-09-01', '2024-12-31', 1);

INSERT INTO seasons (name, start_date, end_date, hotel_id) VALUES ( 'LOW', '2024-01-01', '2024-04-30', 2);
INSERT INTO seasons (name, start_date, end_date, hotel_id) VALUES ( 'MID', '2024-05-01', '2024-08-31', 2);
INSERT INTO seasons (name, start_date, end_date, hotel_id) VALUES ( 'HIGH', '2024-09-01', '2024-12-31', 2);

INSERT INTO seasons (name, start_date, end_date, hotel_id) VALUES ( 'LOW', '2024-01-01', '2024-04-30', 3);
INSERT INTO seasons (name, start_date, end_date, hotel_id) VALUES ( 'MID', '2024-05-01', '2024-08-31', 3);
INSERT INTO seasons (name, start_date, end_date, hotel_id) VALUES ( 'HIGH', '2024-09-01', '2024-12-31', 3);

CREATE TABLE IF NOT EXISTS rooms
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    room_number character varying(50) COLLATE pg_catalog."default" NOT NULL,
    type character varying(50) COLLATE pg_catalog."default" DEFAULT 'DOUBLE'::character varying,
    double_bed_count integer DEFAULT 0,
    single_bed_count integer DEFAULT 0,
    adult_price integer DEFAULT 0,
    child_price integer DEFAULT 0,
    square_meters integer DEFAULT 0,
    has_television boolean DEFAULT true,
    has_balcony boolean DEFAULT false,
    has_air_conditioning boolean DEFAULT false,
    has_minibar boolean DEFAULT false,
    has_valuables_safe boolean DEFAULT false,
    has_gaming_console boolean DEFAULT false,
    has_projector boolean DEFAULT false,
    created_at date NOT NULL DEFAULT CURRENT_DATE,
    updated_at date NOT NULL DEFAULT CURRENT_DATE,
    deleted_at date,
    created_by integer NOT NULL DEFAULT 1,
    updated_by integer NOT NULL DEFAULT 1,
    deleted_by integer,
    hotel_id integer NOT NULL,
    season_id integer NOT NULL,
    pansion_id bigint,
    CONSTRAINT rooms_pkey PRIMARY KEY (id)
);

INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '101', 'DOUBLE', 1, 0, 200.00, 100.00, 20, true, false, false, false, false, false, false, 1, 1, 1);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '102', 'DOUBLE', 1, 0, 200, 100, 20, true, false, false, false, false, false, false, 1, 2, 2);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '103', 'DOUBLE', 1, 0, 200, 100, 20, true, false, false, false, false, false, false, 1, 3, 3);

INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '104', 'SINGLE', 0, 1, 100, 50, 20, true, false, false, false, false, false, false, 1, 1, 4);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '105', 'SINGLE', 0, 1, 100, 50, 20, true, false, false, false, false, false, false, 1, 2, 5);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '106', 'SINGLE', 0, 1, 100, 50, 20, true, false, false, false, false, false, false, 1, 3, 6);

INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '107', 'TRIPLE', 1, 1, 300, 150, 50, true, false, false, false, false, false, false, 1, 1, 7);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '108', 'TRIPLE', 1, 1, 300, 150, 50, true, false, false, false, false, false, false, 1, 2, 8);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '109', 'TRIPLE', 1, 1, 300, 150, 50, true, false, false, false, false, false, false, 1, 3, 9);

INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '201', 'DOUBLE', 1, 0, 200, 100, 20, true, false, false, false, false, false, false, 2, 1, 10);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '202', 'DOUBLE', 1, 0, 200, 100, 20, true, false, false, false, false, false, false, 2, 2, 11);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '203', 'DOUBLE', 1, 0, 200, 100, 20, true, false, false, false, false, false, false, 2, 3, 12);

INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '204', 'SINGLE', 0, 1, 100, 50, 20, true, false, false, false, false, false, false, 2, 1, 13);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '205', 'SINGLE', 0, 1, 100, 50, 20, true, false, false, false, false, false, false, 2, 2, 14);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '206', 'SINGLE', 0, 1, 100, 50, 20, true, false, false, false, false, false, false, 2, 3, 15);

INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '207', 'TRIPLE', 1, 1, 300, 150, 50, true, false, false, false, false, false, false, 2, 1, 16);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '208', 'TRIPLE', 1, 1, 300, 150, 50, true, false, false, false, false, false, false, 2, 2, 17);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '209', 'TRIPLE', 1, 1, 300, 150, 50, true, false, false, false, false, false, false, 2, 3, 18);

INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '301', 'DOUBLE', 1, 0, 200, 100, 20, true, false, false, false, false, false, false, 3, 1, 19);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '302', 'DOUBLE', 1, 0, 200, 100, 20, true, false, false, false, false, false, false, 3, 2, 20);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '303', 'DOUBLE', 1, 0, 200, 100, 20, true, false, false, false, false, false, false, 3, 3, 21);

INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '304', 'SINGLE', 0, 1, 100, 50, 20, true, false, false, false, false, false, false, 3, 1, 22);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '305', 'SINGLE', 0, 1, 100, 50, 20, true, false, false, false, false, false, false, 3, 2, 23);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '306', 'SINGLE', 0, 1, 100, 50, 20, true, false, false, false, false, false, false, 3, 3, 24);

INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '307', 'TRIPLE', 1, 1, 300, 150, 50, true, false, false, false, false, false, false, 3, 1, 25);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '308', 'TRIPLE', 1, 1, 300, 150, 50, true, false, false, false, false, false, false, 3, 2, 26);
INSERT INTO rooms (room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id, pansion_id) VALUES ( '309', 'TRIPLE', 1, 1, 300, 150, 50, true, false, false, false, false, false, false, 3, 3, 27);



CREATE TABLE IF NOT EXISTS reservations
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    guest_citizen_id character varying(50) COLLATE pg_catalog."default",
    guest_full_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    guest_email character varying(50) COLLATE pg_catalog."default" NOT NULL,
    guest_phone character varying(50) COLLATE pg_catalog."default" NOT NULL,
    check_in date NOT NULL,
    check_out date NOT NULL,
    adult_count integer NOT NULL,
    child_count integer NOT NULL,
    price integer NOT NULL,
    created_at date NOT NULL DEFAULT CURRENT_DATE,
    updated_at date NOT NULL DEFAULT CURRENT_DATE,
    deleted_at date,
    created_by integer NOT NULL DEFAULT 1,
    updated_by integer NOT NULL DEFAULT 1,
    deleted_by integer,
    hotel_id integer NOT NULL,
    room_id integer NOT NULL,
    status "char",
    season_id bigint,
    pansion_id bigint,
    CONSTRAINT reservations_pkey PRIMARY KEY (id)
);

INSERT INTO reservations (guest_citizen_id, guest_full_name, guest_email, guest_phone, check_in, check_out, adult_count, child_count, price, hotel_id, room_id, status, season_id, pansion_id) VALUES ( '12345678901', 'Recaizade Mahmut Ekrem', '', '', '2024-02-01', '2024-02-05', 2, 0, 400, 1, 1, 'RESERVED', 1, 1);
INSERT INTO reservations (guest_citizen_id, guest_full_name, guest_email, guest_phone, check_in, check_out, adult_count, child_count, price, hotel_id, room_id, status, season_id, pansion_id) VALUES ( '12345678902', 'Orhan Veli Kanık', '', '', '2024-05-01', '2024-05-05', 2, 0, 400, 1, 2, 'RESERVED', 2, 2);
INSERT INTO reservations (guest_citizen_id, guest_full_name, guest_email, guest_phone, check_in, check_out, adult_count, child_count, price, hotel_id, room_id, status, season_id, pansion_id) VALUES ( '12345678903', 'Ahmet Hamdi Tanpınar', '', '', '2024-09-01', '2024-09-05', 2, 0, 400, 1, 3, 'RESERVED', 3, 3);

INSERT INTO reservations (guest_citizen_id, guest_full_name, guest_email, guest_phone, check_in, check_out, adult_count, child_count, price, hotel_id, room_id, status, season_id, pansion_id) VALUES ( '12345678904', 'Orhan Pamuk', '', '', '2024-02-01', '2024-02-05', 1, 0, 200, 2, 4, 'RESERVED', 4, 4);
INSERT INTO reservations (guest_citizen_id, guest_full_name, guest_email, guest_phone, check_in, check_out, adult_count, child_count, price, hotel_id, room_id, status, season_id, pansion_id) VALUES ( '12345678905', 'Yaşar Kemal', '', '', '2024-05-01', '2024-05-05', 1, 0, 200, 2, 5, 'RESERVED', 5, 5);
INSERT INTO reservations (guest_citizen_id, guest_full_name, guest_email, guest_phone, check_in, check_out, adult_count, child_count, price, hotel_id, room_id, status, season_id, pansion_id) VALUES ( '12345678906', 'Nâzım Hikmet', '', '', '2024-09-01', '2024-09-05', 1, 0, 200, 2, 6, 'RESERVED', 6, 6);

INSERT INTO reservations (guest_citizen_id, guest_full_name, guest_email, guest_phone, check_in, check_out, adult_count, child_count, price, hotel_id, room_id, status, season_id, pansion_id) VALUES ( '12345678907', 'Ahmet Hamdi Tanpınar', '', '', '2024-02-01', '2024-02-05', 3, 0, 600, 3, 7, 'RESERVED', 7, 7);
INSERT INTO reservations (guest_citizen_id, guest_full_name, guest_email, guest_phone, check_in, check_out, adult_count, child_count, price, hotel_id, room_id, status, season_id, pansion_id) VALUES ( '12345678908', 'Orhan Veli Kanık', '', '', '2024-05-01', '2024-05-05', 3, 0, 600, 3, 8, 'RESERVED', 8, 8);
INSERT INTO reservations (guest_citizen_id, guest_full_name, guest_email, guest_phone, check_in, check_out, adult_count, child_count, price, hotel_id, room_id, status, season_id, pansion_id) VALUES ( '12345678909', 'Recaizade Mahmut Ekrem', '', '', '2024-09-01', '2024-09-05', 3, 0, 600, 3, 9, 'RESERVED', 9, 9);
