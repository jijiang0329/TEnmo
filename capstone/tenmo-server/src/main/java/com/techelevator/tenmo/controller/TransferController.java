package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    private TransferDao transferDao;
    private AccountDao accountDao;

    private UserDao userDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path= "transfer", method = RequestMethod.POST)
    public Transfer addTransfer(@Valid @RequestBody Transfer transfer) {
        return transferDao.createTransfer(transfer);
    }
    @RequestMapping(path= "transfer", method = RequestMethod.GET)
    public List<Transfer> list(Principal principal) {
        // TODO create list for FROM/TO transfers
        int userID = transferDao.getAccountIdByUserId(userDao.getUserByUsername(principal.getName()).getId());
        int accountFrom = accountDao.getAccountIdByUserId(userID);
        return transferDao.getTransfersTo(accountFrom);
    }
    @RequestMapping(path= "transfer/{id}", method = RequestMethod.GET)
    public Transfer getTransferDetail(@Valid @PathVariable int transferID) {
        Transfer transfer = transferDao.getTransferById(transferID);
        if(transfer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found.");
        } else {
            return transfer;
        }

    }






}



