package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    private AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "account", method = RequestMethod.GET)
    public BigDecimal getAccountBalance() {
        return accountDao.getBalance();
    }
}
