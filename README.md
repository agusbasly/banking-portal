# 🚀 Comafi Banking Portal

Un portal bancario moderno desarrollado en **Angular** (TypeScript) y **Spring Boot** (Java), que permite gestionar cuentas, consultar movimientos y realizar transferencias de manera sencilla y segura.

---

## ✨ Características principales

- **Listado de cuentas:** Visualiza todas las cuentas bancarias en una tabla con Angular Material.
- **Crear cuenta:** Formulario para dar de alta nuevas cuentas.
- **Eliminar cuenta:** Opción para borrar cuentas existentes.
- **Actualizar saldo:** Modifica el balance de una cuenta mediante un endpoint REST.
- **Transferencias:** Envía fondos entre cuentas y registra la operación.
- **Ver movimientos:** Historial de movimientos de cada cuenta en un diálogo.
- **UI Angular Material:** Tarjetas, tablas, botones y diálogos estilizados.
- **Tests unitarios:** Con Karma y Jasmine para componentes y servicios.
- **Cobertura de código:** Configurada con `ng test --code-coverage`.
- **Mocking HTTP:** Con `HttpClientTestingModule`.

---

## 📁 Estructura del proyecto

```
/
├── backend/              # API RESTful en Spring Boot (Java)
│   ├── src/main/java/    # Controladores, servicios, entidades
│   └── src/test/java/    # Tests con JUnit
├── frontend/             # Aplicación Angular
│   └── src/app/
│       ├── features/     # Lógica de características (accounts, etc.)
│       ├── services/     # Servicios HTTP y mocks de tests
│       └── app.component/ # Componente principal y rutas
├── README.md             # Este archivo de descripción
└── package.json          # Dependencias y scripts de npm
```

---

## 🛠️ Prerrequisitos

- Node.js v12 o superior
- Angular CLI v9 o superior
- Java 11
- Maven o Gradle (para el backend)
- Docker y Docker Compose (opcional)

---

## ⚡ Instalación y ejecución local

### Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```
Servicio disponible en: [http://localhost:8080](http://localhost:8080)

### Frontend (Angular)

```bash
cd frontend
npm install
ng serve
```
Aplicación disponible en: [http://localhost:4200](http://localhost:4200)

---

## 🐳 Ejecución con Docker

### Opción A: Docker Compose (backend + frontend)

```bash
cd <raíz-del-proyecto>
docker-compose up 
```
- Backend: [http://localhost:8080](http://localhost:8080)
- Frontend: [http://localhost:4200](http://localhost:4200)

### Opción B: Contenedores individuales

**Backend:**
```bash
docker build -t banking-backend ./backend
docker run -d -p 8080:8080 banking-backend
```

**Frontend:**
```bash
docker build -t banking-frontend ./frontend
docker run -d -p 4200:80 banking-frontend
```

---

## 🧪 Tests

### Frontend (Angular)

```bash
cd frontend
ng test 
```

### Backend (Spring Boot)

```bash
cd backend
mvn test
```

### Cobertura de código (Frontend)

```bash
cd frontend
ng test --watch=false --code-coverage
```
Informe HTML generado en `frontend/coverage/`.

---

## 📚 Endpoints de la API

| Método | Endpoint                               | Descripción                              |
|--------|----------------------------------------|------------------------------------------|
| GET    | `/accounts`                           | Listar todas las cuentas                 |
| POST   | `/accounts`                           | Crear nueva cuenta                       |
| DELETE | `/accounts/{id}`                      | Eliminar cuenta por ID                   |
| PUT    | `/accounts/{id}?saldo={valor}`        | Actualizar balance de una cuenta         |
| GET    | `/movimientos/{accountId}`            | Obtener movimientos de una cuenta        |
| POST   | `/transferencias`                     | Realizar transferencia entre cuentas     |




