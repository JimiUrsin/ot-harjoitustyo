
package budgetspinner.budgetspinner;

import java.io.File;

/**
 *
 * @author Jimi Ursin
 */
public class Main {
    // TODO Parempi nimi luokalle?
    
    public static void main(String[] args) {        
        Logic logic = new Logic("€");
        
        // If data.txt doesn't exist, we know it's the first time this application is being run
        File db = new File("data.txt");
        boolean firstTime = !db.exists();
        
        if (firstTime) logic.firstTimeSetup(db);
    }
}
