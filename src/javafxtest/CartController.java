package javafxtest;

import Fachlogik.CartItem;
import Fachlogik.Customer;
import Fachlogik.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static javafxtest.JavafxTest.showCustomerMenu;
import static javafxtest.LoginController.currentUser;


public class CartController implements Initializable {

    @FXML
    private TextField tfTotalItems;
    @FXML
    private TextField tfTotalPrice;
    @FXML
    private TableView<CartItem> tblItems;
    @FXML
    private TableColumn<?, ?> tcID;
    @FXML
    private TableColumn<?, ?> tcName;
    @FXML
    private TableColumn<?, ?> tcQuantity;
    @FXML
    private TableColumn<?, ?> tcPrice;
    @FXML
    private TextField tfQuantity;
    private Customer cus;

    /**
     * Initializes controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cus = (Customer) currentUser;
        ObservableList<CartItem> items = FXCollections.observableArrayList(cus.getCart().getCartItems());
        tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblItems.setItems(items);
        setTotal();
    }

    @FXML
    private void removeFromCart(ActionEvent event) {
        CartItem ci = tblItems.getSelectionModel().getSelectedItem();
        if (ci == null) {
            Utilities.showError("Entfernen vom Einkaufswagen", "Wähle ein Produkt zum Entfernen aus!");
        } else {
            tblItems.getItems().remove(ci);
            cus.getCart().deleteItem(ci.getProduct());
            setTotal();
            Utilities.saveState();
            Utilities.showInfo("Entfernen vom Einkaufswagen", "Erfolgreich entfernt");
        }
    }

    @FXML
    private void paceOrder(ActionEvent event) {
        if (tblItems.getItems().isEmpty()) {
            Utilities.showError("Bestellen", "Einkaufswagen ist leer");
        } else {
            boolean val = Utilities.getConfirmation("As a bouqute?");
            cus.placeOrder(showCustomerMenu.getWareHouse(), val);
            Utilities.showInfo("Bestellen", "Erfolgreich bestellt");
            tblItems.getItems().clear();
            tfTotalItems.setText("");
            tfTotalPrice.setText("");
            Utilities.saveState();
            
        }
    }

    @FXML
    private void updateQuantity(ActionEvent event) {
        CartItem ci = tblItems.getSelectionModel().getSelectedItem();
        int ind = tblItems.getSelectionModel().getSelectedIndex();
        if (ci == null) {
            Utilities.showError("Update Stückzahl", "Bitte wähle ein Produkt zum aktualisieren!");
        } else {
            if (tfQuantity.getText().isEmpty()) {
                Utilities.showError("Update Stückzahl", "Bitte gibt die Stückzahl an!");
            } else if (!tfQuantity.getText().matches("\\d+") || (tfQuantity.getText().matches("\\d+") && Integer.parseInt(tfQuantity.getText()) < 1)) {
                Utilities.showError("Update Stückzahl", "Bitte korrekte Stückzahl angeben!");
            } else {
                Utilities.showInfo("Update Stückzahl", "Erfolgreich aktualisiert");
                ci.setQuantity(Integer.parseInt(tfQuantity.getText()));
                tblItems.getItems().set(ind, ci);
                setTotal();
                Utilities.saveState();
            }
        }
    }

    @FXML
    private void tblClick(MouseEvent event) {
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    private void setTotal() {
        tfTotalItems.setText(String.valueOf(tblItems.getItems().size()));
        double total = 0.0;
        for (CartItem it : cus.getCart().getCartItems()) {
            total += it.getPrice() * it.getQuantity();
        }
        tfTotalPrice.setText(String.format("%.2f", total));
    }

}
