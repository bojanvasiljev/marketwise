-- ===============================================
-- Phase 2: Marketwise initial schema with constraints
-- ===============================================

-- USERS TABLE
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- PORTFOLIOS TABLE
CREATE TABLE portfolios (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    cash_balance NUMERIC(19,4) NOT NULL DEFAULT 100000.0000,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- PORTFOLIO POSITIONS TABLE
CREATE TABLE portfolio_positions (
    id BIGSERIAL PRIMARY KEY,
    portfolio_id BIGINT NOT NULL REFERENCES portfolios(id) ON DELETE CASCADE,
    stock_symbol VARCHAR(10) NOT NULL,
    shares NUMERIC(19,4) NOT NULL,
    average_price NUMERIC(19,4) NOT NULL,
    UNIQUE(portfolio_id, stock_symbol)
);

-- TRADES TABLE
CREATE TABLE trades (
    id BIGSERIAL PRIMARY KEY,
    portfolio_id BIGINT NOT NULL REFERENCES portfolios(id) ON DELETE CASCADE,
    stock_symbol VARCHAR(10) NOT NULL,
    shares NUMERIC(19,4) NOT NULL,
    price NUMERIC(19,4) NOT NULL,
    trade_type VARCHAR(10) NOT NULL CHECK (trade_type IN ('BUY','SELL')),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- SEASONS TABLE
CREATE TABLE seasons (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_date TIMESTAMPTZ NOT NULL,
    end_date TIMESTAMPTZ NOT NULL,
    starting_cash NUMERIC(19,4) NOT NULL DEFAULT 100000.0000,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ACCOUNTS TABLE (user balance per season)
CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    season_id BIGINT NOT NULL REFERENCES seasons(id) ON DELETE CASCADE,
    cash_balance NUMERIC(19,4) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(user_id, season_id)
);

-- Indexes
CREATE INDEX idx_accounts_user_id ON accounts(user_id);
CREATE INDEX idx_accounts_season_id ON accounts(season_id);
CREATE INDEX idx_seasons_start_end ON seasons(start_date, end_date);
CREATE INDEX idx_users_created_at ON users(created_at);
CREATE INDEX idx_portfolios_user_id ON portfolios(user_id);
CREATE INDEX idx_positions_portfolio_id ON portfolio_positions(portfolio_id);
CREATE INDEX idx_trades_portfolio_id ON trades(portfolio_id);
CREATE INDEX idx_trades_stock_symbol ON trades(stock_symbol);
