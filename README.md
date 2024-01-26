![Travel Agency](/static/logo.jpg)

# Travel Agency
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13.3-blue.svg)](https://www.postgresql.org/download/)
[![Swing](https://img.shields.io/badge/Swing-1.0.0-green.svg)](https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html)

## Description

This project aims to create a digital management system for Patika Tourism Agency to streamline its daily operations in the hotel sector and optimize customer reservation processes.

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
## Installation

### Clone

1. Clone the repository: `git clone https://github.com/kuraykaraaslan/TravelAgency.git`
2. Navigate to the project directory: `cd TravelAgency`
3. Open the project in your IDE
4. Create a database named `travelagency` in PostgreSQL
5. Run the `travelagency.sql` file in the `src/main/resources` folder to create the tables
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

## Contributing

Contributions are welcome! Please follow the [contribution guidelines](CONTRIBUTING.md).

## License

This project is licensed under the [MIT License](LICENSE).