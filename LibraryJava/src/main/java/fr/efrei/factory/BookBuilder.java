package main.java.fr.efrei.factory;


import main.java.fr.efrei.domain.Book;

public class BookBuilder {
    private final Book book;

    public BookBuilder() {
        this.book = new Book();
    }

    public BookBuilder setId(int id) {
        this.book.setId(id);
        return this;
    }

    public BookBuilder setTitle(String title) {
        this.book.setTitle(title);
        return this;
    }

    public BookBuilder setAuthor(String author) {
        this.book.setAuthor(author);
        return this;
    }

    public BookBuilder setIsbn(String isbn) {
        this.book.setIsbn(isbn);
        return this;
    }

    public BookBuilder setCategory(String category) {
        this.book.setCategory(category);
        return this;
    }

    public BookBuilder setPublicationYear(int year) {
        this.book.setPublicationYear(year);
        return this;
    }

    public BookBuilder setTotalCopies(int totalCopies) {
        this.book.setTotalCopies(totalCopies);
        return this;
    }

    public BookBuilder setAvailableCopies(int availableCopies) {
        this.book.setAvailableCopies(availableCopies);
        return this;
    }
    public BookBuilder setLoanCount(){
        this.book.setLoanCount(0);
        return this;
    }

    public Book build() {
        if (this.book.getTotalCopies() == 0) {
            throw new IllegalArgumentException("Book total copies must be greater than 0");
        }

        if (this.book.getAvailableCopies() == 0) {
            throw new IllegalArgumentException("Book available copies must be greater than 0");
        }

        if (this.book.getId() <= 0) {
            throw new IllegalArgumentException("Book ID must be greater than 0");
        }
        if (this.book.getTitle() == null || this.book.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        return this.book;
    }
}