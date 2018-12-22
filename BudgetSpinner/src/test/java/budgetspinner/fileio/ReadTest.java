
package budgetspinner.fileio;

import static budgetspinner.logic.Logic.initDataFile;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jimi
 */
public class ReadTest {
    private final String testFilename = "testdata";
    
    @Before
    public void setUp() {
        initDataFile(testFilename);
    }
    
    @Test
    public void loadingRunningTotalWorks() {
        Double amount = 123.0;
        Write.saveRunningTotalToFile(testFilename, amount);
        
        String amount2 = Read.readContentFromLine(testFilename, 0, 1).get(0);
        
        if (!"123.0".equals(amount2)) fail("Amount read from file was supposed to be " + amount + " but was: " + amount2);
        
        Double amount3 = Read.loadRunningTotalFromFile(testFilename);
        
        if (!amount.equals(amount3)) fail("Amount read from file was supposed to be " + amount + " but was: " + amount3);
    }
    
    @Test
    public void loadingIncomeWorks() {
        HashMap<String, Double> targetMap = new HashMap<>();
        targetMap.put("test", 123.0);
        Write.saveIncomeExpenseToFile(targetMap, true);
        HashMap<String, Double> loadedMap = Read.loadIncomeExpenseFromFile(true);
        if (!loadedMap.get("test").equals(123.0)) fail("Loaded income was supposed to be 123.0 but was " + loadedMap.get("test"));
    }
    
    @Test
    public void loadingExpenseWorks() {
        HashMap<String, Double> targetMap = new HashMap<>();
        targetMap.put("test", 123.0);
        Write.saveIncomeExpenseToFile(targetMap, false);
        HashMap<String, Double> loadedMap = Read.loadIncomeExpenseFromFile(false);
        if (!loadedMap.get("test").equals(123.0)) fail("Loaded income was supposed to be 123.0 but was " + loadedMap.get("test"));
    }
    
    @Test
    public void gettingDailyIncomeWorks() {
        Double targetDaily = 123.0;
        Write.saveRunningTotalToFile(targetDaily);
    }
    
    @Test
    public void exceptionIsCaughtWhileReadingContent() {
        List<String> test = Read.readContentFromLine("nonexistent", 0, 123);
        if (!test.isEmpty()) fail("Read list from nonexistent file was somehow not empty");
    }
    
    @Test
    public void readingCurrencyWorks() {
        String[] input = new String[] {
            "TC",
            "<e>",
            "morethanthreecharacters",
            ""
        };
        String[] expectedOutput = new String[] {
            "TC",
            "EUR",
            "EUR",
            "EUR"
        };
        
        for(int i = 0; i < input.length; i++) {
            Write.saveCurrencyToFile(testFilename, input[i]);
            String read = Read.readCurrencyFromFile(testFilename);
            
            if (!read.equals(expectedOutput[i])) {
                fail("Currency read from file was supposed to be " + expectedOutput[i] + " but was: " + read);
            }
        }
    }
    
    @Test
    public void dayCalculatingWorks() {
        long millis = System.currentTimeMillis();
        millis -= 86_400_000L;
        Write.saveDateToFile(testFilename, millis);
        int days = Read.daysElapsedSinceLastRun(testFilename);
        if (days != 1) fail("Amount of days elapsed since last run was supposed to be 1 but was:" + days);
    }
}
