
package budgetspinner;

import budgetspinner.gui.FirstTimeSetup;
import budgetspinner.gui.MainView;
import budgetspinner.logic.Logic;
import java.io.File;

/**
 *
 * @author Jimi Ursin
 */
public class Main {
    
    /**
     * Checks whether or not a data file exists (ergo, if program has been run
     * before) and either runs the main view or the first time setup based on that.
     * @param args Program arguments, not used
     */
    public static void main(String[] args) { 
        File db = new File(Logic.DATA_FILENAME);
        if (!db.exists()) {
            FirstTimeSetup.launch(FirstTimeSetup.class);            
        } else {
            MainView.launch(MainView.class);
        }
        
        
    }
}
