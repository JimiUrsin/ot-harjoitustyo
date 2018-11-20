
package budgetspinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Jimi Ursin
 */
public class Logic {
    private double total;
    private double daily;
    private double available;
    private Scanner userInput = new Scanner(System.in);
    private String currency;
    
    public Logic(String currency) {
        this.userInput = new Scanner(System.in);
        this.currency = currency;
    }
    
    /**
     * This method is supposed to be used after asking the user a yes or no
     * question. It will require input until either a positive "yes" or "y"
     * or a negative "no" or "n" answer is acquired.
     * @return true if answer was yes, false if answer was no
     */
    boolean askYesNo() {
        while (true) {
            String answer = userInput.nextLine().toLowerCase();
            if (answer.equals("yes") || answer.equals("y")) {
                return true;
            } else if (answer.equals("no") || answer.equals("n")) {
                return false;
            } else {
                System.out.println("Yes or no only, please.");
            }
        }
    }
    
    /**
     * This is run when there is no database file, which means that it is this
     * application's first run. It asks for the user's monthly income and
     * expenses. With these, it calculates a monthly and daily allowance for
     * use within the application. The sources of income and expenses are also
     * stored in a file to be used for future allowance calculations.
     * @param db file in which to store the data.
     */
    void firstTimeSetup(File db) {
        // First time setup, create database file and find out monthly income
        try {
            db.createNewFile();
        } catch(IOException e) {
            System.err.println("Failed to create database file, data will not be saved.");
        }
        // Program introduction
        System.out.println("Welcome to Budget Spinner! Since it's your first time, let's get to know you, shall we?");

        System.out.println("\nBudget Spinner is an application for keeping track of your income and spending, giving you a visual representation of where your money goes.");
        System.out.println("We need a little bit of information of your economic status. Don't be shy, this will be just between you and me :)");
        
        // String: Income name, Double: Income amount
        HashMap<String, Double> incomeSources = new HashMap<>();

        System.out.println("Let's add your monthly sources of income. These will be used to calculate your daily allowance.");

        // Add income names and amounts to temporary hashmap, these will be saved to a file later
        while(true) {
            System.out.print("Income source: ");
            String source = userInput.nextLine();
            
            double amount;            
            while(true) {
            // Only breaks when amount is successfully retrieved.
            System.out.print("Income amount: ");
                try {
                    amount = Double.parseDouble(userInput.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Well now, that doesn't seem like a number to me, let's try again.");
                    continue;
                }
                if (amount < 0) {
                    System.out.println("Oof, a negative income? That's called an expense, my friend. We'll get to those later, try again.");
                    continue;
                }
                if (amount == 0) {
                    System.out.println("An income of 0? That doesn't seem like an income at all. Let's try again with a real number, shall we?");
                    continue;
                }
                break;
            }

            System.out.println("\nHere's what you entered:");
            System.out.println("Source of income: " + source);
            System.out.println(String.format("Amount of income: %.2f %s", amount, currency));

            System.out.println("\nIs that right? [Y]es or [N]o");
            if (askYesNo()) {
                if (incomeSources.containsKey(source)) {
                    System.out.println("Hmm, seems like you've added a source of income with this name before. Let's try again.");
                    continue;
                } else {
                    incomeSources.put(source, amount);
                }
                
                System.out.println("Sweet!");
                System.out.println("Want to add more sources of income? [Y]es or [N]o");
                
                if (askYesNo()) continue;
                else break;
            } else {
                System.out.println("Alright, let's try again.");
            }
        }
        
        // String: Expense name, Double: Expense amount
        HashMap<String, Double> expenses = new HashMap<>();
        
        System.out.println("Alright, now same with the expenses.");
        
        // Pretty much a repeat of last one, with different strings
        while(true) {
            System.out.print("Expense: ");
            String source = userInput.nextLine();
            double amount;
            while(true) {
            System.out.print("Expense amount: ");
                try {
                    amount = Double.parseDouble(userInput.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Well now, that doesn't seem like a number to me, let's try again.");
                    continue;
                }
                if (amount < 0) {
                    System.out.println("Oof, a negative expense? That's called income, my friend. Didn't we just go through those?");
                    continue;
                }
                if (amount == 0) {
                    System.out.println("An expense of 0? That doesn't seem like an expense at all. Let's try again with a real number, shall we?");
                    continue;
                }
                break;
            }

            System.out.println();
            System.out.println("Here's what you entered:");
            System.out.println("Expense name: " + source);
            System.out.println(String.format("Expense amount: %.2f %s", amount, currency));

            System.out.println("\nIs that right? [Y]es or [N]o");
            if (askYesNo()) {
                if (expenses.containsKey(source)) {
                    System.out.println("Hmm, seems like you've added an expense with this name before. Let's try again.");
                    continue;
                } else {
                    expenses.put(source, amount);
                }
                System.out.println("Sweet!");
                System.out.println("Want to add more expenses? [Y]es or [N]o");
                if (askYesNo()) continue;
                else break;
            } else {
                System.out.println("Alright, let's try again.");
            }
        }
        
        // Calculate daily and monthly allowance
        double monthly = 0;
        for(String s : incomeSources.keySet()) {
            monthly += incomeSources.get(s);
        }
        
        for(String s : expenses.keySet()) {
            monthly -= expenses.get(s);
        }
        double daily = 12 * monthly / 365;
        
        
        
        System.out.println("Nice, we're all done here.");
        System.out.println("Here's how we're doing:");
        System.out.println(String.format("Monthly allowance: %.2f, %s", monthly, currency));
        System.out.println(String.format("Daily allowance: %.2f %s", daily, currency));
        
        System.out.println("The daily allowance is going to be your baseline, and we'll be tracking its amount throughout the month.");
        System.out.println("When you spend money or get money, be sure to tell me! That way I'll be more accurate in telling you how much money you have available.");
        
        System.out.println("\nAlright, we're done with the setup, let's get to the real deal!");
        
        // Save income and expenses in CSV form
        PrintWriter pw;
        try {
            pw = new PrintWriter(db);
        } catch (FileNotFoundException e) {
            System.err.println("Failed to save monthly income and expenses to file: File not found");
            return;
        }
        
        for(String s : incomeSources.keySet()) {
            pw.write(s + "," + incomeSources.get(s) + "\n");
        }
        pw.write("\n\n");
        for(String s : expenses.keySet()) {
            pw.write(s + "," + expenses.get(s) + "\n");
        }
        
        pw.close();
    }
    
}
