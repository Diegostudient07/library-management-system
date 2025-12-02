# Sistema de Gestión de Biblioteca

Sistema completo de gestión de biblioteca desarrollado en Java con Maven y JUnit 5. 

## Características

- **Gestión de libros**: Agregar, eliminar, buscar por ISBN/título/autor
- **Gestión de usuarios**: Registro, validación de email, límite de préstamos
- **Sistema de préstamos**: Control de disponibilidad y préstamos activos
- **Validaciones completas**: Validación de datos en todas las operaciones
- **Cálculo de multas**: Por retraso en devoluciones (14 días de préstamo)
- **Pruebas unitarias completas**: Cobertura exhaustiva con JUnit 5

## Tecnologías

- **Java 11**
- **Maven** (Gestión de dependencias y construcción)
- **JUnit 5** (Pruebas unitarias)

## Estructura del Proyecto

```
src/main/java/com/library/
├── model/
│   ├── Book.java           # Modelo de libro
│   ├── User.java           # Modelo de usuario
│   └── Loan.java           # Modelo de préstamo
├── service/
│   ├── BookService.java    # Servicio de libros
│   ├── UserService.java    # Servicio de usuarios
│   └── LibraryService.java # Servicio principal
└── Main.java               # Aplicación de demostración

src/test/java/com/library/
├── model/
│   ├── BookTest.java
│   ├── UserTest.java
│   └── LoanTest.java
└── service/
    ├── BookServiceTest.java
    ├── UserServiceTest.java
    └── LibraryServiceTest.java
```

## Compilar y Ejecutar

### Compilar el proyecto
```bash
mvn clean install
```

### Ejecutar pruebas
```bash
mvn test
```

### Ejecutar la aplicación
```bash
mvn exec:java -Dexec.mainClass="com.library.Main"
```

## Características del Sistema

### Modelo de Datos

#### Book (Libro)
- ISBN (identificador único)
- Título
- Autor
- Estado de disponibilidad

#### User (Usuario)
- ID de usuario
- Nombre
- Email (validado)
- Préstamos activos (máximo 3)

#### Loan (Préstamo)
- Libro prestado
- Usuario que lo prestó
- Fecha de préstamo
- Fecha de vencimiento (14 días)
- Fecha de devolución
- Cálculo de multas ($1.00 por día de retraso)

### Validaciones

- **Libros**: ISBN, título y autor no pueden ser nulos o vacíos
- **Usuarios**: ID y nombre requeridos, email debe contener "@"
- **Préstamos**: 
  - Libro debe estar disponible
  - Usuario no puede tener más de 3 préstamos activos
  - No se puede prestar el mismo libro dos veces

## Pruebas

El proyecto incluye 13 clases de prueba con más de 40 casos de prueba que cubren:

- ✅ Creación y validación de objetos
- ✅ Operaciones CRUD de servicios
- ✅ Manejo de excepciones
- ✅ Lógica de negocio (préstamos, devoluciones, multas)
- ✅ Búsquedas y filtros

Para ver el reporte de pruebas:
```bash
mvn test
```

## Autor

**Diego** - Sesión 27

## Licencia

Proyecto educativo - Sistema de Gestión de Biblioteca
