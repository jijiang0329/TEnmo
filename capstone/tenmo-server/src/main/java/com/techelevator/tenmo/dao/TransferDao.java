package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    int getTransferID();
    int accountFrom();
    int accountTo();
    int amount();
    Transfer createTransfer(Transfer transfer);

    Transfer getTransferById(int id);
    public int getAccountIdByUserId(int userID);

    String getUserNameByAccountId(int accountID);
    List<Transfer> getTransfersTo(int accountFrom);


}
