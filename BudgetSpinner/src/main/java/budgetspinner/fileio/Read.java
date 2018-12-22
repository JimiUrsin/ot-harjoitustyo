
package budgetspinner.fileio;

import budgetspinner.logic.Logic;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Jimi Ursin
 */
public class Read {
    
    private Read() {
        
    }
    
    /**
     * Attempts to load the running total saved to given data file.
     * @param filename File to read from
     * @return If the read was successful, returns the running total, 0 otherwise.
     */
    public static Double loadRunningTotalFromFile(String filename) {
        try {
            return Double.parseDouble(readContentFromLine(filename, 0, 1).get(0));
        } catch (NumberFormatException e) {
            System.err.println("Unable to read running total from data file.");
            return 0d;
        }
    }
    
    /**
     * Call running total loading method with default data filename.
     * @return If the read was successful, returns the running total, 0 otherwise.
     */
    public static Double loadRunningTotalFromFile() {
        return loadRunningTotalFromFile(Logic.DATA_FILENAME);
    }
    
    /**
     * From the given data file, reads either income or expenses.
     * @param filename File to read from
     * @param income If true, income sources will be read, expenses otherwise
     * @return HashMap containing income/expenses. If the read was unsuccessful, returns an empty HashMap.
     */
    public static HashMap<String, Double> loadIncomeExpenseFromFile(String filename, boolean income) {
        List<String> contents = readContentFromLine(filename, income ? 2 : 102, 100);
        HashMap<String, Double> returnMap = new HashMap<>();
        
        for (String s : contents) {
            if (s.equals(Logic.FILLER)) {
                break;
            }
            String[] split = s.split(",");
            returnMap.put(split[0], Double.parseDouble(split[1]));
        }
        
        return returnMap;
    }
    
    /**
     * Call income/expense loading method with default data filename.
     * @param income If true, income sources will be read, expenses otherwise
     * @return HashMap containing income/expenses. If the read was unsuccessful, returns an empty HashMap.
     */
    public static HashMap<String, Double> loadIncomeExpenseFromFile(boolean income) {
        return loadIncomeExpenseFromFile(Logic.DATA_FILENAME, income);
    }
    
    /**
     * Reads all income sources and expenses, and calculates an amount to be
     * added to the daily budget.
     * @param filename File to read from
     * @return Amount to be added to the daily budget, calculated by calculateDailyAmount
     */
    public static double getDailyIncomeFromFile(String filename) {
        HashMap<String, Double> income = loadIncomeExpenseFromFile(filename, true);
        HashMap<String, Double> expense = loadIncomeExpenseFromFile(filename, false);
        return Logic.calculateDailyAmount(income, expense);
    }
    
    /**
     * Call daily income loading method with default data filename
     * @return Amount to be added to the daily budget, calculated by calculateDailyAmount
     */
    public static double getDailyIncomeFromFile() {
        return getDailyIncomeFromFile(Logic.DATA_FILENAME);
    }

    /**
     * Reads a number of lines from the given filename, from a certain line
     * @param filename File to read from
     * @param from Line to start reading from
     * @param number Amount of lines to read
     * @return A string list containing the contents of the lines, if file reading failed, returns an empty list
     */
    public static List<String> readContentFromLine(String filename, int from, int number) {
        Path p = new File(filename).toPath();
        try {
            return Files.readAllLines(p).subList(from, from + number);
        } catch (IOException e) {
            System.err.println("Unable to read contents from file");
            return new ArrayList<>();
        }
    }
    
    /**
     * Calls readContentFromLine with default data filename.
     * @param from Line to start reading from
     * @param number Amount of lines to read
     * @return A string list containing the contents of the lines, if file reading failed, returns an empty list
     */
    public static List<String> readContentFromLine(int from, int number) {
        return readContentFromLine(Logic.DATA_FILENAME, from, number);
    }
    
    /**
     * Reads the timestamp in the datafile, and calculates how many days have
     * passed since the program was last run based on that.
     * @param filename File to read from
     * @return Number of days that have passed since the program was last run.
     */
    public static int daysElapsedSinceLastRun(String filename) {
        long time;
        try {
            time = Long.parseLong(readContentFromLine(filename, 1, 1).get(0));
        } catch (NumberFormatException e) {
            time = System.currentTimeMillis();
        }
        int days = 0;
        Calendar before = Calendar.getInstance();
        before.setTimeInMillis(time);
        Calendar now = Calendar.getInstance();
        days += (now.get(Calendar.YEAR) - before.get(Calendar.YEAR)) * 365;
        days += now.get(Calendar.DAY_OF_YEAR) - before.get(Calendar.DAY_OF_YEAR);
        return days;
    }
    
    /**
     * Calls daysElapsedSinceLastRun with default data filename
     * @return Number of days that have passed since the program was last run
     */
    public static int daysElapsedSinceLastRun() {
        return daysElapsedSinceLastRun(Logic.DATA_FILENAME);
    }
    
    /**
     * Attempts to read the user's preferred currency from the data file
     * @param filename File to read from
     * @return User's preferred currency if read was successful, EUR otherwise
     */
    public static String readCurrencyFromFile(String filename) {
        String content = readContentFromLine(filename, 202, 1).get(0);
        if (content.equals(Logic.FILLER) || content.length() < 1 || content.length() > 3) {
            return "EUR";
        } else {
            return content;
        }
    }
    
    /**
     * Calls readCurrencyFromFile with the default data filename
     * @return User's preferred currency if read was successful, EUR otherwise
     */
    public static String readCurrencyFromFile() {
        return readCurrencyFromFile(Logic.DATA_FILENAME);
    }
}
