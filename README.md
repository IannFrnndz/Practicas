# Proyecto DAM: Gestión de Ofertas para Viajes Sol Paraíso

Este repositorio contiene mi proyecto de Fin de Grado del ciclo Desarrollo de Aplicaciones Multiplataforma (DAM). El objetivo es la gestión integral de ofertas de viajes para una agencia ficticia llamada **“Viajes Sol Paraíso”**.

---

## Estructura del Proyecto

Principalmente el repositorio se compone de estas tres carpetas fundamentales:

### 1. **gestion-ofertas**
Aplicación web desarrollada en **Java 17** con **Spring Boot**. Permite:

- Registrar y actualizar ofertas de viajes (destinos, precios, fechas, descripciones).
- Búsqueda y visualización de ofertas desde un panel web y API Rest.
- Gestión de usuarios con autenticación (login) para el área de administración.
- Dashboard/resumen administrativo accesible solo para administradores autenticados.
- Frontend web basado en plantillas **HTML/Thymeleaf**:
  - /templates/login.html: Pantalla de login.
  - /templates/dashboard.html: Panel principal.
  - /templates/ofertas/*: Listados, formulario y detalle de ofertas.
  - Fragmentos reutilizables: header, navbar, footer.

#### **Estructura principal de carpetas Java:**
- `controller`: Controladores para la web y REST (ej: `OfertaRestController`, `AuthController`).
- `service`: Lógica de negocio (ej: validaciones, reglas de negocio).
- `repository`: Acceso a datos con Spring Data JPA (ej: `OfertaRepository`).
- `entity`: Entidades que representan tablas de la base de datos (ej: `Oferta`, `Usuario`, `Categoria`).
- `dto`: Objetos de transferencia de datos para separar la API de las entidades internas.
- `exception`: Manejo global de errores (ej: `GlobalExceptionHandler`).

#### **Archivos de configuración importantes:**
- `application.properties`: Configuración de base de datos (por variables de entorno), puerto, etc.
- `Dockerfile`: Para levantar la app fácilmente usando Docker.
- `pom.xml`: Gestión de dependencias/librerías con Maven.

---

### 2. **BasesDeDatos**
Carpeta destinada a los scripts `.sql` de creación y población de la base de datos. Por ejemplo:
- `ofertas_VSP.sql`: Define tablas (usuarios, ofertas) y datos de ejemplo para arrancar rápido el sistema.

---

### 3. **viajessolparaiso_app**
Aplicación móvil desarrollada en **Flutter (Dart)**. Su función es consumir el catálogo de ofertas usando la API REST pública generada por el backend. Actualmente permite:
- Visualizar ofertas (por categorías y detalle individual).
- Modelo de datos tipado usando `fromJson`.
- URL del backend configurable mediante entorno (`--dart-define`).
> No implementa login, ya que solamente realiza consultas públicas (GET); la seguridad de operaciones administrativas está garantizada en el backend.

---

## Tecnologías utilizadas

- **Java 17 + Spring Boot:** Backend y lógica de negocio.
- **HTML5 + Thymeleaf:** Plantillas del frontend web.
- **Dart + Flutter:** App móvil para clientes.
- **Maven:** Gestión de dependencias y empaquetado.
- **Docker:** Despliegue y pruebas locales.
- **MySQL:** Base de datos relacional.

---

## Cómo ejecutar el proyecto

1. **Clona este repositorio:**
   ```
   git clone https://github.com/IannFrnndz/Practicas.git
   ```

2. **Configura la base de datos:**
   - Usa el script correspondientes en `BasesDeDatos/ofertas_VSP.sql` o configura tus variables de entorno para el acceso.

3. **Levanta el backend:**
   - Entra en la carpeta `gestion-ofertas`:
     ```
     cd gestion-ofertas
     ```
   - Puedes usar Docker:
     ```
     docker build -t gestion-ofertas .
     docker run -p 8080:8080 --env-file .env gestion-ofertas
     ```
   - O bien Maven directamente:
     ```
     ./mvnw spring-boot:run
     ```
   - Por defecto, la web quedará accesible en [http://localhost:8080](http://localhost:8080)

4. **Levanta la app Flutter (opcional):**
   - Entra en la carpeta `viajessolparaiso_app`
   - Ejemplo (simulador Android):
     ```
     flutter run --dart-define=API_BASE_URL=http://10.0.2.2:8080/api
     ```

---

## Autor

Ian Fernández Gamo 
Alumno de 2º DAM  
Campus FP Emprende 
Proyecto TFG - Curso 2025/26

---

