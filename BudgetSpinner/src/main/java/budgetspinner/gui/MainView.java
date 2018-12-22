
package budgetspinner.gui;

import budgetspinner.fileio.Read;
import budgetspinner.fileio.Write;
import budgetspinner.logic.Logic;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Jimi
 */
public class MainView extends Application {
    /** Running total */
    private Double amount;
    
    @Override
    public void start(Stage stage) throws Exception {
        mainView(stage);
    }
    
    /**
     * Constructs and displays the main view
     * @param stage Stage into which the main view will be constructed
     */
    public void mainView(Stage stage) {
        Logic.currency = Read.readCurrencyFromFile();
        
        stage.setTitle("Budget Spinner");
        Label name = new Label("Today's budget");
        name.setFont(new Font("Arial", 24));
        
        
        
        // Running total
        amount = Read.loadRunningTotalFromFile();
        int days = Read.daysElapsedSinceLastRun();
        double dailyIncome = Read.getDailyIncomeFromFile();
        amount += days * dailyIncome;
        Label currentAmount = new Label(String.format("%.2f %s", amount, Logic.currency));
        currentAmount.setFont(new Font("Arial", 40));
        
        // Options gear image        
        URL url = getClass().getResource("/options-gear.png");
        ImageView gearView = new ImageView(url.toString());
        gearView.setOnMouseClicked(e -> {
            try {
                new OptionsMenu().start(new Stage());
            } catch (Exception ex) {
                System.err.println("Unable to open options menu");
            }
            refreshLabelAndStage(currentAmount, stage);
        });
        HBox gearBox = new HBox();
        gearBox.getChildren().add(gearView);
        
        // "Add income" button
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER);
        Button income = new Button("Add income");
        income.setOnAction(e -> {
            Double amt = askForAmount(true);
            if (!amt.equals(-1.0)) {
                amount += amt;                
                refreshLabelAndStage(currentAmount, stage);
            }
        });
        
        // "Add expense" button
        Button expense = new Button("Add expense");
        expense.setOnAction(e -> {
            Double amt = askForAmount(false);
            if (!amt.equals(-1.0)) {
                amount -= amt;
                refreshLabelAndStage(currentAmount, stage);       
            }
        });
        
        buttonGroup.getChildren().addAll(income, expense);
        
        // Main VBox, contains everything else
        VBox mainGroup = new VBox();
        mainGroup.setAlignment(Pos.TOP_CENTER);
        mainGroup.setSpacing(20);
        mainGroup.setPadding(new Insets(5, 0, 0, 5));
        mainGroup.getChildren().addAll(gearBox, name, currentAmount, buttonGroup);
        
        
        
        Scene s = new Scene(mainGroup, mainGroup.getPrefWidth(), mainGroup.getPrefHeight());
        
        stage.setOnHidden(e -> {
            Write.saveAllToFile(amount);
        });
        
        stage.setResizable(false);
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
            if (!amt.equals(-1.0)) {
                return amt;
            } else {                
                WindowFactory.showErrorMessage("Your input was not in the required form.\nYour budget will not be changed.");
            }
        }
        return -1.0;
    }
    
    private void refreshLabelAndStage(Label label, Stage stage) {
        label.setText(String.format("%.2f %s", amount, Logic.currency));
        stage.hide();
        stage.show();
    }
    
}
