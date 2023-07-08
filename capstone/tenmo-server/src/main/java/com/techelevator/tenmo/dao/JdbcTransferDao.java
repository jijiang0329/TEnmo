package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int getTransferID() {
        return 0;
    }

    @Override
    public int accountFrom() {
        return 0;
    }

    @Override
    public int accountTo() {
        return 0;
    }

    @Override
    public int amount() {
        return 0;
    }
    @Override
    public List<Transfer> getTransfersTo(int accountFrom) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer Where account_from = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountFrom);
            while (results.next()) {
                Transfer transfer = mapRowToTransfer(results);
                transfers.add(transfer);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfers;
    }
    @Override
    public Transfer getTransferById(int id) {
        Transfer transfer = new Transfer();
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                transfer = mapRowToTransfer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfer;
    }
    public String getUserNameByAccountId(int accountID) {
        //TODO
        String userName = "";
        String sql = "select username\n" +
                "from tenmo_user u\n" +
                "join account a on a.user_id = u.user_id\n" +
                "join transfer t on a.account_id = t.account_to\n" +
                "where t.account_to = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountID);
            if (results.next()) {
                userName = String.valueOf(mapRowToTransfer(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return userName;
    }

    @Override
    public int getAccountIdByUserId(int userID) {
        int accountId = 0;
        String sql = "select account_id\n" +
                "from account a\n" +
                "join tenmo_user u on u.user_id = a.user_id\n" +
                "where a.user_id = ?;";
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
    public Transfer createTransfer(Transfer transfer) {
        Transfer newTransfer = null;

        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id";

        try {
            int newTransferId = jdbcTemplate.queryForObject(sql, int.class, 2, 2,
                    getAccountIdByUserId(transfer.getAccount_from()),
                    getAccountIdByUserId(transfer.getAccount_to()), transfer.getAmount());
            newTransfer = getTransferById(newTransferId);
            if (newTransfer != null) {

                sql = "UPDATE account SET balance=balance - ? WHERE account_id = ? ";
                jdbcTemplate.update(sql, transfer.getAmount(), getAccountIdByUserId(transfer.getAccount_from()) );

                sql = "UPDATE account SET balance=balance + ? WHERE account_id = ? ";
                jdbcTemplate.update(sql, transfer.getAmount(), getAccountIdByUserId(transfer.getAccount_to()) );
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newTransfer;
    }
    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(rs.getInt("transfer_id"));
        transfer.setTransfer_type_id(rs.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(rs.getInt("transfer_status_id"));
        transfer.setAccount_from(rs.getInt("account_from"));
        transfer.setAccount_to(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
}
