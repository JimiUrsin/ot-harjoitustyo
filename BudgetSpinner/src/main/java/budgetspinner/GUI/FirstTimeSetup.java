
package budgetspinner.GUI;

import budgetspinner.logic.Logic;
import java.util.HashMap;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Jimi
 */
public class FirstTimeSetup extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Stage stage = new Stage();
        stage.setTitle("Budget Spinner's first time setup");
        stage.setResizable(false);
        
        VBox infoPane = makeInfoPane(stage);
        stage.setScene(new Scene(infoPane));
        stage.showAndWait();
        
        HashMap<String, Double> income = new HashMap<>();
        stage.setScene(new Scene(makeIncomeExpensePane(stage, true, income)));
        stage.showAndWait();
        
        HashMap<String, Double> expense = new HashMap<>();
        stage.setScene(new Scene(makeIncomeExpensePane(stage, false, expense)));
        stage.showAndWait();
        
        Logic.saveIncomeExpenseToFile("data.txt", income, expense);
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
        confirm.setAlignment(Pos.CENTER);
        confirm.setOnAction(e -> {
            stage.close();
        });
        
        infoPane.getChildren().addAll(infoText, confirm);
        return infoPane;
    }
    
    VBox makeIncomeExpensePane(Stage stage, boolean forIncome, HashMap<String, Double> map) {
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
            
            for(Object o : table.getItems()) {
                
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
    
    void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid input");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static class IncomeExpense {

        private final SimpleStringProperty desc;
        private final SimpleStringProperty amount;

        IncomeExpense(String desc, String amount) {
            this.desc = new SimpleStringProperty(desc);
            this.amount = new SimpleStringProperty(amount);
        }    

        public String getDesc() {
            return this.desc.get();
        }

        public void setDesc(String desc) {
            this.desc.set(desc);
        }

        public String getAmount() {
            return this.amount.get();
        }

        public void setAmount(String amount) {
            this.amount.set(amount);
        }    
    }
}
