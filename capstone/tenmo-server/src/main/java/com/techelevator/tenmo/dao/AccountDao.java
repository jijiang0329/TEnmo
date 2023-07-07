package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {
    int getAccountID();
    int getUserID();

    int getAccountIdByUserId(int userID);

    BigDecimal getBalance(int userID);
}
