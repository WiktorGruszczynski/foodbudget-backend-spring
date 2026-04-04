## Food Analytics Backend (Java Edition)

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.3-6DB33F?style=flat&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=postgresql&logoColor=white)



This is a modern rewrite of the FoodBudget system, transitioning from a Python/Django architecture to a robust Java 21 + Spring Boot 4 ecosystem


### The Migration

The original project was developed using Django. You can find the legacy Python implementation here: WiktorGruszczynski/FoodBudgetBackend.

---

## 🛠 Tech Stack

* Language: Java 21
* Framework: Spring Boot 4.0.3
* Database: PostgreSQL
* Security: Spring Security (Session-based Auth & CSRF Protection)
* Data Access: Spring Data JPA (Hibernate)
* Mail: Spring Boot Starter Mail (Email Verification)
* Utilities: Lombok, Spring Session JDBC

---

## Installation Guide


#### 1. Clone repository
    ```bash
    https://github.com/WiktorGruszczynski/food-analytics-backend-spring.git
    cd food-analytics-backend-spring
    ```

#### 2. Open project in IDE
* Import the project as a Maven project.
* Install dependencies using the Maven wrapper:
    ```bash
    ./mvnw clean install
    ```

#### 3. Environment Configuration
The project is pre-configured via `application.yml` to use environment variables. Provide the following variables in your system or IDE settings:

| Variable        | Description           | Default / Example                          |
|:----------------|:----------------------|:-------------------------------------------|
| `DB_URL`        | PostgreSQL JDBC URL   | `jdbc:postgresql://localhost:5432/food_db` |
| `DB_USERNAME`   | Database User         | `postgres`                                 |
| `DB_PASSWORD`   | Database Password     | `your_password`                            |
| `MAIL_USERNAME` | SMTP Login            | `your-email@gmail.com`                     |
| `MAIL_PASSWORD` | SMTP Password         | `your-app-password`                        |
| `DEBUG_SQL`     | Hibernate SQL Logging | `false`                                    |

---


