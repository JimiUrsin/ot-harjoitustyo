
package budgetspinner.gui;

import budgetspinner.fileio.Read;
import budgetspinner.fileio.Write;
import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    
    private void optionsMenu(Stage stage) {
        Button income = new Button("Edit income");
        income.setOnAction(e -> {
            showEditMenu(true);
        });
        income.setPrefWidth(120);
        
        Button expense = new Button("Edit expenses");        
        expense.setOnAction(e -> {
            showEditMenu(false);
        });
        expense.setPrefWidth(120);
        
        
        VBox mainGroup = new VBox();
        mainGroup.getChildren().addAll(income, expense);
        Scene s = new Scene(mainGroup, mainGroup.getPrefWidth(), mainGroup.getPrefHeight());
        stage.setScene(s);
        stage.initStyle(StageStyle.UNIFIED);
        stage.setResizable(false);
        stage.showAndWait();
    }
    
    private void showEditMenu(boolean income) {
        Stage s = new Stage();
        HashMap<String, Double> map = Read.loadIncomeExpenseFromFile(income);
        VBox box = WindowFactory.makeIncomeExpensePane(s, income, map);
        s.setScene(new Scene(box));
        s.showAndWait();
        Write.saveIncomeExpenseToFile(map, income);        
    }
    
}
