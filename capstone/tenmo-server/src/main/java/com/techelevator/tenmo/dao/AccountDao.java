package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {
    int getAccountID();
    int getUserID();
    BigDecimal getBalance();

    BigDecimal getBalance(int userID);
}
