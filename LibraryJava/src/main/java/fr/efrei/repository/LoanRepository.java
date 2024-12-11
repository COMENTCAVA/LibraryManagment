package main.java.fr.efrei.repository;

import main.java.fr.efrei.domain.Book;
import main.java.fr.efrei.domain.Loan;
import main.java.fr.efrei.domain.User;

import java.util.*;

public class LoanRepository implements ILoanRepository {
    //attributes
    private List<Loan> loans = new ArrayList<>();
    private UserRepository userRepository;
    private int currentLoanId = 1;
    private Map<Integer, List<Integer>> reservations = new HashMap<>();

    //getters
    public List<Loan> getLoans() {
        return loans;
    }

    public int getCurrentLoanId() {
        return currentLoanId;
    }



    //create
    @Override
    public void createLoan(BookRepository bookRepo, UserRepository userRepo) {
        Scanner scanner = new Scanner(System.in);

        //user prompt
        System.out.println("Enter the book ID you want to borrow:");
        int bookId = scanner.nextInt();

        Book book = bookRepo.findById(bookId);
        if (book.getAvailableCopies() <= 0) {
            System.out.println("The book is not available for borrowing.");

            //ask for reservation
            System.out.println("Do you want to reserve it for when it is available? (yes/no)");
            String response = scanner.next();

            if (response.equalsIgnoreCase("yes")) {
                System.out.println("Enter your user ID:");
                int userId = scanner.nextInt();

                User user = userRepo.findById(userId);
                if (user == null) {
                    System.out.println("User not found. Reservation cannot be completed.");
                    return;
                }

                reserveBook(bookRepo, userRepo, bookId, userId);
            }
            else {
                System.out.println("No reservation made.");
            }
            return;
        }// end of possible reservation


        System.out.println("Enter user ID:");
        int userId = scanner.nextInt();

        User user = userRepo.findById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        Loan newLoan = new Loan();
        newLoan.setLoanId(currentLoanId++);
        newLoan.setBook(book);
        newLoan.setUser(user);
        newLoan.setLoanDate(new Date());

        //setting due date at 2 weeks after reservation as an example
        long twoWeeksInMillis = 1000L * 60 * 60 * 24 * 14; // 2 weeks
        newLoan.setDueDate(new Date(System.currentTimeMillis() + twoWeeksInMillis));
        newLoan.setStatus("active");

        //update book/user infos
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        loans.add(newLoan);
        book.incrementLoanCount();
        user.getBorrowedBooks().add(newLoan);

        System.out.println("Loan created successfully. Book borrowed until: " + newLoan.getDueDate());
    }

    //return a book
    @Override
    public void returnBook(int loanId) {
        // find by id
        Loan loan = findById(loanId);

        // verifying if loan does exist
        if (loan == null) {
            System.out.println("Loan not found.");
            return;
        }

        if ("overdue".equals(loan.getStatus())) {
            System.out.println("User " + loan.getUser() + " need to pay a fine of " + loan.getUser().getFines());
        }

        if ("active".equals(loan.getStatus())) {
            System.out.println("Loan already returned.");
            return;
        }

        // update loan
        loan.setStatus("returned");
        loan.setReturnDate(new Date());

        // update the book
        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        // delete loan from user's list of borrowed books
        User user = loan.getUser();
        if (user.getBorrowedBooks().remove(loan)) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Error: Loan not found in user's borrowed books.");
        }
    }


    @Override
    public Loan findById(int loanId) {
        return loans.stream()
                .filter(loan -> loan.getLoanId() == loanId)
                .findFirst()
                .orElse(null);
    }

    //look for user overdue books
    @Override
    public void showOverdueLoansByUser(int userId) {
        loans.stream()
                .filter(loan -> loan.getUser().getId() == userId && "overdue".equals(loan.getStatus()))
                .forEach(loan -> System.out.println("Loan ID: " + loan.getLoanId() +
                        ", Book: " + loan.getBook().getTitle() +
                        ", Due Date: " + loan.getDueDate()));
    }

    //renewing
    @Override
    public void renewLoan(int loanId, int extraDays) {
        //search for the loan
        Loan loan = findById(loanId);
        if (loan == null || !"active".equals(loan.getStatus())) {
            System.out.println("Loan not found or is not active.");
            return;
        }

        //update dueDate
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(loan.getDueDate());
        calendar.add(Calendar.DAY_OF_YEAR, extraDays);
        loan.setDueDate(calendar.getTime());

        System.out.println("Loan renewed successfully. New due date: " + loan.getDueDate());
    }

    //display books for which a request has been made
    @Override
    public void displayDashboard() {
        System.out.println("\n=== Loan Dashboard ===");

        // books currently borrowed
        System.out.println("\nCurrently Borrowed Books:");
        loans.stream()
                .filter(loan -> "active".equals(loan.getStatus()))
                .forEach(loan -> System.out.println("Loan ID: " + loan.getLoanId() +
                        ", Book: " + loan.getBook().getTitle() +
                        ", Borrowed by: " + loan.getUser().getName()));

        // late books
        System.out.println("\nOverdue Books:");
        loans.stream()
                .filter(loan -> "overdue".equals(loan.getStatus()) &&
                        loan.getDueDate().before(new Date()))
                .forEach(loan -> System.out.println("Loan ID: " + loan.getLoanId() +
                        ", Book: " + loan.getBook().getTitle() +
                        ", Borrowed by: " + loan.getUser().getName() +
                        ", Due Date: " + loan.getDueDate()));

        // reserved books
        System.out.println("\nFuture Reservations:");
        reservations.forEach((userId, bookIds) -> {
            System.out.println("User ID: " + userId);
            bookIds.forEach(bookId -> System.out.println("Reserved Book ID: " + bookId));
        });
    }

    //reservation
    @Override
    public void reserveBook(BookRepository bookRepo, UserRepository userRepo, int bookId, int userId) {
        Book book = bookRepo.findById(bookId);
        User user = userRepo.findById(userId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        reservations.computeIfAbsent(userId, k -> new ArrayList<>()).add(bookId);
        System.out.println("Book reserved successfully for user: " + user.getName());
    }

}
