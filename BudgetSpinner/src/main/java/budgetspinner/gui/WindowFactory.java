
package budgetspinner.gui;

import budgetspinner.logic.Logic;
import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Jimi Ursin
 */
public class WindowFactory {

    
    private WindowFactory() {
        
    }
    
    /**
     * Creates the pane which lists recurring income sources and allows the user to add/remove them.
     * @param stage Stage in which the pane will be created
     * @param forIncome Whether to ask for income or expenses
     * @param map Map in which the columns' contents will be written to after the stage is closed.
     * @return VBox containing all the elements of the stage
     */
    static VBox makeIncomeExpensePane(Stage stage, boolean forIncome, HashMap<String, Double> map) {
        String incomeOrExpense = forIncome ? "Income" : "Expense";
        
        Label label = new Label(incomeOrExpense + " setup");
        label.setFont(new Font("Calibri", 20));     
        
        // Table construction
        TableView table = new TableView();
        table.setEditable(true);           
        
        TableColumn income = new TableColumn(incomeOrExpense);
        income.setPrefWidth(200);
        income.setCellValueFactory(
            new PropertyValueFactory<>("desc")
        );
        
        TableColumn amount = new TableColumn("Amount");
        amount.setPrefWidth(125);
        amount.setCellValueFactory(
            new PropertyValueFactory<>("amount")
        );        
        
        table.getColumns().addAll(income, amount);
        
        // Data set of the table view, this will be used when the window is closed.
        ObservableList<IncomeExpense> data = FXCollections.observableArrayList();
        
        // Initialise data set with map contents if applicable.
        for (String s : map.keySet()) {
            data.add(new IncomeExpense(s, map.get(s).toString()));
        }
        table.setItems(data);
        
        // TextFields for adding a new income/expense
        final TextField addDesc = new TextField();
        addDesc.setPromptText(incomeOrExpense);
        addDesc.setMaxWidth(150);
        
        final TextField addAmount = new TextField();
        addAmount.setMaxWidth(100);
        addAmount.setPromptText("Amount");
        
        // Button for adding a new income/expense to the given data list
        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            data.add(new IncomeExpense(
                    addDesc.getText(),
                    addAmount.getText()
            ));
            addDesc.clear();
            addAmount.clear();
        });
        
        // Button for deleting an income/expense from the given data list
        final Button delButton = new Button("Del");
        delButton.setOnAction(e -> {
            table.getItems().remove(table.getSelectionModel().getSelectedItem());
        });
        
        // Button for moving to next phase. Input amounts will be checked for validity
        final Button doneButton = new Button("Done!");
        doneButton.setOnAction(e -> {
            // Check all objects for validity, show an error if one isn't valid
            boolean valid = true;
            map.clear();
            for (Object o : table.getItems()) {
                
                IncomeExpense ie = (IncomeExpense) o;                
                Double amt = Logic.checkAmount(ie.getAmount());
                
                if (amt == -1.0) {
                    showErrorMessage("One or more of the amounts was not in a valid form.");
                    valid = false;
                    break;
                }
                map.put(ie.getDesc(), amt);                        
            }
            
            if (table.getItems().isEmpty()) {
                showErrorMessage("There were no items in the list. Please try again.");
                valid = false;
            }
            if (table.getItems().size() > 100) {
                showErrorMessage("Sorry, but the program currently only supports a maximum of 100 income sources or expenses. Please try again.");
                valid = false;
            }
            if (valid) {
                stage.close();
            } else {                
                map.clear();
            }
        });
        
        // Everything related to adding or deleting rows
        HBox add = new HBox();
        add.getChildren().addAll(addDesc, addAmount, addButton, delButton);
        
        // Main pane, contains all other nodes
        VBox incomeVBox = new VBox();
        incomeVBox.getChildren().addAll(label, table, add, doneButton);
        
        return incomeVBox;
    }
    
    /**
     * Shows an error alert to the user
     * @param message The message to be shown to the user
     */
    static void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid input");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Datatype of the income and expense editing windows
     */
    public static class IncomeExpense {
        
        final SimpleStringProperty amount;
        final SimpleStringProperty desc;

        IncomeExpense(String desc, String amount) {
            this.desc = new SimpleStringProperty(desc);
            this.amount = new SimpleStringProperty(amount);
        }    

        public void setAmount(String amount) {
            this.amount.set(amount);
        }

        public String getDesc() {
            return this.desc.get();
        }

        public String getAmount() {
            return this.amount.get();
        }

        public void setDesc(String desc) {
            this.desc.set(desc);
        }
    }
}

