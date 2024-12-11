package main.java.fr.efrei.factory;


import main.java.fr.efrei.domain.Librarian;

public class LibrarianBuilder {

    private final Librarian librarian;

    public LibrarianBuilder() {
        this.librarian = new Librarian();
    }

    public LibrarianBuilder setName(String name) {
        this.librarian.setName(name);
        return this;
    }

    public LibrarianBuilder setId(int id) {
        this.librarian.setId(id);
        return this;
    }

    public Librarian build() {
        if (this.librarian.getName() == null || this.librarian.getName().isEmpty()) {
            throw new IllegalArgumentException("Librarian must have a name");
        }
        if (this.librarian.getId() <= 0) {
            throw new IllegalArgumentException("Librarian must have an ID greater than 0");
        }
        return this.librarian;
    }
}
