package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static List<String> orderEntries = new ArrayList<>(); // List to store order entries
    public static Scanner scanner = new Scanner(System.in);
    private static double totalPrice = 0.0;

    public void display() {
        displayHome();
    }

    private void displayHome() {
        System.out.println("\u001B[38;2;0;0;255m ___  ___ _    ___     ___ ___ ___  _   _ ___     \u001B[0m");
        System.out.println("\u001B[38;2;0;0;255m|   \\| __| |  |_ _|__ / __|_ _/ _ \\| | | / __|    \u001B[0m");
        System.out.println("\u001B[38;2;0;0;255m| |) | _|| |__ | |___| (__ | | (_) | |_| \\__ \\    \u001B[0m");
        System.out.println("\u001B[38;2;0;0;255m|___/|___|____|___|__ \\___|___\\___/_\\___/|___/___ \u001B[0m");
        System.out.println("\u001B[38;2;0;0;255m / __| /_\\ | \\| |   \\ \\    / /_ _/ __| || | __/ __|\u001B[0m");
        System.out.println("\u001B[38;2;0;0;255m \\__ \\/ _ \\| .` | |) \\ \\/\\/ / | | (__| __ | _|\\__ \\\u001B[0m");
        System.out.println("\u001B[38;2;0;0;139m |___/_/ \\_\\_|\\_|___/ \\_/\\_/ |___\\___|_||_|___|___/\u001B[0m");
        System.out.println("""
                                
                Welcome to our Deli!
                Please enter a bracket option (number only).
                                
                                MAIN MENU
                ---------------------------------------------
                [1] New Order
                [2] Exit
                """);
        boolean isValidInput = false;
        while (!isValidInput) {
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1":
                    startOrder();
                    break;
                case "2":
                    System.out.println("Thank you! Come again!");
                    System.exit(0);
                default:
                    invalidInput();
                    break;
            }
        }

    }

    private void startOrder() {
        int userInput;
        do {
            horizontalLine();
            System.out.println("""
                    Try the best sandwiches in town!
                    Please enter a bracket option (number only).
                     
                                       ORDER MENU
                    ---------------------------------------------
                    [1] Add Custom Sandwich
                    [2] Add Signature Sandwich
                    [3] Add Drink
                    [4] Add Chips
                    [5] Checkout
                    [0] Exit
                    """);
            while (!scanner.hasNextInt()) {
                invalidInput();
                scanner.nextLine(); // Consume invalid input
            }
            userInput = scanner.nextInt();

            switch (userInput) {
                case 1 -> addSandwich();
                case 2 -> addSignatureSandwich();
                case 3 -> addDrink();
                case 4 -> addChips();
                case 5 -> checkOut();
                case 0 -> cancelOrder();
                default -> invalidInput();
            }
        } while (userInput != 0);
    }
    private static void addSandwich() {
        Sandwich.addSandwich();

    }


    private void addSignatureSandwich() {
        System.out.println("\u001B[38;2;0;0;255mAdding signature sandwich...\u001B[0m");
        System.out.println("\u001B[38;2;173;216;230mSelect a sandwich: \nPlease enter a bracket option (number only).\u001B[0m");
        for (SignatureSandwichChoice sandwichChoice : SignatureSandwichChoice.values()) {
            System.out.println("[" + (sandwichChoice.ordinal() + 1) + "] " + sandwichChoice.getDisplayName() + " - $" + sandwichChoice.getPrice());
        }

        int choice = scanner.nextInt();
        if (choice < 1 || choice > SignatureSandwichChoice.values().length) {
            System.out.println("Invalid choice. Signature sandwich not added to the order.");
            return;
        }

        SignatureSandwichChoice selectedSandwich = SignatureSandwichChoice.values()[choice - 1];
        double sandwichPrice = selectedSandwich.getPrice();

        orderEntries.add("Signature Sandwich - " + selectedSandwich.getDisplayName() + ": $" + sandwichPrice);
        totalPrice += sandwichPrice;

        System.out.println("Selected signature sandwich: " + selectedSandwich.getDisplayName() + "\nSignature sandwich price: $" + sandwichPrice);
        System.out.println("\u001B[38;2;0;0;255mSignature sandwich added to the order.\u001B[0m");
    }

    private static void addDrink() {
        System.out.println("\u001B[38;2;0;0;255mAdding drink...\u001B[0m");
        System.out.println("\u001B[38;2;173;216;230mSelect drink size: \nPlease enter a bracket option (number only).\u001B[0m");
        for (DrinkChoice drinkChoice : DrinkChoice.values()) {
            System.out.println("[" + (drinkChoice.ordinal() + 1) + "] " + drinkChoice.getSize() + " - $" + drinkChoice.getPrice());
        }

        int sizeChoice = scanner.nextInt();
        if (sizeChoice < 1 || sizeChoice > DrinkChoice.values().length) {
            System.out.println("Invalid choice. Cancelling drink addition.");
            return;
        }

        DrinkChoice selectedDrink = DrinkChoice.values()[sizeChoice - 1];
        double drinkPrice = selectedDrink.getPrice();

        System.out.println("\u001B[38;2;173;216;230mSelect the drink flavor: \u001B[0m");
        for (FlavorChoice flavorChoice : FlavorChoice.values()) {
            System.out.println("[" + (flavorChoice.ordinal() + 1) + "] " + flavorChoice.getFlavor() + " - $" + flavorChoice.getPrice());
        }

        int flavorChoice = scanner.nextInt();
        if (flavorChoice < 1 || flavorChoice > FlavorChoice.values().length) {
            System.out.println("Invalid choice. Cancelling drink addition.");
            return;
        }

        FlavorChoice selectedFlavor = FlavorChoice.values()[flavorChoice - 1];
        double flavorPrice = selectedFlavor.getPrice();


        orderEntries.add("Drink - " + selectedDrink.getSize() + " - $" + drinkPrice);
        totalPrice += drinkPrice;

        System.out.println("Selected drink: " + selectedDrink.getSize() + " " + selectedFlavor.getFlavor() + "\nDrink price: $" + drinkPrice);
        System.out.println("\u001B[38;2;0;0;255mDrink added to the order.\u001B[0m");
    }

    private static void addChips() {
        System.out.println("\u001B[38;2;0;0;255mAdding chips...\u001B[0m");
        System.out.println("\u001B[38;2;173;216;230mSelect chips: \nPlease enter a bracket option (number only).\u001B[0m");

        String[] chipsOptions = {"Original", "BBQ", "Salt and Vinegar", "Sour Cream and Onion"};
        double[] chipsPrices = {1.50, 1.75, 1.75, 1.75};

        for (int i = 0; i < chipsOptions.length; i++) {
            System.out.println("[" + (i + 1) + "] " + chipsOptions[i] + " - $" + chipsPrices[i]);
        }

        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= chipsOptions.length) {
            orderEntries.add(chipsOptions[choice - 1] + " - $" + chipsPrices[choice - 1]);
            totalPrice += chipsPrices[choice - 1];
            System.out.println("That will be an extra $" + chipsPrices[choice - 1] + ".");
            System.out.println("\u001B[38;2;0;0;255m"+chipsOptions[choice - 1] + " chips added to the order.\u001B[0m");
        } else {
            System.out.println("Invalid choice. Chips not added to the order.");
        }
    }

    private static void checkOut() {

        System.out.println("\u001B[38;2;0;0;255mOrder Summary: \u001B[0m");
        for (String entry : orderEntries) {
            System.out.println(entry);
        }
        System.out.println("\u001B[38;2;173;216;230mTotal Price: $" + totalPrice + "\u001B[0m");
        try {
            // Create the directory if it doesn't exist
            String directoryPath = "Receipts";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdir();
            }
            // Generate the file name using the current date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH.mm.ss");
            String fileName = directoryPath + "/" + now.format(formatter) + ".csv";

            // Write the receipt to the file
            FileWriter writer = new FileWriter(fileName);
            writer.write("DELI-cious Sandwiches Receipt\n");
            writer.write(now.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")) + "\n");
            writer.write(now.format(DateTimeFormatter.ofPattern("HH:mm")) + "\n");
            writer.write("---------------------------------------------\n");
            writer.write("Item\t\t\tItem Price\n");
            for (String entry : orderEntries) {
                writer.write(entry + "\n");
            }
            writer.write("---------------------------------------------\n");
            writer.write("Total Price: " + totalPrice + "\n");
            writer.close();

            System.out.println("\u001B[38;2;0;0;255mReceipt saved successfully!\u001B[0m");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the receipt.");
            e.printStackTrace();
        }
    }

    private void cancelOrder() {
        System.out.println("Thank you! Have a good day!");
        System.exit(0);
    }

    public void invalidInput() {
        System.out.println("\u001B[38;2;139;0;0mInvalid input. Please try again.\u001B[0m");
    }

    public void horizontalLine() {
        System.out.println("\u001B[38;2;0;0;139m-----------------------------------------------------------------------------------------------------------------\u001B[0m");
    }

}