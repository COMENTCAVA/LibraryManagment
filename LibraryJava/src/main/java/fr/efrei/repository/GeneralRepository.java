package main.java.fr.efrei.repository;

import java.util.List;

public interface GeneralRepository<T> {
    void add();
    void delete(int id);
    T findById(int id);
    T findByName(String name);
    void showAll();
}