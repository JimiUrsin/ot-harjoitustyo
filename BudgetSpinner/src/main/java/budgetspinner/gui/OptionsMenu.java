
package budgetspinner.gui;

import budgetspinner.fileio.Read;
import budgetspinner.fileio.Write;
import budgetspinner.logic.Logic;
import java.util.HashMap;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Jimi
 */
public class OptionsMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        optionsMenu(stage);
    }
    
    /**
     * Options menu, contains buttons for editing income/expenses and currency
     * @param stage Stage in which the menu will be constructed
     */
    private void optionsMenu(Stage stage) {
        // Income editing button
        Button income = new Button("Edit income");
        income.setOnAction(e -> {
            showEditMenu(true);
        });
        income.setPrefWidth(120);
        
        // Expense editing button
        Button expense = new Button("Edit expenses");        
        expense.setOnAction(e -> {
            showEditMenu(false);
        });
        expense.setPrefWidth(120);
        
        //Currency editing button
        Button currency = new Button("Set currency");
        currency.setOnAction(e -> {
            String curr = askForCurrency();
            if (curr != null) {
                Logic.currency = curr;
            }
        });
        currency.setPrefWidth(120);
        
        // Main group, contains all buttons of the menu
        VBox mainGroup = new VBox();
        mainGroup.getChildren().addAll(income, expense, currency);
        
        Scene s = new Scene(mainGroup, mainGroup.getPrefWidth(), mainGroup.getPrefHeight());
        stage.setScene(s);
        stage.initStyle(StageStyle.UNIFIED); // Don't remember why I picked this one
        stage.setResizable(false);
        stage.showAndWait();
    }
    
    /**
     * Shows the first time setup income or expense editing window
     * @param income If true, asks for income, expenses otherwise
     */
    private void showEditMenu(boolean income) {
        Stage s = new Stage();
        HashMap<String, Double> map = Read.loadIncomeExpenseFromFile(income);
        VBox box = WindowFactory.makeIncomeExpensePane(s, income, map);
        s.setScene(new Scene(box));
        s.showAndWait();
        Write.saveIncomeExpenseToFile(map, income);        
    }
    
    /**
     * Prompts the user for a string of 1-3 characters, to be used as a currency
     * @return A 1-3 character string if user input was valid, null otherwise
     */
    private String askForCurrency() {
        TextInputDialog userInput = new TextInputDialog();
        userInput.setTitle("Set currency");
        userInput.setHeaderText("Your currency should consist of 1-3 characters");
        userInput.setContentText("Enter currency:");
        
        Optional<String> inputString = userInput.showAndWait();
        if (inputString.isPresent()) {
            String input = inputString.get();
            if (input.length() >= 1 && input.length() <= 3) {
                return input;
            } else {
                WindowFactory.showErrorMessage("The currency you put in was not within the guidelines.\nThe currency was not changed.");
            }
        }
        return null;
    }
    
}
