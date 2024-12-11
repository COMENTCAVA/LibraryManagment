package fr.efrei.Views;
import fr.efrei.Repository.BookRepository;
import fr.efrei.domain.Book;

import java.util.Scanner;

public class BookView {
    //attributes
    private final BookRepository bookRepository;
    private final Scanner scanner;
    //constructor

    public BookView(BookRepository bookRepository, Scanner scanner) {
        this.bookRepository = bookRepository;
        this.scanner = scanner;
    }

    public void displayMenu() {
        while (true) {
            try {
                //menu
                System.out.println("\n=== Book Management ===");
                System.out.println("1. Add a book");
                System.out.println("2. Delete a book");
                System.out.println("3. View all books");
                System.out.println("4. Search for a book by ID");
                System.out.println("5. Update a book");
                System.out.println("6. Search a book by category");
                System.out.println("7. View book stats");
                System.out.println("8. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consumes leftover newline

                switch (choice) {
                    //add
                    case 1 -> bookRepository.add();
                    //delete
                    case 2 -> {
                        System.out.print("Enter the ID of the book to delete: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        bookRepository.delete(id);
                    }
                    //view all
                    case 3 -> bookRepository.showAll();
                    //search by id
                    case 4 -> {
                        System.out.print("Enter the book ID to search: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        Book book = bookRepository.findById(id);
                        System.out.println(book != null ? book : "Book not found.");
                    }
                    //update
                    case 5 -> {
                        System.out.print("Enter the ID of the book to update: ");
                        int bookId = scanner.nextInt();
                        scanner.nextLine(); // Consume leftover newline

                        System.out.println("Which field do you want to update?");
                        System.out.println("1. Title");
                        System.out.println("2. Author");
                        System.out.println("3. Category");
                        System.out.println("4. Total Copies");
                        System.out.print("Enter your choice: ");
                        int fieldChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume leftover newline

                        //switch for searching by category
                        switch (fieldChoice) {
                            case 1 -> {
                                System.out.print("Enter new title: ");
                                String newTitle = scanner.nextLine();
                                bookRepository.updateBook(bookId, newTitle, null, null, -1);
                            }
                            case 2 -> {
                                System.out.print("Enter new author: ");
                                String newAuthor = scanner.nextLine();
                                bookRepository.updateBook(bookId, null, newAuthor, null, -1);
                            }
                            case 3 -> {
                                System.out.print("Enter new category: ");
                                String newCategory = scanner.nextLine();
                                bookRepository.updateBook(bookId, null, null, newCategory, -1);
                            }
                            case 4 -> {
                                System.out.print("Enter new total copies: ");
                                int newTotalCopies = scanner.nextInt();
                                bookRepository.updateBook(bookId, null, null, null, newTotalCopies);
                            }
                            default -> { System.out.println("Invalid choice.");
                        }
                    }
                    }
                    //search by categ
                    case 6 -> {
                        System.out.println("Search by:");
                        System.out.println("1. Title");
                        System.out.println("2. Author");
                        System.out.println("3. Category");
                        System.out.println("4. Availability");
                        System.out.print("Enter your choice: ");
                        int searchChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume leftover newline

                        String title = null, author = null, category = null;
                        Boolean available = null;

                        //switch for searching by category
                        switch (searchChoice) {
                            case 1 -> {
                                System.out.print("Enter title: ");
                                title = scanner.nextLine();
                            }
                            case 2 -> {
                                System.out.print("Enter author: ");
                                author = scanner.nextLine();
                            }
                            case 3 -> {
                                System.out.print("Enter category: ");
                                category = scanner.nextLine();
                            }
                            case 4 -> {
                                System.out.print("Available (true/false): ");
                                available = scanner.nextBoolean();
                            }
                            default -> {
                                System.out.println("Invalid choice.");
                                return;
                            }
                        }

                        var results = bookRepository.searchBooks(title, author, category, available);
                        if (results.isEmpty()) {
                            System.out.println("No books found matching the criteria.");
                        } else {
                            System.out.println("Search results:");
                            results.forEach(System.out::println);
                        }
                    }
                    //display book stats
                    case 7 -> bookRepository.displayBookStatistics();
                    //exit
                    case 8 -> {
                        System.out.println("Returning to main menu.");
                        return;
                    }

                    default -> System.out.println("Invalid option. Please try again.");
                }
            }//end of try
            catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear the invalid input
            }//end of catch
        }
    }
}