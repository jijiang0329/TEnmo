package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface TransferDao {
    int getTransferID();
    int accountFrom();
    int accountTo();
    int amount();

}
