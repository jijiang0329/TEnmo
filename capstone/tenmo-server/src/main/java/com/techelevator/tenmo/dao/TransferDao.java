package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface TransferDao {
    int getTransferID();
    int accountFrom();
    int accountTo();
    int amount();
    Transfer createTransfer(Transfer transfer);

    Transfer getTransferById(int id);



}
