package fr.efrei.Factory;

import fr.efrei.domain.Librarian;

public class LibrarianBuilder {
    private Librarian librarian;

    public LibrarianBuilder() {
        this.librarian = new Librarian();
    }

    public LibrarianBuilder setName(String name) {
        librarian.setName(name);
        return this;
    }

    public LibrarianBuilder setId(int id) {
        librarian.setId(id);
        return this;
    }

    public Librarian build() {
        if (librarian.name == null || librarian.name.isEmpty()) {
            throw new IllegalArgumentException("Librarian must have a name");
        }
        if (librarian.id <= 0) {
            throw new IllegalArgumentException("Librarian must have an ID greater than 0");
        }
        return this.librarian;
    }
}
