CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    username VARCHAR(50),
    email VARCHAR(50),
    password VARCHAR(50),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    updated_at DATE NOT NULL DEFAULT CURRENT_DATE, 
    deleted_at DATE,
    created_by INT NOT NULL DEFAULT 1,
    updated_by INT NOT NULL DEFAULT 1,
    deleted_by INT,
    role VARCHAR(50) DEFAULT 'AGENT'
);


insert into users (id, name, username, email, password, role) values (1, 'Kuray Karaaslan', 'admin', 'admin@kuray.dev', 'admin', 'ADMIN');
insert into users (id, name, username, email, password, role) values (2, 'Enes Karaaslan', 'agent', 'agent@kuray.dev', 'agent', 'AGENT');

create table hotels (
    id SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,

	address_full VARCHAR(50) NOT NULL,
	address_district VARCHAR(50) NOT NULL,
	address_city VARCHAR(50) NOT NULL,
	address_country VARCHAR(50) NOT NULL,

	star_rating INT DEFAULT 3,

	has_car_park BOOLEAN DEFAULT FALSE,
	has_internet BOOLEAN DEFAULT FALSE,
	has_pool BOOLEAN DEFAULT FALSE,
	has_conciege BOOLEAN DEFAULT FALSE,
	has_spa BOOLEAN DEFAULT FALSE,
	has_room_service BOOLEAN DEFAULT FALSE,

	phone VARCHAR(50) NOT NULL,
	website VARCHAR(50) NOT NULL,
	email VARCHAR(50),

    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    updated_at DATE NOT NULL DEFAULT CURRENT_DATE, 
    deleted_at DATE,
    created_by INT NOT NULL DEFAULT 1,
    updated_by INT NOT NULL DEFAULT 1,
    deleted_by INT,

	CONSTRAINT star_rating CHECK (star_rating BETWEEN 1 AND 5),
    CONSTRAINT phone CHECK (phone LIKE '0%'),
    CONSTRAINT website CHECK (website LIKE 'http%'),
    CONSTRAINT email CHECK (email LIKE '%@%')
);

insert into hotels (id, name, address_full, address_district, address_city, address_country, star_rating, has_car_park, has_internet, has_pool, has_conciege, has_spa, has_room_service, phone, website, email) values (1, 'Hotel 1', 'Address 1', 'District 1', 'City 1', 'Country 1', 1, true, true, true, true, true, true, '0123456789', 'http://www.hotel1.com', 'hotel1@hotel1');
insert into hotels (id, name, address_full, address_district, address_city, address_country, star_rating, has_car_park, has_internet, has_pool, has_conciege, has_spa, has_room_service, phone, website, email) values (2, 'Hotel 2', 'Address 2', 'District 2', 'City 2', 'Country 2', 2, true, true, true, true, true, true, '0123456789', 'http://www.hotel2.com', 'hotel2@hotel2');
insert into hotels (id, name, address_full, address_district, address_city, address_country, star_rating, has_car_park, has_internet, has_pool, has_conciege, has_spa, has_room_service, phone, website, email) values (3, 'Hotel 3', 'Address 3', 'District 3', 'City 3', 'Country 3', 3, true, true, true, true, true, true, '0123456789', 'http://www.hotel3.com', 'hotel3@hotel3');
insert into hotels (id, name, address_full, address_district, address_city, address_country, star_rating, has_car_park, has_internet, has_pool, has_conciege, has_spa, has_room_service, phone, website, email) values (4, 'Hotel 4', 'Address 4', 'District 4', 'City 4', 'Country 4', 4, true, true, true, true, true, true, '0123456789', 'http://www.hotel4.com', 'hotel4@hotel4');


create table pansions (
    id SERIAL PRIMARY KEY,
    
    name VARCHAR(50) DEFAULT 'BED_AND_BREAKFAST', --BED_AND_BREAKFAST, HALF_BOARD, FULL_BOARD, ALL_INCLUSIVE, ULTRA_ALL_INCLUSIVE, BED_ONLY , ALL_INCLUSIVE_NO_ALCOHOL

    breakfast BOOLEAN DEFAULT true,
    lunch BOOLEAN DEFAULT false,
    dinner BOOLEAN DEFAULT false,
    midnight_snack BOOLEAN DEFAULT false,
    soft_drinks BOOLEAN DEFAULT false,
    alcoholic_drinks BOOLEAN DEFAULT false,
    snacks BOOLEAN DEFAULT false,

    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    updated_at DATE NOT NULL DEFAULT CURRENT_DATE, 
    deleted_at DATE,
    created_by INT NOT NULL DEFAULT 1,
    updated_by INT NOT NULL DEFAULT 1,
    deleted_by INT,

    hotel_id INT,

    FOREIGN KEY (hotel_id) REFERENCES hotels(id),
    CONSTRAINT type CHECK (name IN ('BED_AND_BREAKFAST', 'HALF_BOARD', 'FULL_BOARD', 'ALL_INCLUSIVE', 'ULTRA_ALL_INCLUSIVE', 'BED_ONLY' , 'ALL_INCLUSIVE_NO_ALCOHOL'))
);

insert into pansions (id, name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) values (1, 'BED_AND_BREAKFAST', true, false, false, false, false, false, false, 1);
insert into pansions (id, name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) values (2, 'HALF_BOARD', true, true, false, false, false, false, false, 1);
insert into pansions (id, name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) values (3, 'FULL_BOARD', true, true, true, false, false, false, false, 1);
insert into pansions (id, name, breakfast, lunch, dinner, midnight_snack, soft_drinks, alcoholic_drinks, snacks, hotel_id) values (4, 'ALL_INCLUSIVE', true, true, true, true, true, true, true, 1);

CREATE TABLE seasons (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) DEFAULT 'LOW' CHECK (name IN ('LOW', 'MID', 'HIGH')),
    start_date DATE NOT NULL CHECK (start_date < end_date),
    end_date DATE NOT NULL CHECK (end_date > start_date),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    updated_at DATE NOT NULL DEFAULT CURRENT_DATE, 
    deleted_at DATE,
    created_by INT NOT NULL DEFAULT 1,
    updated_by INT NOT NULL DEFAULT 1,
    deleted_by INT,
    hotel_id INT,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id)
);

insert into seasons (id, name, start_date, end_date, hotel_id) values (1, 'LOW', '2020-01-01', '2020-03-31', 1);
insert into seasons (id, name, start_date, end_date, hotel_id) values (2, 'MID', '2020-04-01', '2020-06-30', 1);
insert into seasons (id, name, start_date, end_date, hotel_id) values (3, 'HIGH', '2020-07-01', '2020-09-30', 1);

create table rooms (
    id SERIAL PRIMARY KEY,
    
    room_number VARCHAR(50) NOT NULL,
    type VARCHAR(50) DEFAULT 'DOUBLE', --SINGLE, DOUBLE, JUNIOR_SUITE, SUITE
    
    double_bed_count INT DEFAULT 0,
    single_bed_count INT DEFAULT 0,

    adult_price DECIMAL(10,2) DEFAULT 0,
    child_price DECIMAL(10,2) DEFAULT 0,

    square_meters INT DEFAULT 0,

    has_television BOOLEAN DEFAULT true,
    has_balcony BOOLEAN DEFAULT false,
    has_air_conditioning BOOLEAN DEFAULT false,
    has_minibar BOOLEAN DEFAULT false,
    has_valuables_safe BOOLEAN DEFAULT false,
    has_gaming_console BOOLEAN DEFAULT false,
    has_projector BOOLEAN DEFAULT false,

    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    updated_at DATE NOT NULL DEFAULT CURRENT_DATE, 
    deleted_at DATE,
    created_by INT NOT NULL DEFAULT 1,
    updated_by INT NOT NULL DEFAULT 1,
    deleted_by INT,

    hotel_id INT NOT NULL,
    season_id INT NOT NULL,

    FOREIGN KEY (hotel_id) REFERENCES hotels(id),
    FOREIGN KEY (season_id) REFERENCES seasons(id),
    CONSTRAINT double_bed_count CHECK (double_bed_count >= 0),
    CONSTRAINT single_bed_count CHECK (single_bed_count >= 0),
    CONSTRAINT adult_price CHECK (adult_price >= 0),
    CONSTRAINT child_price CHECK (child_price >= 0),
    CONSTRAINT square_meters CHECK (square_meters >= 0),
    CONSTRAINT type CHECK (type IN ('SINGLE', 'DOUBLE', 'JUNIOR_SUITE', 'SUITE'))
);

insert into rooms (id, room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id) values (1, '101', 'SINGLE', 0, 1, 100, 50, 20, true, false, false, false, false, false, false, 1, 1);
insert into rooms (id, room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id) values (2, '102', 'DOUBLE', 1, 0, 200, 100, 30, true, false, false, false, false, false, false, 1, 1);
insert into rooms (id, room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id) values (3, '103', 'JUNIOR_SUITE', 1, 1, 300, 150, 40, true, false, false, false, false, false, false, 1, 1);
insert into rooms (id, room_number, type, double_bed_count, single_bed_count, adult_price, child_price, square_meters, has_television, has_balcony, has_air_conditioning, has_minibar, has_valuables_safe, has_gaming_console, has_projector, hotel_id, season_id) values (4, '104', 'SUITE', 2, 0, 400, 200, 50, true, false, false, false, false, false, false, 1, 1);


CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,
    guest_citizen_id VARCHAR(50),
    guest_full_name VARCHAR(50) NOT NULL,
    guest_email VARCHAR(50) NOT NULL,
    guest_phone VARCHAR(50) NOT NULL,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    adult_count INT NOT NULL,
    child_count INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    updated_at DATE NOT NULL DEFAULT CURRENT_DATE,
    deleted_at DATE,
    created_by INT NOT NULL DEFAULT 1,
    updated_by INT NOT NULL DEFAULT 1,
    deleted_by INT,
    hotel_id INT NOT NULL,
    room_id INT NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED', 'CANCELED', 'COMPLETED', 'CHECKED_IN', 'CHECKED_OUT')),
    FOREIGN KEY (hotel_id) REFERENCES hotels(id),
    FOREIGN KEY (room_id) REFERENCES rooms(id),
    CONSTRAINT guest_email CHECK (guest_email LIKE '%@%'),
    CONSTRAINT guest_phone CHECK (guest_phone LIKE '0%'),
    CONSTRAINT check_in CHECK (check_in < check_out),
    CONSTRAINT check_out CHECK (check_out > check_in),
    CONSTRAINT adult_count CHECK (adult_count > 0),
    CONSTRAINT child_count CHECK (child_count >= 0),
    CONSTRAINT price CHECK (price >= 0)
);

-- Sample insert
INSERT INTO reservations (id, guest_citizen_id, guest_full_name, guest_email, guest_phone, check_in, check_out, adult_count, child_count, price, hotel_id, room_id)
VALUES (1, '12345678901', 'Kuray Karaaslan', 'kuray@kuray.dev', '0123456789', '2020-01-01', '2020-01-02', 2, 0, 100, 1, 1);
