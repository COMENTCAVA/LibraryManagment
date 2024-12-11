package main.java.fr.efrei.views;

import main.java.fr.efrei.repository.LibrarianRepository;

import java.util.Scanner;
public class LibrarianView {
    private final LibrarianRepository librarianRepository;
    private final Scanner scanner;

    public LibrarianView(LibrarianRepository librarianRepository, Scanner scanner) {
        this.librarianRepository = librarianRepository;
        this.scanner = scanner;
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n=== Librarian Management ===");
                System.out.println("1. Add Librarian");
                System.out.println("2. Delete Librarian");
                System.out.println("3. Find Librarian by ID");
                System.out.println("4. Show All Librarians");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear newline

                switch (choice) {
                    case 1 -> librarianRepository.add();
                    case 2 -> {
                        System.out.print("Enter Librarian ID to delete: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        librarianRepository.delete(id);
                    }
                    case 3 -> {
                        System.out.print("Enter Librarian ID to find: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        var librarian = librarianRepository.findById(id);
                        System.out.println(librarian != null ? librarian : "Librarian not found.");
                    }
                    case 4 -> librarianRepository.showAll();
                    case 5 -> {
                        System.out.println("Returning to main menu.");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}