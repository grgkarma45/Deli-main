package org.example;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Sandwich {
    private static final double[] SANDWICH_SIZE = {4, 8, 12}; // Available sandwich sizes in inches
    private static final double[] SANDWICH_SIZE_PRICES = {5.50, 7.00, 8.50}; // Prices for each sandwich size
    private static List<String> orderEntries = new ArrayList<>(); // List to store order entries
    public static Scanner scanner = new Scanner(System.in);
    private static double totalPrice = 0.0;


    public static void addSandwich() {

        // Prompt for SANDWICH SIZE
        System.out.println("\u001B[38;2;0;0;255mAdding custom sandwich...\u001B[0m");
        System.out.println("\u001B[38;2;173;216;230mSelect a sandwich size: \nPlease enter a bracket option (number only).\u001B[0m");
        for (int i = 0; i < SANDWICH_SIZE.length; i++) {
            System.out.println("[" + (i + 1) + "] " + SANDWICH_SIZE[i] + "\"");
        }
        int sizeChoice = scanner.nextInt();
        if (sizeChoice < 1 || sizeChoice > SANDWICH_SIZE.length) {
            System.out.println("Invalid choice. Cancelling sandwich addition.");
            return;
        }
        double sandwichSize = SANDWICH_SIZE[sizeChoice - 1];
        double sandwichSizePrice = SANDWICH_SIZE_PRICES[sizeChoice - 1];


        // Prompt for BREAD CHOICE
        System.out.println("\u001B[38;2;173;216;230mSelect a bread: \nPlease enter a bracket option (number only).\u001B[0m");
        for (BreadChoice breadChoice : BreadChoice.values()) {
            System.out.println("[" + (breadChoice.ordinal() + 1) + "] " + breadChoice.getDisplayName());
        }
        int breadChoiceIndex = scanner.nextInt();
        if (breadChoiceIndex < 1 || breadChoiceIndex > BreadChoice.values().length) {
            System.out.println("Invalid choice. Cancelling sandwich addition.");
            return;
        }
        BreadChoice selectedBread = BreadChoice.values()[breadChoiceIndex - 1];
        double breadPrice = selectedBread.getPrice();


        // Prompt for MEAT CHOICE
        System.out.println("\u001B[38;2;173;216;230mSelect meat(s): \nPlease enter a bracket option (number only).\nSeparate by comma, if multiple (e.g. 1,2,3)\u001B[0m");
        for (MeatChoice meatChoice : MeatChoice.values()) {
            System.out.println("[" + (meatChoice.ordinal() + 1) + "] " + meatChoice.getDisplayName());
        }
        String meatsInput = scanner.next();
        String[] meatChoices = meatsInput.split(",");
        List<String> meats = new ArrayList<>();
        double meatPrice = 0.0;

        for (String meatChoice : meatChoices) {
            int choice = Integer.parseInt(meatChoice.trim());
            if (choice >= 1 && choice <= MeatChoice.values().length) {
                MeatChoice selectedMeat = MeatChoice.values()[choice - 1];
                meats.add(selectedMeat.getDisplayName());
                meatPrice += getMeatPriceForSize(selectedMeat.getPrice(), sandwichSize);

            } else {
                System.out.println("Invalid choice. Cancelling sandwich addition.");
                return;
            }
        }

        // Calculates cost of extra meat based on sandwich size
        double extraMeatCost = 0.0;
        if (sandwichSize == 4) {
            extraMeatCost = 0.50;
        } else if (sandwichSize == 8) {
            extraMeatCost = 1.00;
        } else if (sandwichSize == 12) {
            extraMeatCost = 1.50;
        }

        // Prompt for EXTRA MEAT
        System.out.println("\u001B[38;2;173;216;230mAdd extra meat? (y/n)\u001B[0m");
        String extraMeatChoice = scanner.next();
        boolean extraMeat = extraMeatChoice.equalsIgnoreCase("y");
        if (extraMeat) {
            meatPrice += extraMeatCost;
        }


        // Prompt for CHEESE CHOICE
        System.out.println("\u001B[38;2;173;216;230mSelect cheese(s): \nPlease enter a bracket option (number only).\nSeparate by comma, if multiple (e.g. 1,2,3)\u001B[0m");
        for (CheeseChoice cheeseChoice : CheeseChoice.values()) {
            System.out.println("[" + (cheeseChoice.ordinal() + 1) + "] " + cheeseChoice.getDisplayName());
        }

        String cheeseInput = scanner.next();
        String[] cheeseChoices = cheeseInput.split(",");
        List<String> cheese = new ArrayList<>();

        for (String choice : cheeseChoices) {
            int cheeseChoiceIndex = Integer.parseInt(choice.trim());
            if (cheeseChoiceIndex >= 1 && cheeseChoiceIndex <= CheeseChoice.values().length) {
                CheeseChoice selectedCheese = CheeseChoice.values()[cheeseChoiceIndex - 1];
                cheese.add(selectedCheese.getDisplayName());
            } else {
                System.out.println("Invalid choice. Cancelling sandwich addition.");
                return;
            }
        }

        // Calculate cost of Cheese based on sandwich size
        double cheesePrice = 0.0;
        if (sandwichSize == 4) {
            cheesePrice = 0.75;
        } else if (sandwichSize == 8) {
            cheesePrice = 1.50;
        } else if (sandwichSize == 12) {
            cheesePrice = 2.25;
        }

        // Calculate the cost of extra cheese based on bread size
        double extraCheeseCost = 0.0;
        if (sandwichSize == 4) {
            extraCheeseCost = 0.30;
        } else if (sandwichSize == 8) {
            extraCheeseCost = 0.60;
        } else if (sandwichSize == 12) {
            extraCheeseCost = 0.90;
        }


        // Prompt for additional cheese option
        System.out.println("\u001B[38;2;173;216;230mAdd extra cheese? (y/n)\u001B[0m");
        String extraCheeseChoice = scanner.next();
        boolean extraCheese = extraCheeseChoice.equalsIgnoreCase("y");
        if (extraCheese) {
            cheesePrice += extraCheeseCost;
        }

        // Prompt for SAUCE CHOICE
        System.out.println("\u001B[38;2;173;216;230mSelect sauce(s): \nPlease enter a bracket option (number only).\nSeparate by comma, if multiple (e.g. 1,2,3)\u001B[0m");
        for (SauceChoice sauceChoice : SauceChoice.values()) {
            System.out.println("[" + (sauceChoice.ordinal() + 1) + "] " + sauceChoice.getSauceName());
        }
        String saucesInput = scanner.next();
        String[] sauceChoices = saucesInput.split(",");
        List<String> selectedSauces = new ArrayList<>();
        double saucePrice = 0.0;

        for (String sauceChoice : sauceChoices) {
            int choice = Integer.parseInt(sauceChoice.trim());
            if (choice >= 1 && choice <= SauceChoice.values().length) {
                SauceChoice selectedSauce = SauceChoice.values()[choice - 1];
                selectedSauces.add(selectedSauce.getSauceName());
                saucePrice += selectedSauce.getPrice();
            } else {
                System.out.println("Invalid choice. Cancelling sandwich addition.");
                return;
            }
        }
        // Prompt for REGULAR TOPPING CHOICE
        System.out.println("\u001B[38;2;173;216;230mSelect regular topping(s): \nPlease enter a bracket option (number only).\nSeparate by comma, if multiple (e.g. 1,2,3)\u001B[0m");
        for (RegularToppingChoice regularToppingChoice : RegularToppingChoice.values()) {
            System.out.println("[" + (regularToppingChoice.ordinal() + 1) + "] " + regularToppingChoice.getRegulartoppings());
        }
        String regularToppingsInput = scanner.next();
        String[] regularToppingsChoices = regularToppingsInput.split(",");
        List<String> selectedRegularToppings = new ArrayList<>();
        double regularToppingsPrice = 0.0;

        for (String choice : regularToppingsChoices) {
            int regularToppingChoiceIndex = Integer.parseInt(choice.trim());
            if (regularToppingChoiceIndex >= 1 && regularToppingChoiceIndex <= RegularToppingChoice.values().length) {
                RegularToppingChoice selectedRegularTopping = RegularToppingChoice.values()[regularToppingChoiceIndex - 1];
                selectedRegularToppings.add(selectedRegularTopping.getRegulartoppings());
                regularToppingsPrice += selectedRegularTopping.getPrice();
            } else {
                System.out.println("Invalid choice. Cancelling sandwich addition.");
                return;
            }
        }
        // Prompt for SIDE CHOICE
        System.out.println("\u001B[38;2;173;216;230mSelect side(s): \nPlease enter a bracket option (number only).\nSeparate by comma, if multiple (e.g. 1,2,3)\u001B[0m");
        for (SideChoice sideChoice : SideChoice.values()) {
            System.out.println("[" + (sideChoice.ordinal() + 1) + "] " + sideChoice.getSideChoice());
        }
        String sideChoicesInput = scanner.next();
        String[] sideChoicesArray = sideChoicesInput.split(",");
        List<String> selectedSideChoices = new ArrayList<>();
        double sideChoicePrice = 0.0;

        for (String choice : sideChoicesArray) {
            int sideChoiceIndex = Integer.parseInt(choice.trim());
            if (sideChoiceIndex >= 1 && sideChoiceIndex <= SideChoice.values().length) {
                SideChoice selectedSideChoice = SideChoice.values()[sideChoiceIndex - 1];
                selectedSideChoices.add(selectedSideChoice.getSideChoice());
                sideChoicePrice += selectedSideChoice.getPrice();
            } else {
                System.out.println("Invalid choice. Cancelling sandwich addition.");
                return;
            }
        }
        System.out.println("\u001B[38;2;173;216;230mSANDWICH SUMMARY: \u001B[0m");
        System.out.println("Selected sandwich size: " + sandwichSize + "\" Inches");
        System.out.println("Sandwich size price: $" + sandwichSizePrice);
        System.out.println("Selected bread: " + selectedBread.getDisplayName());
        System.out.println("Bread price: $" + breadPrice);
        System.out.println("Selected meats: " + Arrays.toString(meats.toArray()).replace("[", "").replace("]", ""));
        System.out.println("Meat price: $" + meatPrice);
        System.out.println("Selected Cheese: " + Arrays.toString(cheese.toArray()).replace("[", "").replace("]", ""));
        System.out.println("Cheese price: $" + cheesePrice);
        System.out.println("Selected Sauce: " + Arrays.toString(selectedSauces.toArray()).replace("[", "").replace("]", ""));
        System.out.println("Sauce price: $" + saucePrice);
        System.out.println("Selected regular toppings: " + Arrays.toString(selectedRegularToppings.toArray()).replace("[", "").replace("]", ""));
        System.out.println("Regular toppings price: $" + regularToppingsPrice);
        System.out.println("Selected side choices: " + Arrays.toString(selectedSideChoices.toArray()).replace("[", "").replace("]", ""));
        System.out.println("Total side choices price: $" + sideChoicePrice);

        double sandwichTotalPrice = sandwichSizePrice + breadPrice + meatPrice + cheesePrice;
        orderEntries.add("Sandwich - Size: " + sandwichSize + "\" - $" + sandwichTotalPrice);
        totalPrice += sandwichTotalPrice;

        System.out.println("\u001B[38;2;173;216;230mTotal price of sandwich is $" + totalPrice + "\u001B[0m");
        System.out.println("\u001B[38;2;0;0;255mSandwich added to the order.\u001B[0m");
    }

    private static double getMeatPriceForSize(double meatPrice, double sandwichSize) {
        if (sandwichSize == 4) {
            return meatPrice;  // Meat price for 4-inch sandwich
        } else if (sandwichSize == 8) {
            return meatPrice + 1;  // Meat price for 8-inch sandwich
        } else if (sandwichSize == 12) {
            return meatPrice + 2;  // Meat price for 12-inch sandwich
        } else {
            System.out.println("Invalid sandwich size. Default meat price will be used.");
            return meatPrice * 1.00;  // Default meat price
        }
    }


}