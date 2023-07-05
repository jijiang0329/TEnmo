package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {
    int getAccountID();
    int getUserID();

    BigDecimal getBalance(int userID);
}
