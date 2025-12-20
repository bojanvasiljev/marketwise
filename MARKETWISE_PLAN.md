# Marketwise Stock Simulator - 30 Day Plan

This plan outlines the steps to build a competitive, gamified stock market simulator SaaS using Java Spring Boot, PostgreSQL, and React front-end. Each task can be marked as done as we progress.

---

## Phase 1: Setup & Infrastructure

### ✅ Completed

* Create empty GitHub repository
* Install Java 17 (later upgrade to 21)
* Set up VS Code environment
* Initialize Spring Boot project (Maven) with:

  * spring-boot-starter-web
  * spring-boot-starter-security
  * spring-boot-starter-data-jdbc
  * Flyway
  * PostgreSQL driver
* Configure Docker for PostgreSQL (version 15)
* Configure `application.yml` for datasource and Flyway
* Create initial Flyway migration `V1__init.sql` with tables: `users`, `seasons`, `accounts`
* Run Flyway migrations successfully and confirm tables in DBeaver

### ⬜ Left to do

* Configure Spring Boot logging and dev profiles
* Set up HikariCP connection pool tuning if needed

---

## Phase 2: Backend Core Functionality

### ✅ Completed

* User, Season, Account tables finalized with Flyway
* Portfolio, Portfolio Positions, Trades tables added
* JdbcTemplate-based repositories (no JPA)
* Services for Seasons, Accounts, Portfolios, Trades
* Controllers for Seasons, Accounts, Portfolios, Positions, Trades
* Global exception handling with standard error codes
* Integration tests with Testcontainers (PostgreSQL)
* Swagger / OpenAPI documentation enabled
* Leaderboard queries (season-based rankings)
* APIs secured (temporarily locked down)

### ⬜ Left to do

* Authentication & authorization wiring (JWT/session choice)
* Final validation rules & edge cases
* Performance tuning & query optimization

---

## Phase 3: Frontend (React)

### ⬜ Pending

* Set up React project (Vite or CRA)
* Implement login, registration forms
* Implement dashboard showing:

  * Current season info
  * User balance
  * Leaderboard
* Implement market simulator interface
* Connect frontend with Spring Boot API (REST)
* Add competitive/gamified elements (badges, points, ranking)
* Write frontend tests (Jest/React Testing Library)

---

## Phase 4: SaaS & Payments

### ⬜ Pending

* Integrate Stripe (or other) for subscription/payment
* Add subscription tiers (FREE, PRO, PREMIUM)
* Add backend logic for tier limitations (e.g., number of accounts, starting cash)
* Add frontend payment UI
* Add user subscription management endpoints

---

## Phase 5: DevOps / Deployment

### ⬜ Pending

* Dockerize Spring Boot app
* Set up CI/CD pipeline (GitHub Actions)
* Set up production PostgreSQL (AWS RDS, DigitalOcean, or other)
* Deploy frontend (Netlify, Vercel, or S3+CloudFront)
* Configure environment variables for production
* Monitoring/logging setup (Spring Boot Actuator, Grafana/Prometheus optional)

---

## Phase 6: Optional Advanced Features

### ⬜ Pending

* Real-time stock prices simulation (WebSockets or polling)
* Social features: friend competitions, challenges
* Leaderboards by global rank, friends, and season
* Analytics dashboard for user performance
* Notifications/alerts for trades or milestones

---

Current status:

* Phase 1 completed ✅
* Phase 2 largely completed ✅
* Ready to start Phase 3 (React + TypeScript) 🚀
