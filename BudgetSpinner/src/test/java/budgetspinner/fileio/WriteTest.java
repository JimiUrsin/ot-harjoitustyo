
package budgetspinner.fileio;

import static budgetspinner.logic.Logic.initDataFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jimi
 */
public class WriteTest {
    private final String testFilename = "testdata";
    
    @Before
    public void setUp() {
        initDataFile(testFilename);
    }
    
    @Test
    public void incomeSavingWorks() throws FileNotFoundException, IOException {
        HashMap<String, Double> income = new HashMap<>();
        income.put("testia", 123.0);
        income.put("testib", 456.0);
        income.put("testic", 789.0);
        
        Write.saveIncomeExpenseToFile(testFilename, income, true);
        List<String> expectedIncome = new ArrayList<>();
        expectedIncome.add("testic,789.0");
        expectedIncome.add("testib,456.0");
        expectedIncome.add("testia,123.0");
        
        List<String> readIncome = budgetspinner.fileio.Read.readContentFromLine(testFilename, 2, 3);
        
        for(int i = 0; i < 3; i++) {
            assertEquals(expectedIncome.get(i), readIncome.get(i));
        }
    }
    
    @Test
    public void expenseSavingWorks() throws FileNotFoundException, IOException {
        HashMap<String, Double> expense = new HashMap<>();
        expense.put("testia", 123.0);
        expense.put("testib", 456.0);
        expense.put("testic", 789.0);
        
        Write.saveIncomeExpenseToFile(testFilename, expense, false);
        List<String> expectedExpense = new ArrayList<>();
        expectedExpense.add("testic,789.0");
        expectedExpense.add("testib,456.0");
        expectedExpense.add("testia,123.0");
        
        List<String> readIncome = budgetspinner.fileio.Read.readContentFromLine(testFilename, 102, 3);
        
        for(int i = 0; i < 3; i++) {
            assertEquals(expectedExpense.get(i), readIncome.get(i));
        }
    }
    
    
    @Test
    public void writingToLineWorks() {
        Write.writeContentToLine(testFilename, new String[] {"test"}, 0);
        String s = Read.readContentFromLine(testFilename, 0, 1).get(0);
        if (!s.equals("test")) fail("Line content was supposed to be \"test\" but was: " + s);
    }
    
    @Test
    public void savingRunningTotalWorks() throws FileNotFoundException, IOException {
        Double amount = 123.0;
        Write.saveAllToFile(testFilename, amount);
        
        String readAmount = Read.readContentFromLine(testFilename, 0, 1).get(0);
        
        if (!readAmount.equals("123.0")) {
            fail("Line was supposed to be 123.0 but was: " + readAmount);
        }
    }
    
    @Test
    public void savingCurrencyWorks() {
        String currency = "TC";
        Write.saveCurrencyToFile(testFilename, currency);
        
        String readCurrency = Read.readCurrencyFromFile(testFilename);
        
        if (!currency.equals(readCurrency)) {
            fail("Currency read from file was supposed to be " + currency + " but was " + readCurrency);
        }
    }
    
    
    
    @Test
    public void gettingDailyIncomeWorks() {        
        
        HashMap<String, Double> income = new HashMap<>();
        income.put("testia", 123.0);
        income.put("testib", 456.0);
        income.put("testic", 789.0);
        
        
        HashMap<String, Double> expense = new HashMap<>();
        expense.put("testia", 123.0);
        expense.put("testib", 456.0);
        
        Write.saveAmountsToFile(testFilename, income, expense);
        
        Double d = 789.0 * 12 / 365;
        Double d2 = Read.getDailyIncomeFromFile(testFilename);
        if (!d.equals(d2)) fail("Daily amount read from file was supposed to be " + d + " but was " + d2);
    }
}
