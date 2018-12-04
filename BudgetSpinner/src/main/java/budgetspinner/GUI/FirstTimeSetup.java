
package budgetspinner.gui;

import budgetspinner.fileio.Write;
import budgetspinner.logic.Logic;
import java.util.HashMap;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Jimi
 */
public class FirstTimeSetup extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Stage stage = new Stage();
        stage.setTitle("Budget Spinner's first time setup");
        stage.setResizable(false);
        
        VBox infoPane = makeInfoPane(stage);
        stage.setScene(new Scene(infoPane));
        stage.showAndWait();
        
        HashMap<String, Double> income = new HashMap<>();
        stage.setScene(new Scene(WindowFactory.makeIncomeExpensePane(stage, true, income)));
        stage.showAndWait();
        
        HashMap<String, Double> expense = new HashMap<>();
        stage.setScene(new Scene(WindowFactory.makeIncomeExpensePane(stage, false, expense)));
        stage.showAndWait();
        
        Logic.initDataFile();
        Write.saveAmountsToFile(income, expense);
        Write.saveTotalAndDateToFile(Logic.calculateDailyAmount(income, expense));
        new MainView().start(stage);
    }
    
    /**
     * Constructs the first time setup information pane and returns it.
     * @param stage This stage will be closed when this pane's button is pressed.
     * @return VBox with the information pane's contents (introduction text, closing button)
     */
    VBox makeInfoPane(Stage stage) {
        VBox infoPane = new VBox();
        infoPane.setAlignment(Pos.CENTER);
        infoPane.setPadding(new Insets(10, 0, 10, 10));
        infoPane.setSpacing(10);
        
        Text infoText = new Text("Welcome to Budget Spinner!"
                + "\nSince it's your first time, let's get to know you, shall we?"
                + "\n\nBudget Spinner is an application for keeping track of your income and spending,"
                + "\ngiving you a visual representation of where your money goes."
                + "\nWe need a little bit of information about your financial status."
                + "\n\nDon't be shy, this will be just between you and me :)");
        infoText.setTextAlignment(TextAlignment.CENTER);
        
        Button confirm = new Button("Let's go!");
        confirm.setOnAction(e -> {
            stage.close();
        });
        
        infoPane.getChildren().addAll(infoText, confirm);
        return infoPane;
    }
    
    
    
    
    
    
}
