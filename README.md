# Library Management System

Welcome to the **Library Management System**, a Java-based application designed to handle library operations efficiently. This project allows librarians to manage books, users, loans, and reservations through a clean and modular structure, offering advanced functionality to streamline library operations.

## Features

### Book Management
- Add, update, delete, and list books.
- Advanced search by title, author, category, and availability.
- View statistics on book availability and borrowing trends.

### User Management
- Add, update, delete, and list users.
- Calculate user fines based on overdue books.
- Display loan history and overdue notifications.
- Check remaining books a user can borrow.

### Loan Management
- Borrow and return books.
- Renew loans with extended due dates.
- Handle overdue loans and display associated fines.
- Reserve unavailable books.
- View loan dashboard for active, overdue, and reserved books.

### Librarian Management
- Add, update, delete, and list librarians.
- Authenticate librarians for secure access to the system.

## Project Structure
The project is organized into the following main packages:

### `main.java.fr.efrei.domain`
Contains core entity classes:
- `Book`
- `User`
- `Loan`
- `Librarian`

### `main.java.fr.efrei.factory`
Builders for creating domain objects with validation logic:
- `BookBuilder`
- `UserBuilder`
- `LoanBuilder`
- `LibrarianBuilder`

### `main.java.fr.efrei.repository`
Repositories for managing entities and providing CRUD operations:
- `BookRepository`
- `UserRepository`
- `LoanRepository`
- `LibrarianRepository`

### `main.java.fr.efrei.views`
Views for interacting with the system through a console interface:
- `MainMenu`
- `BookView`
- `UserView`
- `LoanView`
- `LibrarianView`

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or later.
- A terminal or IDE with support for Java projects.

### Installation
1. Clone this repository:
   ```bash
   git clone https://github.com/Candicemamou/LibraryManagment.git
   ```
2. Open the project in your preferred IDE.
3. Compile and run the `MainMenu` class to start the application.

### Usage
- Authenticate using a librarian account to access the main menu.
- Use the respective menus to manage books, users, loans, and librarians.

## Contribution
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push them to your fork.
4. Open a pull request.

## Contact
For questions or feedback, please contact:
- CandiceMamou: [candice.mamou@gmail.com](mailto:candice.mamou@gmail.com)
- [GitHub Repository](https://github.com/Candicemamou/LibraryManagment.git)
