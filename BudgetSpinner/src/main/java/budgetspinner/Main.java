
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
    
    public static void main(String[] args) throws Exception { 
        File db = new File(Logic.DATA_FILENAME);
        if (!db.exists()) {
            FirstTimeSetup.launch(FirstTimeSetup.class);            
        } else {
            MainView.launch(MainView.class);
        }
        
        
    }
}
