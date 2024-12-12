package main.java.fr.efrei.views;

import main.java.fr.efrei.domain.User;
import main.java.fr.efrei.repository.UserRepository;

import java.util.Scanner;

public class UserView {
    //att
    private final UserRepository userRepository;
    private Scanner scanner;
    //constr

    public UserView(UserRepository userRepository, Scanner scanner) {
        this.userRepository = userRepository;
        this.scanner = scanner;
    }

    public void displayMenu() {
        scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== User Management ===");
            System.out.println("1. Add a user");
            System.out.println("2. Delete a user");
            System.out.println("3. View all users");
            System.out.println("4. Search for a user by ID");
            System.out.println("5. Search for a user by name");
            System.out.println("6. Calculate user's fines");
            System.out.println("7. Update user details");
            System.out.println("8. Show User Loan History");
            System.out.println("9. Show number of loan still available");
            System.out.println("10. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                //add
                case 1 -> userRepository.add();
                //delete
                case 2 -> {
                    System.out.print("Enter the ID of the user to delete: ");
                    int id = scanner.nextInt();
                    userRepository.delete(id);
                }
                //display all
                case 3 -> {
                    System.out.println("\n=== List of Users ===");
                    userRepository.showAll();
                }
                //search by Id
                case 4 -> {
                    System.out.print("Enter the user ID to search: ");
                    int id = scanner.nextInt();
                    User user = userRepository.findById(id);
                    System.out.println(user != null ? user : "User not found.");
                    assert user != null;
                    userRepository.alertUnreturnedBooks(user);
                    userRepository.sendOverdueNotifications(user);
                }
                //search by name
                case 5 -> {
                    System.out.print("Enter the user name to search: ");
                    String name = scanner.next();
                    User user = userRepository.findByName(name);
                    System.out.println(user != null ? user : "User not found.");
                    assert user != null;
                    userRepository.alertUnreturnedBooks(user);
                    userRepository.sendOverdueNotifications(user);
                }
                //calculate user fine
                case 6 -> {
                    System.out.print("Enter the user ID to calculate fines: ");
                    int id = scanner.nextInt();
                    var user = userRepository.findById(id);
                    if (user != null) {
                        double fine = userRepository.calculateFine(user);
                        System.out.println("User " + user.getName() + " has a total fine of: " + fine + "$");
                    } else {
                        System.out.println("User not found.");
                    }
                }
                //update user
                case 7 -> {
                    System.out.print("Enter User ID to update: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter new name for the user (leave blank to keep current name): ");
                    String newName = scanner.nextLine();
                    userRepository.updateUser(userId, newName);
                }
                //display loan history
                case 8 -> {
                    System.out.print("Enter User ID to view loan history: ");
                    int userId = scanner.nextInt();

                    userRepository.showUserLoanHistory(userId);
                }
                //check number of loan still available
                case 9 -> {
                    System.out.print("Enter User ID to check remaining books: ");
                    int userId = scanner.nextInt();

                    userRepository.showRemainingBooksToBorrow(userId);
                }
                //exit
                case 10 -> {
                    System.out.println("Returning to main menu.");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}