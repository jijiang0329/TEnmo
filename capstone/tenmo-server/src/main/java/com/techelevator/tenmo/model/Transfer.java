package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Transfer {
    private int transfer_id;
    private int transfer_type_id;
    private int transfer_status_id;

    private int account_from;
    @NotNull(message =  "Transfer to account must not be blank ")
    private int account_to;
    @NotNull(message =  "Transfer amount must not be blank ")
    private BigDecimal amount;

    private static String TRANSFER_STATUS_PENDING = "Pending";
    private static  int TRANSFER_STATUS_PENDING_ID = 1;
    private static String TRANSFER_STATUS_APPROVED = "Approved";
    private static  int TRANSFER_STATUS_APPROVED_ID = 2;


    public Transfer() { }

    public Transfer(int transfer_id, int transfer_type_id, int account_from, int account_to, BigDecimal amount) {
        this.transfer_id = transfer_id;
        this.transfer_type_id = transfer_type_id;
        this.transfer_status_id = TRANSFER_STATUS_APPROVED_ID;
        this.account_from = account_from;
        this.account_to = account_to;
        this.amount = amount;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public int getTransfer_type_id() {
        return transfer_type_id;
    }

    public void setTransfer_type_id(int transfer_type_id) {
        this.transfer_type_id = transfer_type_id;
    }

    public int getTransfer_status_id() {
        return transfer_status_id;
    }

    public void setTransfer_status_id(int transfer_status_id) {
        this.transfer_status_id = transfer_status_id;
    }

    public int getAccount_from() {
        return account_from;
    }

    public void setAccount_from(int account_from) {
        this.account_from = account_from;
    }

    public int getAccount_to() {
        return account_to;
    }

    public void setAccount_to(int account_to) {
        this.account_to = account_to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
