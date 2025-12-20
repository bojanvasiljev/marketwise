# Marketwise AI Coding Guidelines

## Architecture Overview
Marketwise is a stock trading simulator SaaS with gamified seasons/competitions. Backend uses Spring Boot (Java 21) with JDBC (not JPA) for direct SQL queries against PostgreSQL. Frontend is React/TypeScript with Vite, Tailwind CSS, and Recharts. Authentication is session-based (Spring Security planned).

**Key Components:**
- **Backend:** `marketwise/` - REST APIs for users, seasons, accounts, portfolios, trades, leaderboards
- **Frontend:** `marketwise.UI/` - Dashboard, user management, revenue/trading views
- **Database:** PostgreSQL with Flyway migrations in `src/main/resources/db/migration/`
- **Models:** Plain POJOs in `model/` package (no JPA annotations)
- **Data Access:** `repository/` uses JdbcTemplate with raw SQL queries
- **Business Logic:** `service/` layer with password hashing (BCrypt)
- **APIs:** OpenAPI/Swagger docs at `/swagger-ui.html`

## Development Workflows
- **Start Backend:** `cd marketwise && docker-compose up -d` then `mvn spring-boot:run` (runs on :8080)
- **Start Frontend:** `cd marketwise.UI && npm run dev` (runs on :5173, proxies API to :8080)
- **Database:** Flyway auto-migrates on startup; dev DB via Docker Compose (user/pass: postgres/postgres)
- **Tests:** `mvn test` uses Testcontainers PostgreSQL; integration tests extend `AbstractIntegrationTest`
- **Build:** Backend `mvn clean package`; Frontend `npm run build`

## Code Patterns
- **Models:** Simple getters/setters, no annotations (e.g., `User.java`)
- **Repositories:** JdbcTemplate with lambda RowMappers (e.g., `UserRepository.java`)
- **Services:** Handle encoding/validation (e.g., BCrypt passwords in `UserService.java`)
- **Controllers:** OpenAPI annotations, standard REST responses (e.g., `UserController.java`)
- **Frontend APIs:** Fetch with `credentials: 'include'` for sessions; base URL from `VITE_API_BASE_URL` env
- **Error Handling:** Global exception handler returns JSON errors
- **Security:** Temporarily locked down; JWT/session auth planned
- **Styling:** Tailwind utility classes; Recharts for data visualization

## Conventions
- **Naming:** RESTful endpoints (`/api/users`), snake_case DB columns (`user_id`), camelCase Java
- **Testing:** Integration tests with TestRestTemplate; truncate tables in `@BeforeEach`
- **Migrations:** Versioned SQL files (e.g., `V1__init.sql`) with indexes for performance
- **Frontend:** Role-based routes via `ProtectedRoute` component; mock data in `data/` for development

Reference: `MARKETWISE_PLAN.md` for roadmap; schemas in `V1__init.sql`