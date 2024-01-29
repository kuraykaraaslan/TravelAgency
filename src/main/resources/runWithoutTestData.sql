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
