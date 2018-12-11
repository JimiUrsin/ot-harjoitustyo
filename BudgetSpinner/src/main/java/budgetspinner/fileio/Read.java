
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

    public static Double loadRunningTotalFromFile(String filename) {
        return Double.parseDouble(readContentFromLine(filename, 0, 1).get(0));
    }

    public static Double loadRunningTotalFromFile() {
        return loadRunningTotalFromFile(Logic.DATA_FILENAME);
    }
    
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
    
    public static HashMap<String, Double> loadIncomeExpenseFromFile(boolean income) {
        return loadIncomeExpenseFromFile(Logic.DATA_FILENAME, income);
    }
    
    public static double getDailyIncomeFromFile(String filename) {
        HashMap<String, Double> income = loadIncomeExpenseFromFile(filename, true);
        HashMap<String, Double> expense = loadIncomeExpenseFromFile(filename, false);
        return Logic.calculateDailyAmount(income, expense);
    }
    
    public static double getDailyIncomeFromFile() {
        return getDailyIncomeFromFile(Logic.DATA_FILENAME);
    }

    /**
     * Reads a number of lines from the given filename, from a certain line
     * @param filename File to read from
     * @param from Line to start reading from
     * @param number Amount of lines to read
     * @return A string array containing the contents of the lines, if file reading failed, returns an empty array
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

    public static List<String> readContentFromLine(int from, int number) {
        return readContentFromLine(Logic.DATA_FILENAME, from, number);
    }

    public static int daysElapsedSinceLastRun(String filename) {
        long time = Long.parseLong(readContentFromLine(filename, 1, 1).get(0));
        int days = 0;
        Calendar before = Calendar.getInstance();
        before.setTimeInMillis(time);
        Calendar now = Calendar.getInstance();
        days += (now.get(Calendar.YEAR) - before.get(Calendar.YEAR)) * 365;
        days += now.get(Calendar.DAY_OF_YEAR) - before.get(Calendar.DAY_OF_YEAR);
        return days;
    }

    public static int daysElapsedSinceLastRun() {
        return daysElapsedSinceLastRun(Logic.DATA_FILENAME);
    }
    
    
    public static String readCurrencyFromFile(String filename) {
        String content = readContentFromLine(filename, 202, 1).get(0);
        if (content.equals(Logic.FILLER) || content.length() < 1 || content.length() > 3) {
            return "EUR";
        } else {
            return content;
        }
    }
    
    public static String readCurrencyFromFile() {
        return readCurrencyFromFile(Logic.DATA_FILENAME);
    }
}
