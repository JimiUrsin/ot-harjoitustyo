
package budgetspinner.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Jimi Ursin
 */
public final class Logic {
    /** Data file to be used for all operations */
    public static final String DATA_FILENAME = "budgetspinner_data";
    
    /** File filler string, since we can't have empty rows. Will be used for comparisons */
    public static final String FILLER = "<e>";
    
    /** Currency to be shown with the daily budget */
    public static String currency = "EUR";
    
    private Logic() {
        
    }    
    
    /**
     * Fills given file with 203 placeholder rows (1 for the running total, 1 for the timestamp of the last run, and 100 each for income/expenses and 1 for the currency
     * @param filename File to write to.
     */
    public static void initDataFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)))) {
            for (int i = 0; i < 202; i++) {
                bw.write(FILLER);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Unable to initialise data file.");
        }
    }
    
    /**
     * Calls initDataFile with the default data filename
     */
    public static void initDataFile() {
        initDataFile(DATA_FILENAME);
    }
    
    
    
    
    /**
     * Tries to parse a given string. It needs to fulfill following requirements<br>
     * 1. It's parseable by parseDouble<br>
     * 2. It's more than zero<br>
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
    
    /**
     * Calculates the daily amount to be added to the user's running total every day<br>
     * Calculated by ((sum of incomes) - (sum of expenses)) * 12 / 365
     * @param income HashMap of income sources
     * @param expenses HashMap of expenses
     * @return The amount to be added daily
     */
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
