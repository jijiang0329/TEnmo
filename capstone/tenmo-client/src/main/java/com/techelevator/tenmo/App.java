package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;

import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AccountService accountService = new AccountService();
    private AuthenticatedUser currentUser;


    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        accountService.setAuthToken(currentUser.getToken());
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
                int transferto = getUserTransferTo();
                if(transferto == 0) continue;

                Transfer transfer = new Transfer();
                transfer.setAmount(getAmountTransferTo());
                transfer.setAccount_to(transferto);
                transfer.setAccount_from(currentUser.getUser().getId());
                accountService.transfer(transfer);
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        // TODO Auto-generated method stub
        System.out.println("Your current account balance is: $2" +
                "" + accountService.getBalance());
        ;
    }

    private void viewTransferHistory() {
        // TODO Auto-generated method stub

    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }

    private void sendBucks() {
        // TODO Auto-generated method stub
        User[] users = accountService.listUsers();
        System.out.println("-------------------------------------------");
        System.out.println("USER ID     " + "NAME");
        System.out.println("-------------------------------------------");
//        for(User user : users) {
//
//            if(!user.getUsername().equals(currentUser.getUser().getUsername())) {
//                System.out.println(user.getId() + "        " + user.getUsername());
//            }
//        }
        for (User user : users) {
            System.out.println(user.getId() + "        " + user.getUsername());
        }
        System.out.println("---------");

    }

    private void requestBucks() {
        // TODO Auto-generated method stub

    }

    private int getUserTransferTo() {
        System.out.println("Enter ID of user you are sending to (0 to cancel): ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        int userId = Integer.parseInt(userInput);
        //TODO make 0 to cancel
        return userId;
    }
    private int getAmountTransferTo() {
        Scanner scanner = new Scanner(System.in);
        int transferAmount = 0;
        System.out.println("Enter amount: ");
        String userInputAmount = scanner.nextLine();
        transferAmount = Integer.parseInt(userInputAmount);

        return  transferAmount;

    }


}
