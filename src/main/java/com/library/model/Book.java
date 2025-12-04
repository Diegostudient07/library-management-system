package com.library.model;

/**
 * Representa un libro en el sistema de biblioteca.
 */
public class Book {
  private String isbn;
  private String title;
  private String author;
  private boolean available;

  /**
   * Constructor para crear un nuevo libro.
   *
   * @param isbn identificador único del libro
   * @param title título del libro
   * @param author autor del libro
   */
  public Book(String isbn, String title, String author) {
    if (isbn == null || isbn.isEmpty()) {
      throw new IllegalArgumentException("ISBN cannot be null or empty");
    }
    if (title == null || title.isEmpty()) {
      throw new IllegalArgumentException("Title cannot be null or empty");
    }
    if (author == null || author.isEmpty()) {
      throw new IllegalArgumentException("Author cannot be null or empty");
    }
    this.isbn = isbn;
    this.title = title;
    this.author = author;
    this.available = true;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  @Override
  public String toString() {
    return "Book{"
        + "isbn='" + isbn + '\''
        + ", title='" + title + '\''
        + ", author='" + author + '\''
        + ", available=" + available
        + '}';
  }
}