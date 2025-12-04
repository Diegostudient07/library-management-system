package com.library;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LibraryService;
import com.library.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para Main.java y su lógica.
 * Ahora testea directamente métodos públicos de Main.java.
 */
public class MainComponentTest {
  private BookService bookService;
  private UserService userService;
  private LibraryService libraryService;

  @BeforeEach
  public void setUp() {
    // Inicializar servicios usando Main
    Main.initializeServices();
    bookService = Main.getBookService();
    userService = Main.getUserService();
    libraryService = Main.getLibraryService();
  }

  /**
   * Test que verifica Main.cargarDatosIniciales() carga 10 libros.
   */
  @Test
  public void testMainCargarDatosIniciales() {
    Main.cargarDatosIniciales();
    assertEquals(10, bookService.getAllBooks().size());
    assertEquals(3, userService.getAllUsers().size());
  }

  /**
   * Test que verifica libros específicos cargados por Main.
   */
  @Test
  public void testMainBooksLoaded() {
    Main.cargarDatosIniciales();
    
    Book effectiveJava = bookService.findByIsbn("978-0-13-468599-1");
    assertNotNull(effectiveJava);
    assertEquals("Effective Java", effectiveJava.getTitle());
    assertEquals("Joshua Bloch", effectiveJava.getAuthor());
  }

  /**
   * Test que verifica usuarios cargados por Main.
   */
  @Test
  public void testMainUsersLoaded() {
    Main.cargarDatosIniciales();
    
    User diego = userService.findUserById("U001");
    assertNotNull(diego);
    assertEquals("Diego", diego.getName());
    assertEquals("diego@example.com", diego.getEmail());
  }

  /**
   * Test que verifica acceso a servicios desde Main.
   */
  @Test
  public void testMainServiceAccess() {
    assertNotNull(Main.getBookService());
    assertNotNull(Main.getUserService());
    assertNotNull(Main.getLibraryService());
  }

  /**
   * Test simulando agregar libro como lo hace Main.
   */
  @Test
  public void testAgregarLibroLogica() {
    Main.cargarDatosIniciales();
    int librosIniciales = bookService.getTotalBooks();
    
    // Simula agregar un nuevo libro
    String isbn = "978-1-234-56789-0";
    String titulo = "Test Book";
    String autor = "Test Author";

    Book libro = new Book(isbn, titulo, autor);
    bookService.addBook(libro);

    assertEquals(librosIniciales + 1, bookService.getTotalBooks());
    assertNotNull(bookService.findByIsbn(isbn));
  }

  /**
   * Test simulando registro de usuario como lo hace Main.
   */
  @Test
  public void testRegistrarUsuarioLogica() {
    Main.cargarDatosIniciales();
    int usuariosIniciales = userService.getTotalUsers();
    
    String userId = "U999";
    String nombre = "Nuevo Usuario";
    String email = "nuevo@example.com";

    User usuario = new User(userId, nombre, email);
    userService.registerUser(usuario);

    assertEquals(usuariosIniciales + 1, userService.getTotalUsers());
    assertNotNull(userService.findUserById(userId));
  }

  /**
   * Test simulando préstamo como lo hace Main.
   */
  @Test
  public void testRealizarPrestamoLogica() {
    Main.cargarDatosIniciales();
    
    // Usar datos cargados por Main
    Loan prestamo = libraryService.loanBook("978-0-13-468599-1", "U001");

    assertNotNull(prestamo);
    assertNotNull(prestamo.getLoanDate());
    assertNotNull(prestamo.getDueDate());
    
    Book libro = bookService.findByIsbn("978-0-13-468599-1");
    assertFalse(libro.isAvailable());
  }

  /**
   * Test simulando devolución como lo hace Main.
   */
  @Test
  public void testDevolverLibroLogica() {
    Main.cargarDatosIniciales();

    Book libro = bookService.findByIsbn("978-0-13-468599-1");
    libraryService.loanBook("978-0-13-468599-1", "U001");
    assertFalse(libro.isAvailable());

    libraryService.returnBook("978-0-13-468599-1", "U001");
    assertTrue(libro.isAvailable());
  }

  /**
   * Test simulando búsqueda de libros por título.
   */
  @Test
  public void testBuscarLibroPorTitulo() {
    Main.cargarDatosIniciales();

    var resultados = bookService.findByTitle("java");
    assertTrue(resultados.size() > 0);
  }

  /**
   * Test simulando ver todos los libros.
   */
  @Test
  public void testVerTodosLosLibros() {
    Main.cargarDatosIniciales();
    assertEquals(10, bookService.getAllBooks().size());
  }

  /**
   * Test simulando ver todos los usuarios.
   */
  @Test
  public void testVerTodosLosUsuarios() {
    Main.cargarDatosIniciales();
    assertEquals(3, userService.getAllUsers().size());
  }

  /**
   * Test simulando ver préstamos activos.
   */
  @Test
  public void testVerPrestamosActivos() {
    Main.cargarDatosIniciales();

    libraryService.loanBook("978-0-13-468599-1", "U001");
    libraryService.loanBook("978-0-13-235088-4", "U002");

    var prestamosActivos = libraryService.getActiveLoans();
    assertEquals(2, prestamosActivos.size());
  }

  /**
   * Test simulando cálculo de multa.
   */
  @Test
  public void testCalcularMultaPorRetraso() {
    Main.cargarDatosIniciales();

    Loan prestamo = libraryService.loanBook("978-0-13-468599-1", "U001");
    double multa = libraryService.calculateLateFee(prestamo);

    // Préstamo reciente no debe tener multa
    assertEquals(0.0, multa, 0.01);
  }

  /**
   * Test simulando ver libros disponibles.
   */
  @Test
  public void testVerLibrosDisponibles() {
    Main.cargarDatosIniciales();

    libraryService.loanBook("978-0-13-468599-1", "U001");

    var disponibles = bookService.getAvailableBooks();
    assertEquals(9, disponibles.size());
  }

  /**
   * Test simulando búsqueda de usuario por ID.
   */
  @Test
  public void testBuscarUsuarioPorId() {
    Main.cargarDatosIniciales();

    User encontrado = userService.findUserById("U001");
    assertNotNull(encontrado);
    assertEquals("Diego", encontrado.getName());
  }

  @Test
  public void testValidacionEmailUsuario() {
    // Simula validación de email al registrar
    assertThrows(IllegalArgumentException.class, () -> {
      new User("U001", "Diego", "email-invalido");
    });
  }

  @Test
  public void testValidacionIsbnLibro() {
    // Simula validación de ISBN al agregar
    assertThrows(IllegalArgumentException.class, () -> {
      new Book("", "Effective Java", "Joshua Bloch");
    });
  }
}
