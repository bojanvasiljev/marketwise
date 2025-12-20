# Marketwise Project Completion Roadmap

This detailed roadmap outlines all tasks to complete the Marketwise stock trading simulator SaaS. Based on `MARKETWISE_PLAN.md`, `MARKETWISE_IDEA.md`, and `MARKETWISE_STATUS.md`. Check off items as completed. Focus on Phase 3 first, then proceed to later phases.

## Phase 1: Setup & Infrastructure ✅ (Completed)
- [x] Create GitHub repository
- [x] Install Java 21 and set up environment
- [x] Initialize Spring Boot project with Maven (spring-boot-starter-web, -security, -data-jdbc, Flyway, PostgreSQL driver)
- [x] Configure Docker for PostgreSQL 15
- [x] Set up `application.yml` for datasource and Flyway
- [x] Create initial Flyway migration `V1__init.sql` with tables: users, seasons, accounts, portfolios, positions, trades
- [x] Run Flyway migrations and confirm tables in DB
- [x] Configure Spring Boot logging and dev profiles
- [x] Tune HikariCP connection pool if needed

## Phase 2: Backend Core Functionality ✅ (Completed)
- [x] Finalize user, season, account tables with constraints
- [x] Add portfolio, positions, trades tables
- [x] Implement JdbcTemplate-based repositories (UserRepository, etc.)
- [x] Create services for Seasons, Accounts, Portfolios, Trades
- [x] Build controllers for Seasons, Accounts, Portfolios, Positions, Trades
- [x] Add global exception handling with JSON errors
- [x] Write integration tests with Testcontainers PostgreSQL
- [x] Enable Swagger/OpenAPI docs at /swagger-ui.html
- [x] Implement leaderboard queries (season-based rankings)
- [x] Secure APIs temporarily (lock down)
- [x] Add authentication & authorization wiring (JWT/session choice)
- [x] Implement final validation rules & edge cases
- [x] Perform performance tuning & query optimization

## Phase 3: Frontend (React/TypeScript) 🚧 (In Progress)
- [ ] Set up React 18 + TypeScript project with Vite
- [ ] Install and configure TanStack Query, Zustand, Tailwind CSS, Axios, ESLint + Prettier
- [ ] Implement login and registration forms with session handling
- [ ] Set up AuthContext, ProtectedRoute for role-based access
- [ ] Create AppLayout with sidebar navigation (Dashboard, Portfolio, Users, Revenue, Settings)
- [ ] Build Dashboard page: display current season info, user balance, leaderboard
- [ ] Implement Portfolio page: show positions, trades, simulate buys/sells
- [ ] Create Users page: admin table for user management (view, update, delete)
- [ ] Add Revenue page: charts for revenue/trading metrics
- [ ] Build Settings page: user preferences and configuration
- [ ] Integrate APIs: connect to backend for users, portfolios, trades via TanStack Query
- [ ] Add mock data in `data/` for development (users, metrics)
- [ ] Implement responsive design with Tailwind utility classes
- [ ] Add Recharts for data visualization (portfolio charts, leaderboards)
- [ ] Write frontend tests (Jest/React Testing Library)
- [ ] Ensure core investment simulation workflows are usable
- [ ] Prepare for Phase 4: foundation for charts and analytics

## Phase 4: SaaS & Payments ⬜ (Pending)
- [ ] Integrate Stripe (or similar) for payment processing
- [ ] Define subscription tiers: FREE, PRO, PREMIUM with limitations
- [ ] Add backend logic for tier restrictions (e.g., max accounts, starting cash)
- [ ] Implement subscription management endpoints (create, update, cancel)
- [ ] Build payment UI in frontend (pricing page, checkout)
- [ ] Add user subscription management in Settings
- [ ] Handle webhooks for payment confirmations
- [ ] Test payment flows end-to-end

## Phase 5: DevOps / Deployment ⬜ (Pending)
- [ ] Dockerize Spring Boot app (create Dockerfile)
- [ ] Set up CI/CD pipeline with GitHub Actions (build, test, deploy)
- [ ] Configure production PostgreSQL (e.g., AWS RDS, DigitalOcean)
- [ ] Deploy backend to cloud (e.g., AWS EC2, Heroku)
- [ ] Deploy frontend (Netlify, Vercel, S3+CloudFront)
- [ ] Set up environment variables for production
- [ ] Add monitoring/logging: Spring Boot Actuator, Grafana/Prometheus
- [ ] Configure domain and SSL certificates
- [ ] Perform load testing and optimize for production

## Phase 6: Optional Advanced Features ⬜ (Pending)
- [ ] Integrate real-time market data (e.g., Alpha Vantage API)
- [ ] Add advanced analytics and reporting (custom charts, AI insights)
- [ ] Implement social features (user profiles, leaderboards sharing)
- [ ] Build mobile app version (React Native or PWA)
- [ ] Add gamification elements (badges, points, notifications)
- [ ] Expand to other instruments (crypto, options)
- [ ] Implement user feedback and support system

## General Tasks
- [x] Update .github/copilot-instructions.md for AI guidance
- [ ] Update README.md with setup and usage instructions
- [ ] Document API endpoints and schemas
- [ ] Add contribution guidelines and code of conduct
- [ ] Perform security audit and penetration testing
- [ ] Gather user feedback and iterate on features

**Next Priority**: Complete Phase 3 frontend tasks. Mark items as done and let me know what to work on next.