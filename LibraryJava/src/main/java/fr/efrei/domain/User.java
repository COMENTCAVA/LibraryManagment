package main.java.fr.efrei.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    //attributes
    private int id;
    private String name;
    private List<Loan> borrowedBooks = new ArrayList<>();
    private double fines;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Loan> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Loan> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public double getFines() {
        return fines;
    }

    public void setFines(double fines) {
        this.fines = fines;
    }
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "'}";
    }
}