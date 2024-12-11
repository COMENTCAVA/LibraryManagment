package main.java.fr.efrei.repository;

public interface ILoanRepository {
    public void createLoan(BookRepository bookRepo, UserRepository userRepo);
    public void reserveBook(BookRepository bookRepo, UserRepository userRepo, int bookId, int userId);
    public void returnBook(int loanId);
    public Loan findById(int loanId);
    public void showOverdueLoansByUser(int userId);
    public void renewLoan(int loanId, int extraDays);
    public void displayDashboard();

}
