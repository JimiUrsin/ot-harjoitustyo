
package budgetspinner.gui;

import budgetspinner.fileio.Read;
import budgetspinner.fileio.Write;
import budgetspinner.logic.Logic;
import java.io.File;
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
import javafx.scene.image.Image;
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
    private Double amount;
    
    @Override
    public void start(Stage stage) throws Exception {
        mainView(stage);
    }
    
    public void mainView(Stage stage) throws URISyntaxException {
        stage.setTitle("Budget Spinner");
        Label name = new Label("Budget Spinner");
        name.setFont(new Font("Arial", 24));
        
        // Options gear image
        
        URL url = getClass().getResource("/options-gear.png");
        ImageView gearView = new ImageView(url.toString());
        gearView.setOnMouseClicked(e -> {
            try {
                new OptionsMenu().start(new Stage());
            } catch (Exception ex) {
                System.err.println("Unable to open options menu");
            }
        });
        HBox gearBox = new HBox();
        gearBox.getChildren().add(gearView);
        
        // Running total
        amount = Read.loadRunningTotalFromFile();
        int days = Read.daysElapsedSinceLastRun();
        double dailyIncome = Read.getDailyIncomeFromFile();
        amount += days * dailyIncome;
        Label currentAmount = new Label(String.format("%.2f", amount));
        currentAmount.setFont(new Font("Arial", 40));
        
        // "Add income" button
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER);
        Button income = new Button("Add income");
        income.setOnAction(e -> {
            Double amt = askForAmount(true);
            if (!amt.equals(-1.0)) {
                amount += amt;
                currentAmount.setText(String.format("%.2f", amount));
            }
        });
        
        // "Add expense" button
        Button expense = new Button("Add expense");
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
        mainGroup.setAlignment(Pos.TOP_CENTER);
        mainGroup.setSpacing(20);
        mainGroup.setPadding(new Insets(5, 0, 0, 5));
        mainGroup.getChildren().addAll(gearBox, name, currentAmount, buttonGroup);
        
        
        Scene s = new Scene(mainGroup, mainGroup.getPrefWidth(), mainGroup.getPrefHeight());
        
        stage.setOnHidden(e -> {
            Write.saveTotalAndDateToFile(amount);
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
            }
        }
        return -1.0;
    }
    
}