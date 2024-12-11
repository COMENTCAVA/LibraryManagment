package main.java.fr.efrei.repository;

public interface ILibrarianRepository {
    public Librarian findByNameAndId(String name, int id);
}
