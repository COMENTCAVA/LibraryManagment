package main.java.fr.efrei.views;
import main.java.fr.efrei.domain.*;
import main.java.fr.efrei.repository.BookRepository;
import main.java.fr.efrei.repository.LibrarianRepository;
import main.java.fr.efrei.repository.LoanRepository;
import main.java.fr.efrei.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        //initialization for testing purposes


        // Initialize repositories
        BookRepository bookRepository = new BookRepository();
        LoanRepository loanRepository = new LoanRepository();
        UserRepository userRepository = new UserRepository();
        LibrarianRepository librarianRepository = new LibrarianRepository();

        // Initialize user
        User user1 = new User();
        user1.setId(1);
        user1.setName("Alice");

        User user2 = new User();
        user2.setId(2);
        user2.setName("Bob");

        // add users to rep
        List<User> users = userRepository.getUsers();
        users.add(user1);
        users.add(user2);


        // initialized non borrowed books
        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Clean Code");
        book1.setAuthor("Robert C. Martin");
        book1.setIsbn("111111111");
        book1.setCategory("Programming");
        book1.setPublicationYear(2008);
        book1.setTotalCopies(5);
        book1.setAvailableCopies(5);

        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("The Pragmatic Programmer");
        book2.setAuthor("Andrew Hunt and David Thomas");
        book2.setIsbn("222222222");
        book2.setCategory("Programming");
        book2.setPublicationYear(1999);
        book2.setTotalCopies(3);
        book2.setAvailableCopies(3);

        // initialize book with overdue status
        Book overdueBook = new Book();
        overdueBook.setId(3);
        overdueBook.setTitle("Design Patterns");
        overdueBook.setAuthor("Erich Gamma et al.");
        overdueBook.setIsbn("333333333");
        overdueBook.setCategory("Software Engineering");
        overdueBook.setPublicationYear(1994);
        overdueBook.setTotalCopies(2);
        overdueBook.setAvailableCopies(1); // 1 copy already borrowed for overdue example

        // add books to the rep
        List<Book> books = bookRepository.getBooks();
        books.add(book1);
        books.add(book2);
        books.add(overdueBook);

        // create overdue loan to test fine calculation and rreturn function
        Loan overdueLoan = new Loan();
        overdueLoan.setLoanId(1);
        overdueLoan.setBook(overdueBook);
        overdueLoan.setUser(user1);
        overdueLoan.setLoanDate(new Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 15))); // Emprunté il y a 15 jours
        overdueLoan.setDueDate(new Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 5))); // Retard de 5 jours
        overdueLoan.setStatus(LoanStatus.OVERDUE);


        // add loan to rep
        List<Loan> loans = loanRepository.getLoans();
        loans.add(overdueLoan);

        // update user and book concerned
        user1.getBorrowedBooks().add(overdueLoan);
        overdueBook.setAvailableCopies(overdueBook.getAvailableCopies() - 1);
        double fine = userRepository.calculateFine(user1);
        user1.setFines(fine);
        //end of prompt for data


        // ---------------------GENERAL MENU---------------------
        //initialize librarian
        Librarian admin = new Librarian();
        admin.setId(1);
        admin.setName("admin");
        //add librarian to rep
        List<Librarian> librarians = librarianRepository.getLibrarians();
        librarians.add(admin);

        // Initialize shared scanner
        Scanner scanner = new Scanner(System.in);

        // Authentication step
        System.out.println("=== Welcome to the Library Management System ===");
        Librarian currentLibrarian = authenticateLibrarian(librarianRepository, scanner);

        if (currentLibrarian == null) {
            System.out.println("Invalid name or ID. Exiting system.");
            return;
        }

        // Initialize views
        BookView bookView = new BookView(bookRepository, scanner);
        LoanView loanView = new LoanView(loanRepository, bookRepository, userRepository, scanner);
        UserView userView = new UserView(userRepository, scanner);
        LibrarianView librarianView = new LibrarianView(librarianRepository, scanner);

        // Main menu loop
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Librarian Management");
            System.out.println("2. Book Management");
            System.out.println("3. Loan Management");
            System.out.println("4. User Management");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear newline

            switch (choice) {
                case 1 -> librarianView.displayMenu();
                case 2 -> bookView.displayMenu();
                case 3 -> loanView.displayMenu();
                case 4 -> userView.displayMenu();
                case 5 -> {
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }



    public static Librarian authenticateLibrarian(LibrarianRepository librarianRepository, Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your password/ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
            return null;
        }

        // Recherche du bibliothécaire dans le repository
        return librarianRepository.findByNameAndId(name, id);
    }
}

