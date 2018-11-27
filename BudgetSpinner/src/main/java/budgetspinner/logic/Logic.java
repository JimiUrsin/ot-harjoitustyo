
package budgetspinner.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author Jimi Ursin
 */
public final class Logic {
    
    
    private Logic() {
        
    }
    
    
    /**
     * Saves contents of the two given HashMaps to a file with the given filename in CSV form.
     * @param filename File to save the contents in, if it doesn't yet exist, it will be created.
     * @param income HashMap of String-Double pairs of income
     * @param expenses HashMap of String-Double pairs of expenses
     */
    public static void saveAmountsToFile(String filename, HashMap<String, Double> income, HashMap<String, Double> expenses) {
        PrintWriter pw;
        File f = new File(filename);
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.err.println("Failed to save monthly income and expenses to file: File could not be created");
            return;
        }
        try {
            pw = new PrintWriter(f);
        } catch (FileNotFoundException e) {
            System.err.println("Failed to save monthly income and expenses to file: File not found");
            return;
        }
        
        for (String s : income.keySet()) {
            pw.write(s.replaceAll("(\n|\r)", "") + "," + income.get(s) + "\n");
        }
        pw.write("\n\n");
        for (String s : expenses.keySet()) {
            pw.write(s.replaceAll("(\n|\r)", "") + "," + expenses.get(s) + "\n");
        }
        
        pw.close();
    }
    
    public static void saveTotalAndDateToFile(String filename, Double amount) {        
        PrintWriter pw;
        File f = new File(filename);
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.err.println("Failed to save running total to file: File could not be created");
            return;
        }
        try {
            pw = new PrintWriter(f);
        } catch (FileNotFoundException e) {
            System.err.println("Failed to save running total to file: File not found");
            return;
        }
        
        pw.write(amount.toString());
        pw.write("\n\n");
        pw.write("" + System.currentTimeMillis());
        pw.close();
    }
    
    public static Double loadRunningTotalFromFile(String filename) {
        BufferedReader bf;
        try {
            bf = new BufferedReader(new FileReader(new File(filename)));
        } catch (FileNotFoundException e) {
            System.err.println("Could not load running total from file: File not found");
            return -1.0;
        }
        try {
            return Double.parseDouble(bf.readLine());
        } catch (IOException e) {
            System.err.println("Could not read running total from file");
            return -1.0;
        }
    }
    
    public static int daysElapsedSinceLastRun(String filename) {
        BufferedReader bf;
        try {
            bf = new BufferedReader(new FileReader(new File(filename)));
        } catch (FileNotFoundException e) {
            System.err.println("Could not load date from file: File not found");
            return -1;
        }
        long time = -1;
        try {
            // Skip running total value
            bf.readLine();
            bf.readLine();
            bf.readLine();
            Long.parseLong(bf.readLine());
        } catch (IOException e) {
            System.err.println("Could not read date from file");
            return -1;
        }
        int days = 0;

        Calendar before = Calendar.getInstance();
        before.setTimeInMillis(time);
        Calendar now = Calendar.getInstance();
        days += (now.get(Calendar.YEAR) - before.get(Calendar.YEAR)) * 365;
        days += now.get(Calendar.DAY_OF_YEAR) - before.get(Calendar.DAY_OF_YEAR);
        System.out.println(days);
        return days;
    }
    
    
    /**
     * Tries to parse a given string. It needs to fulfill following requirements
     * 1. It's parseable by parseDouble
     * 2. It's more than zero
     * 3. It's not infinite or NaN
     * @param amountString The string that will be parsed
     * @return If the amount was valid, it will be returned, -1.0 otherwise
     */
    public static Double checkAmount(String amountString) {
        Double amount;
        try {
            amount = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            return -1.0;
        }
        if (amount > 0 && Double.isFinite(amount)) {
            return amount;
        }
        return -1.0;
    }
    
    public static Double calculateDailyAmount(HashMap<String, Double> income, HashMap<String, Double> expenses) {
        double amount = 0;
        for (String s : income.keySet()) {
            amount += income.get(s);
        }
        
        for (String s : expenses.keySet()) {
            amount -= expenses.get(s);
        }
        
        return amount * 12 / 365;
    }
}
