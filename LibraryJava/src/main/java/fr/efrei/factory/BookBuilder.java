package main.java.fr.efrei.factory;


import main.java.fr.efrei.domain.Book;

public class BookBuilder {
    private Book book;

    public BookBuilder() {
        this.book = new Book();
    }

    public BookBuilder setId(int id) {
        book.id = id;
        return this;
    }

    public BookBuilder setTitle(String title) {
        book.title = title;
        return this;
    }

    public BookBuilder setAuthor(String author) {
        book.author = author;
        return this;
    }

    public BookBuilder setIsbn(String isbn) {
        book.isbn = isbn;
        return this;
    }

    public BookBuilder setCategory(String category) {
        book.category = category;
        return this;
    }

    public BookBuilder setPublicationYear(int year) {
        book.publicationYear = year;
        return this;
    }

    public BookBuilder setTotalCopies(int totalCopies) {
        book.totalCopies = totalCopies;
        return this;
    }

    public BookBuilder setAvailableCopies(int availableCopies) {
        book.availableCopies = availableCopies;
        return this;
    }
    public BookBuilder setLoanCount(){
        book.loanCount = 0;
        return this;
    }

    public Book build() {
        if (book.id <= 0) {
            throw new IllegalArgumentException("Book ID must be greater than 0");
        }
        if (book.title == null || book.title.isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        return this.book;
    }
}