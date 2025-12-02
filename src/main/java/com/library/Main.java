package com.library;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LibraryService;
import com.library.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static BookService bookService;
    private static UserService userService;
    private static LibraryService libraryService;
    private static Scanner scanner;

    public static void main(String[] args) {
        // Inicializar servicios
        bookService = new BookService();
        userService = new UserService();
        libraryService = new LibraryService(bookService, userService);
        scanner = new Scanner(System.in);

        // Agregar datos iniciales
        cargarDatosIniciales();

        // Menú principal
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    agregarLibro();
                    break;
                case 2:
                    registrarUsuario();
                    break;
                case 3:
                    realizarPrestamo();
                    break;
                case 4:
                    devolverLibro();
                    break;
                case 5:
                    buscarLibro();
                    break;
                case 6:
                    verTodosLosLibros();
                    break;
                case 7:
                    verTodosLosUsuarios();
                    break;
                case 8:
                    verPrestamosActivos();
                    break;
                case 9:
                    verPrestamosVencidos();
                    break;
                case 10:
                    System.out.println("\n¡Gracias por usar el Sistema de Gestión de Biblioteca!");
                    salir = true;
                    break;
                default:
                    System.out.println("\n❌ Opción inválida. Intente nuevamente.");
            }
        }
        
        scanner.close();
    }

    private static void cargarDatosIniciales() {
        // Agregar libros de ejemplo - Programación
        bookService.addBook(new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch"));
        bookService.addBook(new Book("978-0-13-235088-4", "Clean Code", "Robert C. Martin"));
        bookService.addBook(new Book("978-0-13-110362-7", "The Pragmatic Programmer", "Andrew Hunt"));
        bookService.addBook(new Book("978-0-201-63361-0", "Design Patterns", "Gang of Four"));
        bookService.addBook(new Book("978-0-596-52068-7", "JavaScript: The Good Parts", "Douglas Crockford"));
        bookService.addBook(new Book("978-1-491-95077-3", "Python Crash Course", "Eric Matthes"));
        bookService.addBook(new Book("978-0-134-68599-2", "Refactoring", "Martin Fowler"));
        bookService.addBook(new Book("978-0-321-12521-7", "Domain-Driven Design", "Eric Evans"));
        bookService.addBook(new Book("978-0-132-35088-4", "The Clean Coder", "Robert C. Martin"));
        bookService.addBook(new Book("978-1-617-29424-3", "Spring in Action", "Craig Walls"));
        
        // Registrar usuarios de ejemplo
        userService.registerUser(new User("U001", "Diego", "diego@example.com"));
        userService.registerUser(new User("U002", "María", "maria@example.com"));
        userService.registerUser(new User("U003", "Carlos", "carlos@example.com"));
    }

    private static void mostrarMenu() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN DE BIBLIOTECA            ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("  1. 📚 Agregar libro");
        System.out.println("  2. 👤 Registrar usuario");
        System.out.println("  3. 📖 Realizar préstamo");
        System.out.println("  4. 📥 Devolver libro");
        System.out.println("  5. 🔍 Buscar libro");
        System.out.println("  6. 📋 Ver todos los libros");
        System.out.println("  7. 👥 Ver todos los usuarios");
        System.out.println("  8. 📊 Ver préstamos activos");
        System.out.println("  9. ⚠️  Ver préstamos vencidos");
        System.out.println(" 10. 🚪 Salir");
        System.out.println("════════════════════════════════════════════════");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void agregarLibro() {
        System.out.println("\n=== AGREGAR NUEVO LIBRO ===");
        
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        
        try {
            Book libro = new Book(isbn, titulo, autor);
            bookService.addBook(libro);
            System.out.println("✅ Libro agregado exitosamente!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void registrarUsuario() {
        System.out.println("\n=== REGISTRAR NUEVO USUARIO ===");
        
        System.out.print("ID de usuario: ");
        String userId = scanner.nextLine();
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        try {
            User usuario = new User(userId, nombre, email);
            userService.registerUser(usuario);
            System.out.println("✅ Usuario registrado exitosamente!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void realizarPrestamo() {
        System.out.println("\n=== REALIZAR PRÉSTAMO ===");
        
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();
        
        System.out.print("ID del usuario: ");
        String userId = scanner.nextLine();
        
        try {
            Loan prestamo = libraryService.loanBook(isbn, userId);
            System.out.println("✅ Préstamo realizado exitosamente!");
            System.out.println("   Fecha de préstamo: " + prestamo.getLoanDate());
            System.out.println("   Fecha de vencimiento: " + prestamo.getDueDate());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void devolverLibro() {
        System.out.println("\n=== DEVOLVER LIBRO ===");
        
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();
        
        System.out.print("ID del usuario: ");
        String userId = scanner.nextLine();
        
        try {
            libraryService.returnBook(isbn, userId);
            System.out.println("✅ Libro devuelto exitosamente!");
            
            // Buscar el préstamo para calcular multa si hay
            List<Loan> loans = libraryService.getAllLoans();
            for (Loan loan : loans) {
                if (loan.getBook().getIsbn().equals(isbn) && 
                    loan.getUser().getUserId().equals(userId) &&
                    loan.getReturnDate() != null) {
                    double multa = loan.calculateLateFee();
                    if (multa > 0) {
                        System.out.println("⚠️  Multa por retraso: $" + multa);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void buscarLibro() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║           🔍 BUSCAR LIBRO                     ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("1. Buscar por ISBN");
        System.out.println("2. Buscar por título");
        System.out.println("3. Buscar por autor");
        System.out.print("Seleccione opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1:
                System.out.print("ISBN: ");
                String isbn = scanner.nextLine();
                Book libro = bookService.findByIsbn(isbn);
                if (libro != null) {
                    List<Book> resultado = new ArrayList<>();
                    resultado.add(libro);
                    mostrarLibros(resultado);
                } else {
                    System.out.println("\n❌ Libro no encontrado.");
                }
                break;
            case 2:
                System.out.print("Título: ");
                String titulo = scanner.nextLine();
                List<Book> librosTitulo = bookService.findByTitle(titulo);
                mostrarLibros(librosTitulo);
                break;
            case 3:
                System.out.print("Autor: ");
                String autor = scanner.nextLine();
                List<Book> librosAutor = bookService.findByAuthor(autor);
                mostrarLibros(librosAutor);
                break;
            default:
                System.out.println("❌ Opción inválida.");
        }
    }

    private static void verTodosLosLibros() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║         📋 CATÁLOGO DE LIBROS                 ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        List<Book> libros = bookService.getAllBooks();
        mostrarLibros(libros);
        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("📊 Total de libros: " + libros.size());
        System.out.println("✅ Libros disponibles: " + bookService.getAvailableBooks().size());
    }

    private static void verTodosLosUsuarios() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        👥 USUARIOS REGISTRADOS                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        List<User> usuarios = userService.getAllUsers();
        
        if (usuarios.isEmpty()) {
            System.out.println("❌ No hay usuarios registrados.");
        } else {
            for (User usuario : usuarios) {
                System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.println("👤 ID: " + usuario.getUserId());
                System.out.println("   Nombre: " + usuario.getName());
                System.out.println("   Email: " + usuario.getEmail());
                System.out.println("   Préstamos activos: " + usuario.getActiveLoans().size() + "/" + User.MAX_LOANS);
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        }
        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("📊 Total de usuarios: " + usuarios.size());
    }

    private static void verPrestamosActivos() {
        System.out.println("\n=== PRÉSTAMOS ACTIVOS ===");
        List<Loan> prestamos = libraryService.getActiveLoans();
        
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos activos.");
        } else {
            for (Loan prestamo : prestamos) {
                System.out.println("\n📖 Libro: " + prestamo.getBook().getTitle());
                System.out.println("   Usuario: " + prestamo.getUser().getName());
                System.out.println("   Fecha préstamo: " + prestamo.getLoanDate());
                System.out.println("   Fecha vencimiento: " + prestamo.getDueDate());
                if (prestamo.isOverdue()) {
                    System.out.println("   ⚠️  VENCIDO - Días de retraso: " + prestamo.getDaysOverdue());
                    System.out.println("   Multa: $" + prestamo.calculateLateFee());
                }
            }
        }
        System.out.println("\nTotal préstamos activos: " + prestamos.size());
    }

    private static void verPrestamosVencidos() {
        System.out.println("\n=== PRÉSTAMOS VENCIDOS ===");
        List<Loan> prestamosVencidos = libraryService.getOverdueLoans();
        
        if (prestamosVencidos.isEmpty()) {
            System.out.println("✅ No hay préstamos vencidos.");
        } else {
            for (Loan prestamo : prestamosVencidos) {
                System.out.println("\n⚠️  Libro: " + prestamo.getBook().getTitle());
                System.out.println("   Usuario: " + prestamo.getUser().getName());
                System.out.println("   Fecha vencimiento: " + prestamo.getDueDate());
                System.out.println("   Días de retraso: " + prestamo.getDaysOverdue());
                System.out.println("   Multa: $" + prestamo.calculateLateFee());
            }
        }
        System.out.println("\nTotal préstamos vencidos: " + prestamosVencidos.size());
    }

    private static void mostrarLibros(List<Book> libros) {
        if (libros.isEmpty()) {
            System.out.println("❌ No se encontraron libros.");
        } else {
            for (Book libro : libros) {
                System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.println("📚 ISBN: " + libro.getIsbn());
                System.out.println("   Título: " + libro.getTitle());
                System.out.println("   Autor: " + libro.getAuthor());
                String estado = libro.isAvailable() ? "✅ Disponible" : "❌ Prestado";
                System.out.println("   Estado: " + estado);
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        }
    }
}