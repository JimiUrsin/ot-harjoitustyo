
package budgetspinner.gui;

import budgetspinner.logic.Logic;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Jimi
 */
public class MainView extends Application{
    private Double amount;
    
    @Override
    public void start(Stage stage) throws Exception {
        mainView(stage);
    }
    
    public void mainView(Stage stage) {
        stage.setTitle("Budget Spinner");
        Label name = new Label("Budget Spinner");
        name.setFont(new Font("Arial", 24));
        
        amount = Logic.loadRunningTotalFromFile("running.txt");
        Label currentAmount = new Label(String.format("%.2f", amount));
        currentAmount.setFont(new Font("Arial", 40));
        
        // Buttons for adding a new income or expense
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER);
        Button income = new Button ("Add income");
        income.setOnAction(e -> {
            Double amt = askForAmount(true);
            if (!amt.equals(-1.0)) {
                amount += amt;
                currentAmount.setText(String.format("%.2f", amount));
            }
        });
        
        Button expense = new Button ("Add expense");
        expense.setOnAction(e -> {
            Double amt = askForAmount(false);
            if (!amt.equals(-1.0)) {
                amount -= amt;
                currentAmount.setText(String.format("%.2f", amount));
            }
        });
        
        buttonGroup.getChildren().addAll(income, expense);
        
        // Main VBox, contains everything else
        VBox mainGroup = new VBox();
        mainGroup.setAlignment(Pos.CENTER);
        mainGroup.setSpacing(20);
        mainGroup.getChildren().addAll(name, currentAmount, buttonGroup);
        
        Scene s = new Scene(mainGroup, 250, 350);
        
        stage.setOnHidden(e -> {
            Logic.saveTotalAndDateToFile("running.txt", amount);
        });
        stage.setScene(s);
        stage.show();
    }
    
    Double askForAmount(boolean income) {
        TextInputDialog dialog = new TextInputDialog("Amount");
        dialog.setTitle("Add a new " + (income ? "income" : "expense"));
        dialog.setHeaderText("Enter amount");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            Double amt = Logic.checkAmount(result.get());
            if (!amt.equals(-1.0)) return amt;
        }
        return -1.0;
    }
    
}
