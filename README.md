![Travel Agency](/static/logo.jpg)

# Travel Agency
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13.3-blue.svg)](https://www.postgresql.org/download/)

## Description

This project aims to create a digital management system for Patika Tourism Agency to streamline its daily operations in the hotel sector and optimize customer reservation processes.


## Table of Contents

- [Description](#description)
- [Technologies](#technologies)
- [Project Overview](#project-overview)
- [Features](#features)
- [Installation](#installation)
    - [Clone](#clone)
    - [Permission Table](#permission-table)
- [Database Design](#database-design)
- [Coding Practices](#coding-practices)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)



## Technologies

- Java 21
- Swing Framework
- PostgreSQL

## Project Overview

The project consists of 3 main parts: **Hotel Management**, **User Management** and **Reservation Management**.

## Features

- **User Management:**
    - Admin can add, delete, update, and filter users based on their roles (admin, staff).

- **Hotel Management:**
    - List hotels with details such as name, address, star rating, and amenities.
    - Add new hotels with information like email, phone, and location.
    - Specify accommodation types (pension types) offered by each hotel.

- **Room Management:**
    - List rooms for each hotel with details like type, features, and availability.
    - Add new rooms, specifying type, features, and stock quantity.

- **Season Management:**
    - Define historical seasons to adjust room pricing based on periods.
    - Specify start and end dates for each season.

- **Pricing Management:**
    - Set dynamic pricing for rooms based on seasons and accommodation types.
    - Differentiate prices for adults and children.

- **Room Search:**
    - Search available rooms based on city, date range, and hotel name.
    - Display relevant information about hotels and rooms that match the search criteria.

- **Reservation Operations:**
    - List existing reservations with details like check-in/out dates, guests, and total price.
    - Add new reservations by selecting available rooms and providing guest information.
    - Delete or update existing reservations.
- **Security:**
    - Users are required to log in to the system with their credentials.
    - 
## Installation

### Clone

1. Clone the repository: `git clone https://github.com/kuraykaraaslan/TravelAgency.git`
2. Navigate to the project directory: `cd TravelAgency`
3. Open the project in your IDE
4. Create a database named `agency` in PostgreSQL or change the database name in the `src/main/java/agency/core/DataBase.java` file
5. Run the `runWithTestData.sql` file in the `src/main/resources` folder to create the tables or run the `runWithoutTestData.sql` file to create the tables without test data

6. Run the project

### Permission Table

### Admin

- User Management: Admin can list, add, delete, update, and filter users based on their roles (admin, staff).

### Employee (Agency Staff)

- Hotel Management: List and add hotels.
- Room Management: List and add rooms.
- Season Management: List and add seasons.
- Pricing Management.
- Room Search.
- Reservation Operations: List, add, delete, and update reservations.


## Database Design

The system uses a relational database with the following tables:

- `user`: Stores user information.
- `hotel`: Stores hotel information.
- `season`: Stores seasonal information for hotels.
- `pension`: Stores pension types for hotels.
- `room`: Stores room information.
- `reservation`: Stores reservation details.

The project uses Swing for the graphical user interface, providing a user-friendly experience for both admins and employees.

### Coding Practices

- Variable and function names are clear and self-explanatory.
- Code readability and formatting are maintained with proper indentation.
- Comments and documentation are provided for better understanding.
- The code is modular and reusable.

## Screenshots

Include screenshots here to visually showcase the application's interface and features.

![Welcome](/static/welcome.png)
![All Rooms](/static/all_rooms.png)
![All Reservations](/static/all_reservations.png)
![Reservation Details](/static/reservation_details.png)
![All Hotels](/static/all_hotels.png)
![Hotel Details](/static/hotel_details.png)
![Hotel Assets](/static/hotel_assets.png)
![Hotel Rooms](/static/hotel_rooms.png)
![Hotel Seasons](/static/hotel_seasons.png)
![Hotel Pensions](/static/hotel_pensions.png)
![Hotel Reservations](/static/hotel_reservations.png)


## Change Log

- **v1.0.0** (2024-02-01)
    - Initial release
- **v1.0.1** (2024-02-02)
    - Added room search feature as RoomListView.
    - Added links to hotel details from the reservation details
    - Added links to room details from the reservation details
    - Added links to season details from the reservation details
    - Added availability check for rooms in the reservation details
    - Added Role search feature as UserListView
    - Bug Fixed: Pansion selection for the room added



## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is a learning project developed under the guidance of [Patika.dev](https://www.patika.dev/). If you having same route with me, you can use this project as a reference.
