package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int getAccountID() {
        return 0;
    }

    @Override
    public int getUserID() {
        return 0;
    }

    @Override
    public int getAccountIdByUserId(int userID) {
        int accountId = 0;
        String sql = "SELECT account_id FROM account WHERE user_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
            if (result.next()) {
                accountId = result.getInt("account_id");
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }
        catch (Exception ex) {
            throw new DaoException("Something went wrong!", ex);
        }

        return accountId;
    }


    @Override
    public BigDecimal getBalance(int userID) {
        String balance = "";

        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
            if (result.next()) {
                balance = result.getString("balance");
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }
        catch (Exception ex) {
            throw new DaoException("Something went wrong!", ex);
        }

        return new BigDecimal(balance);
    }
    public int getAccountIdByUser(int userId) {
        int accountId = 0;
        String sql = "SELECT account_id FROM account WHERE user_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            if (result.next()) {
                accountId = result.getInt("account_id");
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }
        catch (Exception ex) {
            throw new DaoException("Something went wrong!", ex);
        }

        return accountId;
    }








}
