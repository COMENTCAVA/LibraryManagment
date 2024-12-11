package main.java.fr.efrei.repository;

import main.java.fr.efrei.domain.Librarian;

public interface ILibrarianRepository {
    public Librarian findByNameAndId(String name, int id);
}
