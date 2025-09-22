# 🎬 Cinema API

RESTful API для кинотеатра с возможностью управления фильмами, сеансами и бронирования мест.  
Проект реализован на **Java 21** с использованием **Spring Boot**.

---

## 🚀 Функционал
- Управление фильмами (CRUD)
- Управление сеансами
- Бронирование и отмена мест
- Авторизация и аутентификация (JWT: access + refresh tokens)
- Поддержка WebSocket для real-time обновлений
- Кэширование для оптимизации запросов
- CI/CD конфигурация (Maven + Spring Boot plugin)

---

## 🛠️ Технологии
- **Java 21**
- **Spring Boot 3.5.5**
    - Spring Web
    - Spring Data JPA
    - Spring Security
- **SQLite** (в качестве базы данных)
- **Hibernate**
- **JWT (jjwt)**
- **Lombok**
- **MapStruct** (для маппинга DTO ↔ Entity)
- **WebSocket**
- **Maven**

---

## ⚙️ Установка и запуск
1. Клонировать проект:
   ```
   git clone https://github.com/username/cinema.git
2. Перейти в папку проекта:
    ```
   cd cinema
3. Собрать проект:
    ```
    mvn clean install
4. Запустить приложение:
    ```
   mvn spring-boot:run

## 🔑 Конфигурация
Основные настройки находятся в application.properties:
    ```bash
    spring.application.name=cinema
    
    spring.datasource.url=jdbc:sqlite:database.db
    spring.datasource.driver-class-name=org.sqlite.JDBC
    
    spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
    spring.jpa.hibernate.ddl-auto=update
    
    # JWT
    jwt.secret=your-secret-key
    jwt.access-token-expiration-ms=3600000
    jwt.refresh-token-expiration-ms=1209600000

## 📌 API Endpoints (примеры)
    🎥 Фильмы
    GET /api/v1/movie – получить список фильмов
    GET /api/v1/movie/{id} – получить фильм по ID
    POST /api/v1/movie – создать фильм
    PUT /api/v1/movie/{id} – обновить фильм
    DELETE /api/v1/movie/{id} – удалить фильм

    🔑 Аутентификация
    POST /api/v1/auth/register – регистрация
    POST /api/v1/auth/login – вход
    POST /api/v1/auth/refresh – обновление токена
    POST /logout – выход из системы (инвалидация refresh-токена).

## 🧪 Тестирование
Для запуска тестов:
    
    mvn test

## 📂 Структура проекта
    
    src/main/java/com/app
    ├── controller     # REST-контроллеры
    ├── entity         # JPA-сущности
    ├── exception      # Кастомные исключения
    ├── model          # DTO
    ├── repository     # Репозитории (Spring Data JPA)
    ├── security       # JWT и конфигурация Security
    ├── service        # Сервисы (бизнес-логика)
    └── util           # Утилиты (например, мапперы)

