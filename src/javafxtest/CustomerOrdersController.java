package javafxtest;

import Fachlogik.CartItem;
import Fachlogik.Customer;
import Fachlogik.Order;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static javafxtest.JavafxTest.showCustomerMenu;
import static javafxtest.LoginController.currentUser;


public class CustomerOrdersController implements Initializable {

    @FXML
    private TableView<Order> tblOrder;
    @FXML
    private TableColumn<?, ?> tcID;
    @FXML
    private TableColumn<?, ?> tcPrice;
    @FXML
    private TableColumn<?, ?> tcStatus;
    @FXML
    private TableColumn<?, ?> tcBouq;
    @FXML
    private TextArea taDetails;
    private Customer cus = null;

    /**
     * Initializes controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cus = (Customer) currentUser;
        ObservableList<Order> items = FXCollections.observableArrayList(cus.getAllOrders());
        tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tcBouq.setCellValueFactory(new PropertyValueFactory<>("bouqute"));
        tblOrder.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblOrder.setItems(items);
        taDetails.setStyle("-fx-font-family: 'monospaced';");

    }

    @FXML
    private void cancel(ActionEvent event) {
        Order ord = tblOrder.getSelectionModel().getSelectedItem();
        int ind = tblOrder.getSelectionModel().getSelectedIndex();
        if (ord == null) {
            Utilities.showError("Bestellung stornieren", "Bestellung auswählen");
        } else {
            boolean confirm = Utilities.getConfirmation("Bist du sicher, die Bestellung zu stornieren?");
            if (confirm) {
                cus.orderCancel(showCustomerMenu.getWareHouse(), ord.getId());
                ord.setStatus("Storniert");
                tblOrder.getItems().set(ind, ord);
                taDetails.setText("");
                Utilities.saveState();
            }

        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void tblClick(MouseEvent event) {
        Order ord = tblOrder.getSelectionModel().getSelectedItem();
        if (ord != null) {
            taDetails.setText("");
            taDetails.appendText(String.format("%-10s%-20s%-20s%-20s%-20s%n", "ID", "Name", "Preis", "Stückzahl", "Preis insgesamt"));
            for (CartItem ci : ord.getOrderItems()) {
                taDetails.appendText(String.format("%-10d%-20s%-20.2f%-20d%-20.2f%n", ci.getId(), ci.getName(), ci.getPrice(), ci.getQuantity(), ci.getPrice() * ci.getQuantity()));
            }
        }
    }

}
