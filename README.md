
# 🛒 Sistema de Carrito de Compras - Java Swing MDI MVC SOLID

### 👨‍💻 Autor: Carlos Andrés Cajas Tapia  
*Carrera:* Computación  
*Asignatura:* Programación Orientada a Objetos  
*Práctica de Laboratorio N.º 4*  
*Título:* Desarrollo de una aplicación para la gestión de archivos y persistencia de datos con validaciones personalizadas, almacenamiento flexible, interfaz gráfica avanzada con enfoque MDI, patrón MVC, DAO y principios SOLID.  
📄 [Informe técnico](https://docs.google.com/document/d/1TTEw9lEWoGPeoWqrx6-nudZxcpbR7zDSevjQqZ0sIO8/edit?tab=t.0)  
---

## 📊 Diagrama de Clases UML

<p align="center">
  <img src="https://github.com/user-attachments/assets/957c52fe-686e-4ce7-912f-84a2d059f16f" alt="Diagrama de Clases UML" width="100%" height="auto">
</p>

---

## 🎯 Objetivos de la práctica

### Objetivo General
Desarrollar un sistema completo en Java Swing con interfaz gráfica avanzada MDI, respetando la arquitectura MVC, DAO, SOLID, incorporando validaciones personalizadas, almacenamiento flexible y documentación Javadoc.

### Objetivos Específicos
- ✅ Implementar validaciones robustas mediante excepciones propias y controladas.
- ✅ Validar correctamente cédula ecuatoriana como username.
- ✅ Validar contraseñas seguras con restricciones definidas.
- ✅ Permitir almacenamiento configurable: memoria, archivos de texto o binarios.
- ✅ Mantener principios SOLID, patrón MVC, DAO con nuevas implementaciones.
- ✅ Generar ejecutable .jar y documentación Javadoc.
- ✅ Mantener internacionalización, formateo dinámico, gráficos y personalización visual.

---

## 🧠 Requerimientos técnicos y funcionales

- Validaciones avanzadas con excepciones propias y controladas.
- Validación de cédula ecuatoriana mediante dígito verificador.
- Contraseñas seguras (mínimo 6 caracteres, mayúsculas, minúsculas, carácter especial @_-).
- Almacenamiento configurable al iniciar sesión: memoria, archivo texto o binario.
- CRUD completo de Usuarios, Productos y Carritos.
- Recuperación de contraseña con preguntas de seguridad.
- Internacionalización (Español, Inglés, Francés).
- Formato de número y fecha dinámico.
- Gráficos usando `Graphics`.
- DAO con almacenamiento flexible y selección de ruta de archivos.
- Generación de `.jar` ejecutable y documentación Javadoc.

---

## 🗂 Estructura del Repositorio

```text
ec.edu.ec.poo/
├── modelo/         # Clases de dominio: Usuario, Producto, Carrito, etc.
├── vista/          # Interfaces gráficas (Swing) para usuarios, carritos, productos.
├── controller/     # Lógica de controladores y validaciones.
├── dao/            # Interfaces DAO.
├── dao/imple/
│   ├── texto/      # DAO con archivos de texto.
│   └── binario/    # DAO con archivos binarios.
└── utils/          # Internacionalización, validaciones, excepciones personalizadas.
```

---

## 🛠 Funcionalidades Completas

### 🔐 Validaciones y Autenticación
- Validación de cédula como username.
- Validación de contraseñas seguras.
- Validación de tipos de datos y campos obligatorios.
- Control de acceso por roles (Administrador / Usuario).

### 📝 Gestión de Usuarios
- Registro con preguntas de seguridad.
- Recuperación de contraseña con verificación guiada.
- CRUD de usuarios para administrador.

### 🛍 Gestión de Productos y Carritos
- CRUD completo de productos (solo ADMIN).
- CRUD completo de carritos con cálculo de subtotal, IVA, total.
- Asociación de carritos a usuario autenticado.

### 🌐 Internacionalización (i18n)
- Cambio dinámico de idioma (Español, Inglés, Francés).
- Traducción automática de menús, botones, ventanas, mensajes, tablas.

### 📁 Almacenamiento Flexible
- Elección del modo de almacenamiento:
  - ✅ Memoria
  - ✅ Archivo de Texto
  - ✅ Archivo Binario
- Opción de especificar ruta al seleccionar almacenamiento en archivos.

### 📊 Gráficos y Formato Dinámico
- Gráficos personalizados con `Graphics` (ejemplo: carritos por usuario).
- Formateo regional de número y fecha:
    - *Español:* 1.000,00 / dd/MM/yyyy
    - *Inglés:* 1,000.00 / MM/dd/yyyy
    - *Francés:* 1 000,00 / dd.MM.yyyy

---


## 📸 Capturas (Ver en informe técnico)

- ✅ Login con selección de almacenamiento y cambio de idioma.
- ✅ Vista principal según rol.
- ✅ Registro y recuperación de contraseña con preguntas.
- ✅ CRUD de productos y carritos.
- ✅ Estadísticas gráficas.

---

## ✍ Firma

*Nombre del estudiante:* Carlos Andrés Cajas Tapia  
*Firma:* _________________________________
