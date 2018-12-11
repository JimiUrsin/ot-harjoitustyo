
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
    public static final String DATA_FILENAME = "budgetspinner_data";
    public static final String FILLER = "<e>";
    public static String currency = " EUR";
    
    private Logic() {
        
    }    
    
    
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
    
    public static void initDataFile() {
        initDataFile(DATA_FILENAME);
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
