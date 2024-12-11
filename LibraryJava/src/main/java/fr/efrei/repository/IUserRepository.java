package main.java.fr.efrei.repository;

import main.java.fr.efrei.domain.User;

public interface IUserRepository {
    public double calculateFine(User user);
    public void displayOverdueBooks(User user);
    public void updateUser(int userId, String newName);
    public void showUserLoanHistory(int userId);
    public int remainingBooksToBorrow(User user);
    public void showRemainingBooksToBorrow(int userId);
    public void sendOverdueNotifications(User user);
    public void alertUnreturnedBooks(User user);
}
