package main.java.fr.efrei.factory;

import main.java.fr.efrei.domain.Book;
import main.java.fr.efrei.domain.Loan;
import main.java.fr.efrei.domain.LoanStatus;
import main.java.fr.efrei.domain.User;

import java.util.Date;

public class LoanBuilder {
    private final Loan loan;

    public LoanBuilder() {
        this.loan = new Loan();
    }

    public LoanBuilder setLoanId(int loanId) {
        if (loanId <= 0) {
            throw new IllegalArgumentException("Loan ID must be greater than 0");
        }
        loan.setLoanId(loanId);
        return this;
    }

    public LoanBuilder setUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        loan.setUser(user);
        return this;
    }

    public LoanBuilder setBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        loan.setBook(book);
        return this;
    }

    public LoanBuilder setLoanDate(Date loanDate) {
        if (loanDate == null) {
            throw new IllegalArgumentException("Loan date cannot be null");
        }
        loan.setLoanDate(loanDate);
        return this;
    }

    public LoanBuilder setDueDate(Date dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        loan.setDueDate(dueDate);
        return this;
    }

    public LoanBuilder setReturnDate(Date returnDate) {
        loan.setReturnDate(returnDate);
        return this;
    }

    public LoanBuilder setStatus(LoanStatus status) {
        loan.setStatus(status);
        return this;
    }

    public Loan build() {
        if (loan.getLoanId() <= 0) {
            throw new IllegalStateException("Loan ID is required and must be greater than 0");
        }
        if (loan.getUser() == null) {
            throw new IllegalStateException("User is required");
        }
        if (loan.getBook() == null) {
            throw new IllegalStateException("Book is required");
        }
        if (loan.getLoanDate() == null) {
            throw new IllegalStateException("Loan date is required");
        }
        if (loan.getDueDate() == null) {
            throw new IllegalStateException("Due date is required");
        }
        return this.loan;
    }
}