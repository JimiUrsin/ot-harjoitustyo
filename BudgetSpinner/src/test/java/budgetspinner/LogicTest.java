package budgetspinner;

import budgetspinner.Logic;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;



/**
 *
 * @author Jimi Ursin
 */
public class LogicTest {
    Logic logic;
    
    @Before
    public void setUp() {
        logic = new Logic("testdb.txt");
    }
    
    @Test
    public void logicExists() {
        assertTrue(logic != null);
    }
    
    // TODO First time setup tests
}
