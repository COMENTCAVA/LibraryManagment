package main.java.fr.efrei.repository;

import fr.efrei.Factory.UserBuilder;
import fr.efrei.domain.User;
import fr.efrei.Factory.UserBuilder;
import fr.efrei.domain.Loan;
import fr.efrei.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRepository implements GeneralRepository<User>, IUserRepository {
    //attributes
    private List<User> users = new ArrayList<>();

    private static final double FINE_PER_DAY = 1.0; // 1 rand per day as an example
    public List<User> getUsers() {
        return users;
    }

    //methods from gen repository
    @Override
    public void add() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter user ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne vide

        System.out.println("Enter user name:");
        String name = scanner.nextLine();

        User newUser = new UserBuilder()


                .setId(id)
                .setName(name)
                .build();

        users.add(newUser);
        System.out.println("User added successfully.");
    }

    @Override
    public void delete(int id) {
        if (users.removeIf(user -> user.getId() == id)) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    @Override
    public User findById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void showAll() {
        for (User user : users) {
            System.out.println(user.getName() + " (ID: " + user.getId() + ")");
        }
    }
    //calculate fine
    @Override
    public double calculateFine(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        double fine = 0.0;
        for (Loan loan : user.getBorrowedBooks()) {
            if ("overdue".equals(loan.getStatus()) && loan.getReturnDate() == null) {
                long overdueDays = (System.currentTimeMillis() - loan.getDueDate().getTime()) / (1000 * 60 * 60 * 24);
                fine += Math.max(overdueDays, 0) * FINE_PER_DAY;
            }
        }
        user.setFines(fine);
        return fine;
    }

    //display overdue books for a user
    @Override
    public void displayOverdueBooks(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        user.getBorrowedBooks().stream()
                .filter(loan -> "overdue".equals(loan.getStatus()))
                .forEach(loan -> System.out.println("Book: " + loan.getBook().getTitle() + ", Due Date: " + loan.getDueDate()));
    }

    //update user details
    @Override
    public void updateUser(int userId, String newName) {
        User user = findById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        if (newName != null && !newName.isEmpty()) {
            user.setName(newName);
        }

        System.out.println("User information updated successfully: " + user);
    }

    //loan history
    @Override
    public void showUserLoanHistory(int userId) {
        User user = findById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Loan History for User: " + user.getName() + " (ID: " + user.getId() + ")");
        for (Loan loan : user.getBorrowedBooks()) {
            System.out.println("\nBook: " + loan.getBook().getTitle() +
                    "\n Loan Date: " + loan.getLoanDate() +
                    "\n Due Date: " + loan.getDueDate() +
                    "\n Status: " + loan.getStatus() +
                    (loan.getReturnDate() != null ? "\n Return Date: " + loan.getReturnDate() : ""));
        }
    }
    //calculate the number of books user can still borrow
    @Override
    public int remainingBooksToBorrow(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        int maxAllowedBooks = 5; // Exemple : un utilisateur peut emprunter jusqu'Ã  5 livres
        int currentlyBorrowed = (int) user.getBorrowedBooks().stream()
                .filter(loan -> "active".equals(loan.getStatus()))
                .count();

        return maxAllowedBooks - currentlyBorrowed;
    }
    //display possible books to borrow
    @Override
    public void showRemainingBooksToBorrow(int userId) {
        User user = findById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        int remaining = remainingBooksToBorrow(user);
        System.out.println("User " + user.getName() + " can borrow " + remaining + " more books.");
    }

    //alerts for user
    @Override
    public void sendOverdueNotifications(User user) {
        for (Loan loan : user.getBorrowedBooks()) {
            if ("overdue".equals(loan.getStatus())) {
                System.out.println("Notification: User " + user.getName() +
                        " has an overdue book: " + loan.getBook().getTitle() +
                        " (Due Date: " + loan.getDueDate() + ").");
            }
        }
    }
    @Override
    public void alertUnreturnedBooks(User user) {
        for (Loan loan : user.getBorrowedBooks()) {
            if ("active".equals(loan.getStatus())) {
                System.out.println("Alert: User " + user.getName() +
                        " has not returned the book: " + loan.getBook().getTitle() +
                        " (Due Date: " + loan.getDueDate() + ").");
            }
        }
    }
}
