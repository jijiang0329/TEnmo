package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "account", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(Principal principal) {
        User user =  userDao.getUserByUsername(principal.getName());
        return accountDao.getBalance(user.getId());
    }
    @RequestMapping(path = "user", method = RequestMethod.GET)
    public List<User> listUsers(Principal principal) {
        List<User> users2 = new ArrayList<>();

        List<User> users = userDao.getUsers();
        for(User user : users) {
            if(!user.getUsername().equals(principal.getName())) {
                users2.add(user);
            }
        }
        if (users == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        } else {
            return users2;
        }
    }
}
