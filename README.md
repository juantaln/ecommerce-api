# E-commerce API con Spring Boot y Funcionalidades Avanzadas

Este es el backend para una aplicación de e-commerce, desarrollado con Java y Spring Boot. La API gestiona productos, usuarios con roles y un flujo transaccional completo que incluye una pasarela de pagos con Stripe y notificaciones por email.

### 🚀 Demo en Vivo

La API está desplegada y completamente funcional en la siguiente URL. ¡Puedes probarla usando Postman!

**URL Base:** `https://ecommerce-api-8ujg.onrender.com` 

---

### 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3
* **Seguridad:** Spring Security (Autenticación con JWT)
* **Base de Datos:** Spring Data JPA (Hibernate) con PostgreSQL
* **Pasarela de Pagos:** Stripe
* **Notificaciones:** Spring Boot Mail (envío asíncrono)
* **Contenerización:** Docker
* **Despliegue:** Render

---

### ✨ Features

* **Gestión de Productos (CRUD):** Endpoints para crear, leer, actualizar y eliminar productos (protegido para rol `ADMIN`).
* **Autenticación y Autorización:** Registro y Login de usuarios con JWT. Rutas protegidas por roles (`USER`, `ADMIN`).
* **Integración con Stripe:** El flujo de creación de pedidos genera una intención de pago en Stripe antes de confirmar la orden.
* **Notificaciones Asíncronas:** Envío de un correo de confirmación de compra en segundo plano para no afectar el tiempo de respuesta de la API.
* **Despliegue en la Nube:** La aplicación está completamente contenerizada con Docker y desplegada en una plataforma como servicio (Render) con una base de datos externa.

---

### ⚙️ Endpoints de la API

#### Autenticación (`/api/auth`)

* `POST /register`: Registra un nuevo usuario (con rol `USER` por defecto).
* `POST /login`: Autentica un usuario y devuelve un token JWT.

#### Productos (`/api/products`)

* `GET /`: Obtiene todos los productos (Público).
* `GET /{id}`: Obtiene un producto por ID (Público).
* `POST /`: Crea un nuevo producto (Solo `ADMIN`).
* `PUT /{id}`: Actualiza un producto existente (Solo `ADMIN`).
* `DELETE /{id}`: Elimina un producto (Solo `ADMIN`).

#### Pedidos (`/api/orders`)

* `POST /`: Crea un nuevo pedido para el usuario autenticado (requiere token).

---
**Nota sobre el Despliegue Gratuito:**
Esta API está desplegada en el plan gratuito de Render. Si es la primera vez que la usas después de un tiempo, la primera petición puede tardar hasta un minuto en responder mientras el servidor se "despierta". ¡Ten paciencia! Las peticiones siguientes serán instantáneas
---