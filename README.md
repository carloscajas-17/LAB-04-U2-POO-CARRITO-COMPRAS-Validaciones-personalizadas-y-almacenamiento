# 🛒 Sistema de Carrito de Compras - Java Swing MDI MVC SOLID

### 👨‍💻 Autor: Andrés Cajas  
*Carrera:* Computación  
*Asignatura:* Programación Orientada a Objetos  
*Práctica de Laboratorio N.º 3*  
*Título:* Interfaz gráfica avanzada en Java Swing con enfoque MDI, patrón MVC, DAO y principios SOLID  
📄 [Informe técnico](https://docs.google.com/document/d/1qwa3452jh1zkwDfnyKFFldieOzutVq53deOQVhGN5AE/edit?usp=sharing)  
🎥 [Presentación en YouTube](https://youtu.be/uVT19o5FIVc?si=yQ_wOQ7icvGdiXPG)

## 🖼 Diagrama de Clase
![image](https://github.com/user-attachments/assets/e2bbb97e-e478-4338-b176-32c8fea1a195)


---

## 🎯 Objetivos

### Objetivo General  
Desarrollar una aplicación modular con interfaz gráfica en Java Swing utilizando MDI y el patrón MVC.

### Objetivos Específicos
- ✅ Aplicar los principios *SOLID* en todo el sistema.
- ✅ Implementar *control de acceso por roles* y *recuperación de contraseña*.
- ✅ Incorporar *internacionalización dinámica* (i18n).
- ✅ Utilizar *DAOs en memoria* como capa de persistencia.
- ✅ Mejorar la experiencia del usuario con *formateo y gráficos*.

---

## 🧠 Marco Teórico

- *🪟 Interfaz MDI:* Manejo de múltiples ventanas internas con JDesktopPane y JInternalFrame.
- *🎯 Patrón MVC:* Separación clara entre Modelo, Vista y Controlador.
- *📁 DAO:* Acceso a datos desacoplado mediante clases DAO en memoria.
- *📐 Principios SOLID:* Diseño orientado a objetos limpio, escalable y mantenible.
- *🌍 Internacionalización:* Uso de archivos .properties y ResourceBundle con cambio de idioma en tiempo real.

---

## 🗂 Arquitectura del Proyecto

El sistema se estructura en los siguientes paquetes:


📦 ec.edu.ec.poo.modelo       → Clases de dominio: Usuario, Producto, Carrito, etc.
📦 ec.edu.ec.poo.vista        → Interfaces gráficas (Swing) de usuarios, carritos y productos.
📦 ec.edu.ec.poo.controller   → Lógica de negocio, control de eventos, validaciones.
📦 ec.edu.ec.poo.dao.imple    → DAO en memoria (UsuarioDAO, ProductoDAO, etc).
📦 ec.edu.ec.poo.utils        → Internacionalización y utilidades (MensajeInternacionalizacionHandler).


📸 Ver árbol de paquetes completo en el informe.

---

## 🛠 Funcionalidades Implementadas

### 🔐 Acceso y Roles
- Inicio de sesión con control de roles (ADMINISTRADOR / USUARIO).
- Registro con validación de campos obligatorios.
- Sistema de recuperación de contraseña mediante preguntas de seguridad.

### 🛍 Gestión de Productos y Carritos
- CRUD de productos (solo para ADMIN).
- CRUD de carritos con productos seleccionables por cada usuario.
- Cálculo de subtotales, IVA y total.
- Visualización de detalles del carrito.

### 🌐 Internacionalización (i18n)
- Idiomas soportados: *Español, **Inglés, **Francés*.
- Cambio en tiempo real sin reiniciar.
- Traducción automática de:
  - Menús
  - Botones
  - Títulos
  - Tablas
  - Mensajes emergentes

Ejemplo de claves:
properties
menu.producto.crear = Crear Producto
menu.carrito.listar = Listar Carrito
login.titulo = Inicio de Sesión


---

## 🔄 Recuperación de Contraseña

- Registro de al menos 3 preguntas de seguridad.
- Flujo guiado:
  1. Validar ID y nombre
  2. Mostrar 2 preguntas al azar
  3. Si responde correctamente, ingresar nueva contraseña

> Todo el proceso está completamente desacoplado usando MVC y los textos se adaptan al idioma actual.

---

## 📊 Gráficos y Formato Dinámico

- Uso de Graphics para visualización de estadísticas simples.
- Formateo de *número y fecha* con NumberFormat y DateFormat:
  - *Español:* 1.000,50 y 12/07/2025
  - *Inglés:* 1,000.50 y 07/12/2025
  - *Francés:* 1 000,50 y 12.07.2025

---

## 🔐 Control de Acceso por Rol

| Vista                       | Admin 👑 | Usuario 👤 |
|----------------------------|----------|------------|
| Menú de Productos          | ✅       | ❌         |
| Carritos                   | ✅       | ✅         |
| Internacionalización       | ✅       | ✅         |
| Recuperar Contraseña       | ✅       | ✅         |
| Ver Detalles del Carrito   | ✅       | ✅         |

> Los menús se habilitan/deshabilitan visualmente desde MenuPrincipalView según el rol activo.

---

## 📌 Conclusiones

- Se logró implementar una *arquitectura completa* y funcional en Java.
- Se aplicaron correctamente los principios *SOLID, el patrón **MVC* y el patrón *DAO*.
- Se construyó una interfaz *amigable y modular*, con cambio de idioma en vivo.
- El sistema es *flexible, mantenible y ampliable* para nuevos módulos o bases de datos reales.

---

## 📷 Capturas (ver informe)

- Login y cambio de idioma dinámico  
- Vista principal según el rol  
- CRUD de productos y carritos  
- Flujo completo de recuperación de contraseña  
- Vista gráfica con Graphics  

---

## ✍ Firma

*Nombre de Estudiante:* Andrés Cajas  
*Firma:* ______________________________
