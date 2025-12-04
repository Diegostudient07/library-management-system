package com.library.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Representa un préstamo de un libro a un usuario.
 */
public class Loan {
  private Book book;
  private User user;
  private LocalDate loanDate;
  private LocalDate dueDate;
  private LocalDate returnDate;

  public static final int LOAN_PERIOD_DAYS = 14;
  public static final double LATE_FEE_PER_DAY = 1.0;

  /**
   * Constructor para crear un nuevo préstamo.
   *
   * @param book libro a prestar
   * @param user usuario que solicita el préstamo
   */
  public Loan(Book book, User user) {
    if (book == null) {
      throw new IllegalArgumentException("Book cannot be null");
    }
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    if (!book.isAvailable()) {
      throw new IllegalStateException("Book is not available for loan");
    }

    this.book = book;
    this.user = user;
    this.loanDate = LocalDate.now();
    this.dueDate = loanDate.plusDays(LOAN_PERIOD_DAYS);
    this.returnDate = null;

    book.setAvailable(false);
    user.addLoan(this);
  }

  /**
   * Procesa la devolución del libro prestado.
   */
  public void returnBook() {
    this.returnDate = LocalDate.now();
    book.setAvailable(true);
    user.removeLoan(this);
  }

  /**
   * Verifica si el préstamo está vencido.
   *
   * @return true si está vencido, false en caso contrario
   */
  public boolean isOverdue() {
    if (returnDate != null) {
      return false;
    }
    return LocalDate.now().isAfter(dueDate);
  }

  /**
   * Calcula los días de retraso del préstamo.
   *
   * @return número de días de retraso
   */
  public long getDaysOverdue() {
    if (!isOverdue()) {
      return 0;
    }
    return ChronoUnit.DAYS.between(dueDate, LocalDate.now());
  }

  /**
   * Calcula la multa por retraso.
   *
   * @return monto de la multa
   */
  public double calculateLateFee() {
    return getDaysOverdue() * LATE_FEE_PER_DAY;
  }

  public Book getBook() {
    return book;
  }

  public User getUser() {
    return user;
  }

  public LocalDate getLoanDate() {
    return loanDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }

  @Override
  public String toString() {
    return "Loan{"
        + "book=" + book.getTitle()
        + ", user=" + user.getName()
        + ", loanDate=" + loanDate
        + ", dueDate=" + dueDate
        + ", returnDate=" + returnDate
        + '}';
  }
}
