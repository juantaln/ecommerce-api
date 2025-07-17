# E-commerce API con Spring Boot

Backend para una aplicación de e-commerce simple, desarrollada con Java y Spring Boot. La API gestiona productos, usuarios con roles (ADMIN/USER) y un flujo transaccional para la creación de pedidos. La seguridad está implementada con Spring Security y JSON Web Tokens (JWT).

### Tecnologías Utilizadas
* Java 17
* Spring Boot
* Spring Security (con autorización por roles)
* Spring Data JPA (con relaciones ManyToMany)
* JWT (JSON Web Tokens)
* MySQL
* Lombok & Maven

### Features
* **Gestión de Productos (CRUD):** Los administradores pueden crear, leer, actualizar y eliminar productos.
* **Autenticación de Usuarios:** Registro y Login para usuarios.
* **Autorización por Roles:**
    * **Admins:** Gestionan productos.
    * **Usuarios:** Pueden ver productos y crear pedidos.
* **Creación de Pedidos:** Endpoint transaccional que valida el stock de productos y actualiza el inventario al crear un pedido.

### Cómo Ejecutar Localmente
1.  Clonar el repositorio: `git clone <tu-url-de-github>`
2.  Crear una base de datos en MySQL llamada `ecommerce_db`.
3.  El archivo `data.sql` creará los roles `ROLE_USER` y `ROLE_ADMIN` automáticamente al iniciar.
4.  Actualizar el archivo `src/main/resources/application.properties` con tus credenciales de MySQL.
5.  Ejecutar el proyecto.

### Endpoints de la API

#### Autenticación (Público)
* `POST /api/auth/register`: Registra un nuevo usuario (con rol `ROLE_USER` por defecto).
* `POST /api/auth/login`: Autentica un usuario y devuelve un token JWT.

#### Productos
* `GET /api/products`: Obtiene todos los productos (Público).
* `GET /api/products/{id}`: Obtiene un producto por ID (Público).
* `POST /api/products`: Crea un producto (Solo ADMIN).
* `PUT /api/products/{id}`: Actualiza un producto (Solo ADMIN).
* `DELETE /api/products/{id}`: Elimina un producto (Solo ADMIN).

#### Pedidos (Protegido)
* `POST /api/orders`: Crea un nuevo pedido para el usuario autenticado (USER o ADMIN).