package com.marketwise.marketwise.common;

public class MarketWiseSQL {

  // Account SQL queries
  public static String GET_ACCOUNTS_BY_SEASON = "SELECT * FROM accounts WHERE season_id = ?";

  public static String GET_ACCOUNT_BY_USER_AND_SEASON = "SELECT * FROM accounts WHERE user_id = ? AND season_id = ?";

  public static String CREATE_ACCOUNT = "INSERT INTO accounts (user_id, season_id, cash_balance, create_date) VALUES (?, ?, ?, NOW()) RETURNING id, create_date";

  public static String UPDATE_CASH_BALANCE = "UPDATE accounts SET cash_balance = ? WHERE id = ?";

  // User SQL queries
  public static String GET_USER_BY_ID = "SELECT id, username, email, password_hash, create_date FROM users WHERE id = ?";

  public static String GET_USERS = "SELECT id, username, email, password_hash, create_date FROM users";

  public static String CREATE_USER = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?) RETURNING id, create_date";

  public static String UPDATE_USER = "UPDATE users SET email = ?, password_hash = ? WHERE id = ?";

  public static String DELETE_USER = "DELETE FROM users WHERE id = ?";

  // Season SQL queries
  public static String GET_SEASON_BY_ID = "SELECT * FROM seasons WHERE id = ?";

  public static String GET_SEASONS = "SELECT * FROM seasons ORDER BY start_date DESC";

  public static String CREATE_SEASON = "INSERT INTO seasons (name, start_date, end_date, starting_cash) VALUES (?, ?, ?, ?) RETURNING id, create_date";

  public static String UPDATE_SEASON = "UPDATE seasons SET name = ?, start_date = ?, end_date = ? WHERE id = ?";

  public static String DELETE_SEASON = "DELETE FROM seasons WHERE id = ?";

  // Trade SQL queries
  public static String GET_TRADES_BY_PORTFOLIO = "SELECT * FROM trades WHERE portfolio_id = ?";

  public static String CREATE_TRADE = "INSERT INTO trades (portfolio_id, stock_symbol, shares, price, trade_type, create_date) VALUES (?, ?, ?, ?, ?, NOW()) RETURNING id, create_date";

  // Portfolio SQL queries
  public static String GET_PORTFOLIO_BY_USER = "SELECT * FROM portfolios WHERE user_id = ?";

  public static String GET_PORTFOLIO_BY_PORTFOLIO = "SELECT * FROM portfolios WHERE id = ?";

  public static String CREATE_PORTFOLIO = "INSERT INTO portfolios (user_id, cash_balance, create_date) VALUES (?, ?, NOW()) RETURNING id, create_date, cash_balance";

  public static String CREATE_PORTFOLIO_NO_CASH = "INSERT INTO portfolios (user_id, create_date) VALUES (?, NOW()) RETURNING id, create_date, cash_balance";

  public static String UPDATE_PORTFOLIO_CASH_BALANCE = "UPDATE portfolios SET cash_balance = ? WHERE id = ?";

  // Portfolio Positions SQL queries
  public static String GET_PORTFOLIO_POSITIONS_BY_PORTFOLIO = "SELECT * FROM portfolio_positions WHERE portfolio_id = ?";

  public static String CHECK_PORTFOLIO_POSITION = "SELECT COUNT(*) FROM portfolio_positions WHERE portfolio_id = ? AND stock_symbol = ?";

  public static String CREATE_PORTFOLIO_POSITION = "INSERT INTO portfolio_positions (portfolio_id, stock_symbol, shares, average_price) VALUES (?, ?, ?, ?) RETURNING id";

  public static String UPDATE_PORTFOLIO_POSITION = "UPDATE portfolio_positions SET shares = ?, average_price = ? WHERE portfolio_id = ? AND stock_symbol = ?";

  public static String UPDATE_PORTFOLIO_POSITION_SHARES = "UPDATE portfolio_positions SET shares = ?, average_price = ? WHERE id = ?";

  public static String DELETE_PORTFOLIO_POSITION = "DELETE FROM portfolio_positions WHERE id = ?";

  // Leaderboard SQL queries
  public static String GET_LEADERBOARD_BY_SEASON = """
            "SELECT u.id AS user_id, u.username, 
                   SUM(a.cash_balance + COALESCE(p.total_value, 0)) AS net_worth
            FROM users u
            JOIN accounts a ON a.user_id = u.id
            LEFT JOIN (
                SELECT portfolio_id, SUM(shares * average_price) AS total_value
                FROM portfolio_positions
                GROUP BY portfolio_id
            ) p ON p.portfolio_id = a.id
            WHERE a.season_id = ?
            GROUP BY u.id, u.username
            ORDER BY net_worth DESC
            LIMIT ?
            """;
}