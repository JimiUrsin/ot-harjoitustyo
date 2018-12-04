
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
    private Write() {
        
    }

    public static void saveRunningTotalToFile(String filename, Double amount) {
        writeContentToLine(filename, new String[]{amount.toString()}, 0);
    }

    public static void saveRunningTotalToFile(Double amount) {
        saveRunningTotalToFile(Logic.DATA_FILENAME, amount);
    }

    public static void saveDateToFile(String filename) {
        writeContentToLine(filename, new String[]{String.valueOf(System.currentTimeMillis())}, 1);
    }

    public static void saveDateToFile() {
        saveDateToFile(Logic.DATA_FILENAME);
    }
    
    // Test function
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

    public static void saveAmountsToFile(HashMap<String, Double> income, HashMap<String, Double> expenses) {
        saveIncomeExpenseToFile(Logic.DATA_FILENAME, income, true);
        saveIncomeExpenseToFile(Logic.DATA_FILENAME, expenses, false);
    }

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

    public static void writeContentToLine(String[] data, int line) {
        writeContentToLine(Logic.DATA_FILENAME, data, line);
    }

    public static void saveTotalAndDateToFile(String filename, Double amount) {
        saveRunningTotalToFile(filename, amount);
        saveDateToFile(filename);
    }

    public static void saveTotalAndDateToFile(Double amount) {
        saveTotalAndDateToFile(Logic.DATA_FILENAME, amount);
    }
}
