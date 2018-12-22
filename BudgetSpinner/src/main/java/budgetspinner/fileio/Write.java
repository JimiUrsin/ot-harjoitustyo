
package budgetspinner.fileio;

import budgetspinner.logic.Logic;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jimi
 */
public class Write {
    
    /**
     * Private constructor to prevent instancing since this is a static-ish class
     */
    private Write() {
        
    }
    
    /**
     * Writes the given running total amount to the file's first line
     * @param filename File to write to
     * @param amount The running total to be saved to file
     */
    public static void saveRunningTotalToFile(String filename, Double amount) {
        writeContentToLine(filename, new String[]{amount.toString()}, 0);
    }
    
    /**
     * Calls saveRunningTotalToFile with the default data filename
     * @param amount The running total to be saved to file
     */
    public static void saveRunningTotalToFile(Double amount) {
        saveRunningTotalToFile(Logic.DATA_FILENAME, amount);
    }
    
    /**
     * Saves the current timestamp to given file's second line
     * @param filename File to write to
     */
    public static void saveDateToFile(String filename) {
        writeContentToLine(filename, new String[]{String.valueOf(System.currentTimeMillis())}, 1);
    }
    
    /**
     * Calls saveDateToFile with the default data file name
     */
    public static void saveDateToFile() {
        saveDateToFile(Logic.DATA_FILENAME);
    }
    
    /**
     * Save argument's timestamp to the given file.<br>
     * Mostly used for testing
     * @param filename File to write to
     * @param millis Timestamp to write to file
     */
    public static void saveDateToFile(String filename, long millis) {
        writeContentToLine(filename, new String[]{String.valueOf(millis)}, 1);
    }

    /**
     * Saves contents of the two given HashMaps to a file with the given filename in CSV form.
     * @param filename File to write to
     * @param income HashMap of String-Double pairs of income
     * @param expenses HashMap of String-Double pairs of expenses
     */
    public static void saveAmountsToFile(String filename, HashMap<String, Double> income, HashMap<String, Double> expenses) {
        saveIncomeExpenseToFile(filename, income, true);
        saveIncomeExpenseToFile(filename, expenses, false);
    }
    
    /**
     * Calls saveAmountsToFile with the default data filename
     * @param income HashMap of String-Double pairs of income
     * @param expenses HashMap of String-Double pairs of expenses
     */
    public static void saveAmountsToFile(HashMap<String, Double> income, HashMap<String, Double> expenses) {
        saveIncomeExpenseToFile(Logic.DATA_FILENAME, income, true);
        saveIncomeExpenseToFile(Logic.DATA_FILENAME, expenses, false);
    }
    
    /**
     * Saves the contents of the HashMap to a file, in CSV form.
     * @param filename File to save to
     * @param dataMap Data map of String-Double Description-Amount pairs
     * @param forIncome If true, saves the contents to 
     */
    public static void saveIncomeExpenseToFile(String filename, HashMap<String, Double> dataMap, boolean forIncome) {
        String[] data = new String[dataMap.size()];
        int index = 0;
        for (String s : dataMap.keySet()) {
            data[index] = s + "," + dataMap.get(s);
            index++;
        }
        writeContentToLine(filename, data, forIncome ? 2 : 102);
    }

    public static void saveIncomeExpenseToFile(HashMap<String, Double> dataMap, boolean forIncome) {
        saveIncomeExpenseToFile(Logic.DATA_FILENAME, dataMap, forIncome);
    }

    /**
     * Writes the contents of the given dataset to the given line, and fills the rest with newlines
     * @param filename File to write to
     * @param data Data to be written to the file. Newlines will be added to the end of all lines
     * @param lineNumber Line from which the writing will begin
     */
    public static void writeContentToLine(String filename, String[] data, int lineNumber) {
        Path filePath = new File(filename).toPath();
        try {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(filePath);
            for (int i = 0; i < data.length; i++) {
                lines.set(lineNumber + i, data[i]);
            }
            Files.write(filePath, lines);
        } catch (IOException e) {
            System.err.println("Unable to write content to file");
        }
    }
    
    /**
     * Calls writeContentToLine with the default data filename
     * @param data Data to be written to the file. Newlines will be added to the end of all lines
     * @param line Line from which the writing will begin
     */
    public static void writeContentToLine(String[] data, int line) {
        writeContentToLine(Logic.DATA_FILENAME, data, line);
    }
    
    /**
     * Saves the running total, date timestamp and currency to the given file
     * @param filename File to write to
     * @param amount Amount that will be written to the file
     */
    public static void saveAllToFile(String filename, Double amount) {
        saveRunningTotalToFile(filename, amount);
        saveDateToFile(filename);
        saveCurrencyToFile(Logic.currency);
    }
    
    /**
     * Calls saveAllToFile with the default data filename
     * @param amount Amount that will be written to the file
     */
    public static void saveAllToFile(Double amount) {
        saveAllToFile(Logic.DATA_FILENAME, amount);
    }
    
    /**
     * Saves given currency to file with given name. Maintains backwards compatibility with older versions
     * @param filename File to write to
     * @param currency Currency to write to file
     */
    public static void saveCurrencyToFile(String filename, String currency) {
        Path filePath = new File(filename).toPath();
        try {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(filePath);
            if (lines.size() == 202) {
                lines.add(currency);
            } else {
                lines.set(202, currency);
            }
            Files.write(filePath, lines);
        } catch (IOException e) {
            System.err.println("Unable to write currency to file");
        }
    }
    
    /**
     * Calls saveCurrencyToFile with the default data filename
     * @param currency Currency to write to file
     */
    public static void saveCurrencyToFile(String currency) {
        saveCurrencyToFile(Logic.DATA_FILENAME, currency);
    }
}
