package main.java.fr.efrei.repository;

import main.java.fr.efrei.domain.Book;
import java.util.List;

public interface IBookRepository {
    public void updateBook(int bookId, String title, String author, String category, int totalCopies);
    public List<Book> searchBooks(String title, String author, String category, Boolean available);
    public void displayBookStatistics();
}
