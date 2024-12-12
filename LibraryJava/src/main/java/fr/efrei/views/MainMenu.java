package main.java.fr.efrei.views;

import main.java.fr.efrei.domain.*;
import main.java.fr.efrei.factory.BookBuilder;
import main.java.fr.efrei.factory.LibrarianBuilder;
import main.java.fr.efrei.factory.LoanBuilder;
import main.java.fr.efrei.factory.UserBuilder;
import main.java.fr.efrei.repository.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final BookRepository bookRepository = new BookRepository();
    private static final LoanRepository loanRepository = new LoanRepository();
    private static final UserRepository userRepository = new UserRepository();
    private static final LibrarianRepository librarianRepository = new LibrarianRepository();

    public static void main(String[] args) {
        initializeData();

        Librarian admin = initializeLibrarian();
        librarianRepository.getLibrarians().add(admin);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Welcome to the Library Management System ===");
            Librarian currentLibrarian = authenticateLibrarian(scanner);

            if (currentLibrarian == null) {
                System.out.println("Invalid name or ID. Exiting system.");
                return;
            }

            BookView bookView = new BookView(bookRepository, scanner);
            LoanView loanView = new LoanView(loanRepository, bookRepository, userRepository, scanner);
            UserView userView = new UserView(userRepository, scanner);
            LibrarianView librarianView = new LibrarianView(librarianRepository, scanner);

            runMainMenu(scanner, bookView, loanView, userView, librarianView);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void initializeData() {
        User user1 = new UserBuilder()
                .setId(1)
                .setName("Alice")
                .build();

        User user2 = new UserBuilder()
                .setId(2)
                .setName("Bob")
                .build();

        userRepository.getUsers().addAll(List.of(user1, user2));

        Book book1 = new BookBuilder()
                .setId(1)
                .setTitle("Clean Code")
                .setAuthor("Robert C. Martin")
                .setIsbn("111111111")
                .setCategory("Programming")
                .setPublicationYear(2008)
                .setTotalCopies(5)
                .setAvailableCopies(5)
                .build();

        Book book2 = new BookBuilder()
                .setId(2)
                .setTitle("The Pragmatic Programmer")
                .setAuthor("Andrew Hunt and David Thomas")
                .setIsbn("222222222")
                .setCategory("Programming")
                .setPublicationYear(1999)
                .setTotalCopies(3)
                .setAvailableCopies(3)
                .build();

        Book overdueBook = new BookBuilder()
                .setId(3)
                .setTitle("Design Patterns")
                .setAuthor("Erich Gamma et al.")
                .setIsbn("333333333")
                .setCategory("Software Engineering")
                .setPublicationYear(1994)
                .setTotalCopies(2)
                .setAvailableCopies(1)
                .build();

        bookRepository.getBooks().addAll(List.of(book1, book2, overdueBook));

        Loan overdueLoan = new LoanBuilder()
                .setLoanId(1)
                .setBook(overdueBook)
                .setUser(user1)
                .setLoanDate(new Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 15)))
                .setDueDate(new Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 5)))
                .setStatus(LoanStatus.OVERDUE)
                .build();

        loanRepository.getLoans().add(overdueLoan);
        user1.getBorrowedBooks().add(overdueLoan);
        overdueBook.setAvailableCopies(overdueBook.getAvailableCopies() - 1);
        user1.setFines(userRepository.calculateFine(user1));
    }

    private static Librarian initializeLibrarian() {
        return new LibrarianBuilder()
                .setId(1)
                .setName("admin")
                .build();
    }

    private static void runMainMenu(Scanner scanner, BookView bookView, LoanView loanView, UserView userView, LibrarianView librarianView) {
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

    private static Librarian authenticateLibrarian(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your password/ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            return librarianRepository.findByNameAndId(name, id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
            return null;
        }
    }
}
