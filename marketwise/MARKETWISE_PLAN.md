# Marketwise Stock Simulator - Progress Tracker

This tracker shows the current status of the Marketwise Stock Simulator project. Check off tasks as they are completed.

---

## Phase 1: Setup & Infrastructure

### ✅ Completed
- [x] Create empty GitHub repository
- [x] Install Java 17 (later upgrade to 21)
- [x] Set up VS Code environment
- [x] Initialize Spring Boot project (Maven) with:
  - [x] spring-boot-starter-web
  - [x] spring-boot-starter-security
  - [x] spring-boot-starter-data-jdbc
  - [x] Flyway
  - [x] PostgreSQL driver
- [x] Configure Docker for PostgreSQL (version 15)
- [x] Configure `application.yml` for datasource and Flyway
- [x] Create initial Flyway migration `V1__init.sql` with tables: `users`, `seasons`, `accounts`
- [x] Run Flyway migrations successfully and confirm tables in DBeaver
- [x] Configure Spring Boot logging and dev/prod profiles
- [x] Set up HikariCP connection pool tuning

---

## Phase 2: Backend Core Functionality

### ✅ Completed
- [x] Create `PortfolioController` + `PortfolioService` + `PortfolioRepository` (basic CRUD, positions)
- [x] Create `AccountController` + `AccountService` + `AccountRepository` (basic CRUD)
- [x] Create `PortfolioPositionController` + `PortfolioPositionService` (CRUD positions)
- [x] Implement `TradeService` for executing BUY/SELL trades
- [x] Fix portfolio retrieval by portfolio ID (previously by user ID)
- [x] Swagger/OpenAPI setup (`springdoc-openapi-starter-webmvc-ui`) ✅ working

### ⬜ Pending

#### 1. User Repository & Service

* Create `UserRepository` with JdbcTemplate (CRUD)
* Create `UserService` for business logic
* Implement password hashing (BCrypt)
* Add `UserController` endpoints (`POST`, `GET`, `PUT`, `DELETE`)
* Unit tests for user repository & service

#### 2. Season Repository & Service

* Create `SeasonRepository` with JdbcTemplate (CRUD)
* Create `SeasonService` for business logic
* Add `SeasonController` endpoints (`POST`, `GET`, `PUT`, `DELETE`)
* Validate season dates in service
* Unit tests for season repository & service

#### 3. Account Repository & Service

* Add business logic in `AccountService` (starting balance, 1 account per user/season)
* Unit tests for account repository & service

#### 4. Leaderboard Queries

* Create leaderboard SQL query per season
* Implement `LeaderboardRepository`/`Service`
* Add API endpoint: `GET /api/leaderboard/season/{seasonId}`
* Unit & integration tests for leaderboard

#### 5. Unit and Integration Tests

* Repository tests with Testcontainers PostgreSQL
* Service tests with mocks
* Controller integration tests using MockMvc

---

## Phase 3: Frontend (React)

### ⬜ Pending
- [ ] Set up React project (Vite or CRA)
- [ ] Implement login, registration forms
- [ ] Implement dashboard showing:
  - [ ] Current season info
  - [ ] User balance
  - [ ] Leaderboard
- [ ] Implement market simulator interface
- [ ] Connect frontend with Spring Boot API (REST)
- [ ] Add competitive/gamified elements (badges, points, ranking)
- [ ] Write frontend tests (Jest/React Testing Library)

---

## Phase 4: SaaS & Payments

### ⬜ Pending
- [ ] Integrate Stripe (or other) for subscription/payment
- [ ] Add subscription tiers (FREE, PRO, PREMIUM)
- [ ] Add backend logic for tier limitations (e.g., number of accounts, starting cash)
- [ ] Add frontend payment UI
- [ ] Add user subscription management endpoints

---

## Phase 5: DevOps / Deployment

### ⬜ Pending
- [ ] Dockerize Spring Boot app
- [ ] Set up CI/CD pipeline (GitHub Actions)
- [ ] Set up production PostgreSQL (AWS RDS, DigitalOcean, or other)
- [ ] Deploy frontend (Netlify, Vercel, or S3+CloudFront)
- [ ] Configure environment variables for production
- [ ] Monitoring/logging setup (Spring Boot Actuator, Grafana/Prometheus optional)

---

## Phase 6: Optional Advanced Features

### ⬜ Pending
- [ ] Real-time stock prices simulation (WebSockets or polling)
- [ ] Social features: friend competitions, challenges
- [ ] Leaderboards by global rank, friends, and season
- [ ] Analytics dashboard for user performance
- [ ] Notifications/alerts for trades or milestones

---

**Current status:**
- Phase 1 fully completed ✅
- Phase 2 in progress, key controllers and services done ✅
- Frontend, SaaS, DevOps, Advanced features pending ⬜
