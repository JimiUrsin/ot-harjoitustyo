
package budgetspinner;

import budgetspinner.GUI.FirstTimeSetup;
import budgetspinner.GUI.MainView;
import java.io.File;

/**
 *
 * @author Jimi Ursin
 */
public class Main {
    // TODO Parempi nimi luokalle?
    
    public static void main(String[] args) throws Exception { 
        
        // If data.txt doesn't exist, we know it's the first time this application is being run
        File db = new File("data.txt");
        if (!db.exists()) {
            FirstTimeSetup.launch(FirstTimeSetup.class);            
        } else {
            MainView.launch(MainView.class);
        }
        
        
    }
}
