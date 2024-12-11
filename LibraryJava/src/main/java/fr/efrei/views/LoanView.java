package fr.efrei.Views;
import main.java.fr.efrei.repository.BookRepository;

import java.util.Scanner;

public class LoanView {
    //att
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final Scanner scanner;

    //constr
    public LoanView(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository, Scanner scanner) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.scanner = scanner;
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n=== Loan Management ===");
                System.out.println("1. Borrow a book");
                System.out.println("2. Renew Loan");
                System.out.println("3. Return a book");
                System.out.println("4. View overdue loans");
                System.out.println("5. Make a reservation");
                System.out.println("6. Display Loan Dashboard");
                System.out.println("7. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    //create
                    case 1 -> loanRepository.createLoan(bookRepository, userRepository);
                    //renew
                    case 2 -> {
                        System.out.print("Enter Loan ID to renew: ");
                        int loanId = scanner.nextInt();

                        System.out.print("Enter number of extra days to extend: ");
                        int extraDays = scanner.nextInt();

                        loanRepository.renewLoan(loanId, extraDays);
                    }
                    //return
                    case 3 -> {
                        System.out.print("Enter the loan ID to return the book: ");
                        int loanId = scanner.nextInt();
                        scanner.nextLine();
                        loanRepository.returnBook(loanId);
                    }
                    //view overdue Loans for a user
                    case 4 -> {
                        System.out.print("Enter the user ID to view overdue loans: ");
                        int userId = scanner.nextInt();
                        scanner.nextLine();
                        loanRepository.showOverdueLoansByUser(userId);
                    }
                    //reserve a book for later
                    case 5 -> {
                        System.out.print("Enter Book ID to reserve: ");
                        int bookId = scanner.nextInt();

                        System.out.print("Enter User ID who wants to reserve: ");
                        int userId = scanner.nextInt();

                        loanRepository.reserveBook(bookRepository, userRepository, bookId, userId);
                    }
                    //show display dashboard
                    case 6 -> {
                        loanRepository.displayDashboard();
                    }
                    //exit
                    case 7 -> {
                        System.out.println("Returning to main menu.");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
            }
        }
    }
}