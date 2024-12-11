package main.java.fr.efrei.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import main.java.fr.efrei.domain.Book;
import main.java.fr.efrei.factory.BookBuilder;

public class BookRepository implements GeneralRepository<Book>, IBookRepository {
    //List of Books
    private final List<Book> books = new ArrayList<>();
    //getter
    public List<Book> getBooks() {
        return books;
    }

    //functions from gen repository

    @Override
    public void add() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter book title:");
        String title = scanner.nextLine();

        System.out.println("Enter book author:");
        String author = scanner.nextLine();

        System.out.println("Enter book ISBN:");
        String isbn = scanner.nextLine();

        System.out.println("Enter book category:");
        String category = scanner.nextLine();

        System.out.println("Enter book id:");
        int id = scanner.nextInt();

        System.out.println("Enter publication year:");
        int publicationYear = scanner.nextInt();

        System.out.println("Enter total copies:");
        int totalCopies = scanner.nextInt();

        System.out.println("Enter available copies:");
        int availableCopies = scanner.nextInt();

        Book newBook = new BookBuilder()
                .setId(id)
                .setTitle(title)
                .setAuthor(author)
                .setIsbn(isbn)
                .setCategory(category)
                .setPublicationYear(publicationYear)
                .setTotalCopies(totalCopies)
                .setAvailableCopies(availableCopies)
                .build();

        books.add(newBook);
        System.out.println("Book added successfully.");
    }

    @Override
    public void delete(int id) {
        if (books.removeIf(book -> book.getId() == id)) {
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    @Override
    public Book findById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Book findByName(String name) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }


    @Override
    public void showAll() {
        for (Book book : books) {
            System.out.println(book.getTitle() + " - " + book.getAuthor() + " - " + book.getPublicationYear());
        }
    }


    //update
    @Override
    public void updateBook(int bookId, String title, String author, String category, int totalCopies) {
        //search by id
        Book book = findById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        //modify attributes
        if (title != null) book.setTitle(title);
        if (author != null) book.setAuthor(author);
        if (category != null) book.setCategory(category);
        if (totalCopies >= 0) {
            int diff = totalCopies - book.getTotalCopies();
            book.setAvailableCopies(book.getAvailableCopies() + diff);
            book.setTotalCopies(totalCopies);

        }

        System.out.println("Book updated successfully: " + book);
    }

    //search books by category
    @Override
    public List<Book> searchBooks(String title, String author, String category, Boolean available) {
        return books.stream()
                .filter(book -> (title == null || book.getTitle().equalsIgnoreCase(title)) &&
                        (author == null || book.getAuthor().equalsIgnoreCase(author)) &&
                        (category == null || book.getCategory().equalsIgnoreCase(category)) &&
                        (available == null || (available ? book.getAvailableCopies() > 0 : book.getAvailableCopies() == 0)))
                .collect(Collectors.toList());
    }

    //display book stats
    @Override
    public void displayBookStatistics() {
        //getting all stats
        // total of books availables
        long totalAvailableBooks = books.stream()
                .filter(book -> book.getAvailableCopies() > 0)
                .count();

        // most borrowed books
        List<Book> mostBorrowedBooks = books.stream()
                .sorted(Comparator.comparingInt(Book::getLoanCount).reversed())
                .limit(5)
                .toList();

        // less borrowed books
        List<Book> leastBorrowedBooks = books.stream()
                .sorted(Comparator.comparingInt(Book::getLoanCount))
                .limit(5)
                .toList();

        // Display stats
        System.out.println("=== Book Statistics ===");
        System.out.println("Total books available: " + totalAvailableBooks);

        System.out.println("\nMost borrowed books:");
        mostBorrowedBooks.forEach(book ->
                System.out.println(book.getTitle() + " - has been borrowed " + book.getLoanCount() + " times"));

        System.out.println("\nLeast borrowed books:");
        leastBorrowedBooks.forEach(book ->
                System.out.println(book.getTitle() + " - Borrowed " + book.getLoanCount() + " times"));
    }
}