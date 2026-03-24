# 📚 SmartShelf (Умная Полка)

![Angular](https://img.shields.io/badge/Angular-17+-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3+-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)

**SmartShelf** — это корпоративная/университетская платформа для шеринга (обмена) вещами. Проект позволяет пользователям делиться своими книгами, инструментами или настольными играми, а также брать во временное пользование вещи коллег.

---

## ✨ Ключевой функционал

* **Умный каталог:** Мгновенный поиск по доступным вещам без перезагрузки страницы.
* **Личный инвентарь:** Управление своими ресурсами и отслеживание их статуса (Доступна / На руках).
* **Центр обмена (State Machine):** Полный цикл управления заявками:
    * Отправка запроса владельцу.
    * Одобрение или отклонение заявки владельцем.
    * Подтверждение возврата вещи.
* **Живые уведомления:** Индикация новых входящих заявок в реальном времени.

---

## 🏗 Архитектура приложения (High-Level Architecture)

Проект построен по классической трехуровневой микросервисной архитектуре с разделением на Frontend и Backend.

---

## 🛠 Технологический стек

### Backend
* **Java 21** & **Spring Boot 3**
* **Spring Security & JWT** (Безопасность и авторизация)
* **Spring Data JPA / Hibernate** (Работа с БД)
* **PostgreSQL** (Основная база данных)

### Frontend
* **Angular 17** (Standalone Components)
* **RxJS** (Реактивное программирование и State Management)
* **Tailwind CSS** (Стилизация и адаптивная верстка)

---

## 🚀 Как запустить проект локально

### Быстрый запуск через Docker
Для этого способа вам нужен только установленный Docker Desktop.
1. Откройте терминал в корневой папке проекта (где находится файл `docker-compose.yml`).
2. Выполните команду:
   ```bash
   docker-compose up --build
   ```
