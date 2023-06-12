package javafxtest;

import Fachlogik.Owner;
import Fachlogik.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProductMGTController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfColor;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfQuantity;
    @FXML
    private TableView<Product> tblProducts;
    @FXML
    private TableColumn tcID;
    @FXML
    private TableColumn tcName;
    @FXML
    private TableColumn tcPrice;
    @FXML
    private TableColumn tcColor;
    @FXML
    private TableColumn tcQuantity;
    @FXML
    private TableColumn tcType;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private RadioButton rbID;
    @FXML
    private RadioButton rbType;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnAdd;

    private Owner ow = null;
    @FXML
    private ToggleGroup tgSort;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ow = (Owner) LoginController.currentUser;
        ObservableList<Product> items = FXCollections.observableArrayList(ow.getWh().getProducts());
        tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tblProducts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblProducts.setItems(items);

    }

    @FXML
    private void actionAdd(ActionEvent event) {
        if (validation()) {
            Product p = new Product(tfName.getText(), Double.parseDouble(tfPrice.getText()), tfColor.getText(), tfType.getText(), Integer.parseInt(tfQuantity.getText()));
            ow.addProduct(p);
            tblProducts.getItems().add(p);
            rbID.setSelected(false);
            rbType.setSelected(false);
            Utilities.showInfo("Produkt Hinzufügen", "Erfolgreich hinzugefügt!");
            Utilities.saveState();
            clearFields();
        }
    }

    @FXML
    private void actionDelete(ActionEvent event) {
        Product p = tblProducts.getSelectionModel().getSelectedItem();
        if(p==null){
            Utilities.showError("Produkt löschen", "Bitte Produkt auswählen!");
        }else{
            tblProducts.getItems().remove(p);
            ow.getWh().removeProduct(p);
            clearFields();
            Utilities.saveState();
            Utilities.showInfo("Produkt löschen", "Erfolgreich gelöscht!");
        }
    }

    @FXML
    private void actionUpdate(ActionEvent event) {
        Product p = tblProducts.getSelectionModel().getSelectedItem();
        int ind = tblProducts.getSelectionModel().getSelectedIndex();
        if(p==null){
            Utilities.showError("Update Produkt", "Bitte Produkt auswählen!");
        }else{
            if(validation()){
                p.setName(tfName.getText());
                p.setColor(tfColor.getText());
                p.setType(tfType.getText());
                p.setPrice(Double.parseDouble(tfPrice.getText()));
                p.updateQuantity(Integer.parseInt(tfQuantity.getText()));
                tblProducts.getItems().set(ind, p); 
                Utilities.saveState();
                Utilities.showInfo("Update Produkt", "Erfolgreich aktualisiert!");
            }
        }
    }

    @FXML
    private void actionSortID(ActionEvent event) {
        ow.getWh().getProducts().sort(Comparator.comparing(Product::getId));
        tblProducts.getItems().clear();
        for (Product p : ow.getWh().getProducts()) {
            tblProducts.getItems().add(p);
        }
    }

    @FXML
    private void actionSortType(ActionEvent event) {
        ow.getWh().getProducts().sort((product1, product2) -> product1.getType().compareTo(product2.getType()));
        tblProducts.getItems().clear();
        for (Product p : ow.getWh().getProducts()) {
            tblProducts.getItems().add(p);
        }
    }

    @FXML
    private void actionBack(ActionEvent event) throws IOException {
        Utilities.saveState();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OwnerMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    private boolean validation() {
        if (tfName.getText().isEmpty()) {
            Utilities.showError("Product Management", "Bitte Namen eingeben!");
            return false;
        } else if (tfPrice.getText().isEmpty()) {
            Utilities.showError("Product Management", "Bitte Preis eingeben!");
            return false;
        } else if (tfQuantity.getText().isEmpty()) {
            Utilities.showError("Product Management", "Bitte Stückzahl eingeben!");
            return false;
        } else if (tfColor.getText().isEmpty()) {
            Utilities.showError("Product Management", "Bitte Farbe eingeben!");
            return false;
        } else if (tfType.getText().isEmpty()) {
            Utilities.showError("Product Management", "Bitte Type eingeben!");
            return false;
        } else if (!tfPrice.getText().matches("^[0-9]*\\.?[0-9]+$")) {
            Utilities.showError("Product Management", "Ungültiger Preis!");
            return false;
        } else if (!tfQuantity.getText().matches("\\d+")) {
            Utilities.showError("Product Management", "Ungültige Stückzahl!");
            return false;
        } else if (tfPrice.getText().matches("^[0-9]*\\.?[0-9]+$") && Double.parseDouble(tfPrice.getText()) <= 0) {
            Utilities.showError("Product Management", "Ungültiger Preis!");
            return false;
        } else if (tfQuantity.getText().matches("\\d+") && Integer.parseInt(tfQuantity.getText()) < 1) {
            Utilities.showError("Product Management", "Ungültige Stückzahl!");
            return false;
        }
        return true;
    }

    private void clearFields() {
        tfName.setText("");
        tfQuantity.setText("");
        tfType.setText("");
        tfPrice.setText("");
        tfColor.setText("");
    }

    @FXML
    private void actionPop(MouseEvent event) {
        Product p = tblProducts.getSelectionModel().getSelectedItem();
        if(p!=null){
            tfName.setText(p.getName());
            tfPrice.setText(String.valueOf(p.getPrice()));
            tfQuantity.setText(String.valueOf(p.getQuantity()));
            tfType.setText(p.getType());
            tfColor.setText(p.getColor());
        }
    }

}
