package main.java.fr.efrei.factory;

import main.java.fr.efrei.domain.User;

import java.util.ArrayList;

public class UserBuilder {
    private final User user;

    public UserBuilder() {
        this.user = new User();
    }

    public UserBuilder setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0");
        }
        user.setId(id);
        return this;
    }

    public UserBuilder setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        user.setName(name);
        return this;
    }

    public UserBuilder setFines(double fines) {
        user.setFines(fines);
        return this;
    }

    public UserBuilder setBorrowedBooks() {
        user.setBorrowedBooks(new ArrayList<>());
        return this;
    }

    public User build() {
        if (user.getId() <= 0) {
            throw new IllegalStateException("User ID is required and must be greater than 0");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalStateException("User name is required");
        }
        return this.user;
    }
}