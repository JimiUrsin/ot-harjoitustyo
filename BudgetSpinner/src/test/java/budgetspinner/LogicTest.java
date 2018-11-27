package budgetspinner;

import budgetspinner.logic.Logic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;



/**
 *
 * @author Jimi Ursin
 */
public class LogicTest {
    
    @Before
    public void setUp() {
        File f = new File("test.txt");
        f.delete();
    }
    
    @Test
    public void incomeExpenseSavingWorks() throws FileNotFoundException, IOException {
        String filename = "test.txt";
        HashMap<String, Double> income = new HashMap<>();
        income.put("testia", 123.0);
        income.put("testib", 456.0);
        income.put("testic", 789.0);
        
        HashMap<String, Double> expense = new HashMap<>();
        expense.put("testid", 987.0);
        expense.put("testie", 654.0);
        expense.put("testif", 321.0);
        
        Logic.saveAmountsToFile(filename, income, expense);
        
        File f = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(f));
        
        String[] testRows = new String[] {
            "testic,789.0", 
            "testib,456.0",
            "testia,123.0",
            "",
            "",
            "testif,321.0",
            "testie,654.0",
            "testid,987.0"
        };
        
        for(int i = 0; i < testRows.length; i++) {
            String row = br.readLine();
            if (!testRows[i].equals(row)) {
                fail("Row " + i + " was supposed to be " + testRows[i] + " but was: " + row);
            }
        }
    }
    
    @Test
    public void savingRunningTotalWorks() throws FileNotFoundException, IOException {
        Double amount = 123.0;
        Logic.saveTotalAndDateToFile("test.txt", amount);
        
        BufferedReader br = new BufferedReader(new FileReader("test.txt"));
        String line = br.readLine();
        
        if (!line.equals("123.0")) {
            fail("Line was supposed to be 123.0 but was: " + line);
        }
    }
    
    @Test
    public void loadingRunningTotalWorks() {
        Double amount = 123.0;        
        Logic.saveTotalAndDateToFile("test.txt", amount);
        
        Double amount2 = Logic.loadRunningTotalFromFile("test.txt");
        
        if (!amount.equals(amount2)) fail("Amount read from file was supposed to be " + amount + " but was: " + amount2);
    }
    
    @Test
    public void checkAmountWorks() {
        StringBuilder hugeString = new StringBuilder();
        
        Random r = new Random();
        for(int i = 0; i < 100000; i++) {
            hugeString.append((char)('0' + r.nextInt(10)));
        }
        /*
        Test strings
        1. Text
        2. Valid string
        3. Negative value
        4. Text that might be parsed as a double
        5. Same as above
        6. A number that's 100 000 characters long, which becomes infinity
        */
        String[] input = new String[] {
            "test",
            "123.0",
            "-123.0",
            "infinity",
            "NaN",
            hugeString.toString()
        };
        Double[] expectedOutput = new Double[] {
            -1.0,
            123.0,
            -1.0,
            -1.0,
            -1.0,
            -1.0
        };
        
        for(int i = 0; i < input.length; i++) {
            Double output = Logic.checkAmount(input[i]);
            if (!output.equals(expectedOutput[i])) fail("Checking amount " + input[i] + " was supposed to output " + expectedOutput[i] + " but was: " + output);
        }
    }
    
    @Test
    public void calculatingDailyAmountWorks() {
        HashMap<String, Double> income = new HashMap<>();
        income.put("testia", 123.0);
        income.put("testib", 456.0);
        income.put("testic", 789.0);
        income.put("testig", 1000.0);
        
        HashMap<String, Double> expense = new HashMap<>();
        expense.put("testid", 987.0);
        expense.put("testie", 654.0);
        expense.put("testif", 321.0);
        
        Double amount = Logic.calculateDailyAmount(income, expense);
        Double expectedAmount = (123.0 + 456.0 + 789.0 + 1000.0 - 987.0 - 654.0 - 321.0) * 12 / 365;
        if (!amount.equals(expectedAmount)) fail("Daily amount was supposed to be " + expectedAmount + " but was: " + amount);
    }
    
    // TODO First time setup tests
}
