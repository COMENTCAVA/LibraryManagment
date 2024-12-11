package main.java.fr.efrei.repository;


import main.java.fr.efrei.domain.Librarian;
import main.java.fr.efrei.factory.LibrarianBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibrarianRepository implements GeneralRepository<Librarian>, ILibrarianRepository{
    //attribute
    private final List<Librarian> librarians = new ArrayList<>();

    //getter
    public List<Librarian> getAll() {
        return new ArrayList<>(librarians); // Retourne une nouvelle liste pour éviter des modifications externes
    }

    public List<Librarian> getLibrarians() {
        return librarians;
    }

    //functions of gen repository
    @Override
    public void add() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter librarian name:");
        String name = scanner.nextLine();

        System.out.println("Enter librarian ID:");
        int id = scanner.nextInt();

        Librarian newLibrarian = new LibrarianBuilder()
                .setName(name)
                .setId(id)
                .build();

        librarians.add(newLibrarian);
        System.out.println("Librarian added successfully.");
    }

    @Override
    public void delete(int id) {
        librarians.removeIf(librarian -> librarian.getId() == id);
        System.out.println("Librarian deleted successfully.");
    }

    @Override
    public Librarian findById(int id) {
        return librarians.stream().filter(librarian -> librarian.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Librarian findByName(String name) {
        return librarians.stream().filter(librarian -> librarian.getName().toLowerCase().contains(name.toLowerCase())).findFirst().orElse(null);
    }


    @Override
    public void showAll() {
        for (Librarian librarian : librarians) {
            System.out.println(librarian.getName() + " (ID: " + librarian.getId() + ")");
        }
    }

    //find by name and id for login purpose
    @Override
    public Librarian findByNameAndId(String name, int id) {
        return librarians.stream()
                .filter(librarian -> librarian.getName().equalsIgnoreCase(name) && librarian.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
