
package budgetspinner;

import budgetspinner.gui.FirstTimeSetup;
import budgetspinner.gui.MainView;
import java.io.File;

/**
 *
 * @author Jimi Ursin
 */
public class Main {
    // TODO Parempi nimi luokalle?
    
    public static void main(String[] args) throws Exception { 
        
        // If data.txt or running.txt doesn't exist, we know it's the first time this application is being run
        File db = new File("data.txt");
        File running = new File("running.txt");
        if (!db.exists() || !running.exists()) {
            FirstTimeSetup.launch(FirstTimeSetup.class);            
        } else {
            MainView.launch(MainView.class);
        }
        
        
    }
}
