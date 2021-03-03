package kz.iitu.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("atm")
public class ATM {
    private String currentCardNumber;
    private boolean accessStatus;

    @Autowired
    private Bank bank;
    private Scanner sc;

    public ATM() {}

    @Autowired
    public ATM(Bank bank) {
        this.bank = bank;
        sc = new Scanner(System.in);
    }

    public String getCurrentCardNumber() {
        return currentCardNumber;
    }

    public void setCurrentCardNumber(String currentCardNumber) {
        this.currentCardNumber = currentCardNumber;
    }

    public boolean isAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(boolean accessStatus) {
        this.accessStatus = accessStatus;
    }

    public Bank getBank() {
        return bank;
    }

    @Autowired
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Scanner getSc() {
        return sc;
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

    public void logIn() {
        String cardNumber, pinCode;

        System.out.print("\nInput card number: ");
        cardNumber = sc.next();
        System.out.print("Input pin code: ");
        pinCode = sc.next();

        if (bank.checkCard(cardNumber, pinCode)) {
            accessStatus = true;
            currentCardNumber = cardNumber;
            System.out.println("Valid data.");
        } else {
            System.out.println("Error, invalid data!!!");
        }
    }

    public void withdraw() {
        System.out.print("Input amount: ");
        double amount = sc.nextDouble();

        if (bank.withdraw(amount, currentCardNumber)) {
            System.out.println("In progress.... \nDon't forget to collect the money!");
        } else {
            System.out.println("Error, you don't have enough money in your account!");
        }
    }

    public void topUp() {
        System.out.print("Input amount: ");
        double amount = sc.nextDouble();

        if (amount > 0) {
            if (bank.topUp(amount, currentCardNumber)) {
                System.out.println("In progress.... \nDone!");
            } else {
                System.out.println("Error, try again!!!");
            }
        } else {
            System.out.println("Error, enter a positive money!!!");
        }
    }

    public void changePinCode() {
        System.out.print("Input new pin code: ");
        String pinCode = sc.next();
        bank.changePinCode(pinCode, currentCardNumber);
        System.out.println("New pin code is set.");
    }

    public void showMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("enter 1 - check balance");
        System.out.println("enter 2 - withdraw");
        System.out.println("enter 3 - top up");
        System.out.println("enter 4 - change pin code");
        System.out.println("enter 0 - EXIT");
    }

    public void run() {
        System.out.println("*** Start Bank Application ***");
        boolean stop = false;

        while (!stop) {
            if (currentCardNumber == null) {
                logIn();
            } else {
                if (accessStatus) {
                    showMenu();

                    System.out.print("enter: ");
                    String chose = sc.next();

                    switch (chose) {
                        case "1":
                            System.out.println("You balance: " + bank.checkBalance(currentCardNumber));
                            break;
                        case "2":
                            withdraw();
                            break;
                        case "3":
                            topUp();
                            break;
                        case "4":
                            changePinCode();
                            break;
                        case "0":
                            stop = true;
                            break;
                        default:
                            System.out.println("Invalid argument!");
                    }
                    accessStatus = false;
                } else {
                    System.out.println("\nDo you want to perform the operation again?");
                    System.out.print("enter Yes/No: ");
                    String chose = sc.next();

                    if (chose.equalsIgnoreCase("yes")) {
                        System.out.print("Input pin code: ");
                        String pinCode = sc.next();

                        if (bank.checkCard(currentCardNumber, pinCode)) {
                            accessStatus = true;
                        } else {
                            System.out.println("Error, invalid pin code!!!");
                            stop = true;
                        }
                    } else {
                        stop = true;
                    }
                }
            }
        }
    }
}
