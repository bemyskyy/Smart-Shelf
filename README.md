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

```mermaid
graph TD
    Client[Browser / Client] -->|HTTP / REST API| UI[Angular 17 SPA]
    
    subgraph Frontend
        UI -->|RxJS + HttpClient| AuthGuard[Auth Guard / JWT Interceptor]
        AuthGuard -->|Authorization: Bearer| API_Gateway
    end
    
    subgraph Backend Core [Spring Boot 3]
        API_Gateway((API Endpoints)) --> Sec[Spring Security]
        Sec --> Ctrl[Controllers]
        Ctrl --> Services[Business Logic / State Machine]
        Services --> JPA[Spring Data JPA]
    end
    
    Frontend -.->|JSON / REST| Backend Core
    JPA -->|TCP / 5433| DB[(PostgreSQL)]