package com.github.Lama591divine;

public class ConsoleApp {
    public static void main(String[] args) {
        BankService bankService = new BankService();

        while (true) {
            System.out.println("\n=== Bank System Menu ===");
            System.out.println("1. Create a user");
            System.out.println("2. Show user info");
            System.out.println("3. Manage friends");
            System.out.println("4. Create an account");
            System.out.println("5. Show account balance");
            System.out.println("6. Deposit money");
            System.out.println("7. Withdraw money");
            System.out.println("8. Transfer money");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = bankService.scanner.nextInt();
            bankService.scanner.nextLine();

            switch (choice) {
                case 1 -> bankService.createUser();
                case 2 -> bankService.showUserInfo();
                case 3 -> bankService.manageFriends();
                case 4 -> bankService.createAccount();
                case 5 -> bankService.showBalance();
                case 6 -> bankService.depositMoney();
                case 7 -> bankService.withdrawMoney();
                case 8 -> bankService.transferMoney();
                case 9 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
