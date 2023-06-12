package javafxtest;

import Fachlogik.CartItem;
import Fachlogik.Customer;
import Fachlogik.FavoriteProduct;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static javafxtest.JavafxTest.showCustomerMenu;

public class WareHouseCustomerController implements Initializable {

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
    private RadioButton rbColor;
    @FXML
    private ToggleGroup tgSort;
    @FXML
    private RadioButton rbPrice;
    @FXML
    private RadioButton rbType;

    private Customer cus = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cus = (Customer) LoginController.currentUser;
        ObservableList<Product> items = FXCollections.observableArrayList(showCustomerMenu.getWareHouse().getProducts());
        tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tblProducts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblProducts.setItems(items);
        // TODO
    }

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addToFavourites(ActionEvent event) {
        Product p = tblProducts.getSelectionModel().getSelectedItem();
        if (p == null) {
            Utilities.showError("zu Favoriten Hinzufügen", "Bitte wähle ein Produkt aus!");
        } else {
            FavoriteProduct favoriteProduct = new FavoriteProduct();
            cus.addFavorite(favoriteProduct);
            favoriteProduct.setProduct(p, cus);
            Utilities.showInfo("zu Favoriten Hinzufügen", "Erfolgreich Hinzugefügt!");
            Utilities.saveState();
        }
    }

    @FXML
    private void addToCart(ActionEvent event) {
        Product p = tblProducts.getSelectionModel().getSelectedItem();
        if (p == null) {
            Utilities.showError("zum Einkaufswagen Hinzufügen", "Bitte wähle ein Produkt aus!");
        } else {
            cus.getCart().addItem(p, 1);
            Utilities.showInfo("zum Einkaufswagen Hinzufügen", "Erfolgreich Hinzugefügt!");
            Utilities.saveState();
        }
    }

    @FXML
    private void colorSort(ActionEvent event) {
        showCustomerMenu.getWareHouse().getProducts().sort((product1, product2) -> product1.getColor().compareTo(product2.getColor()));
        tblProducts.getItems().clear();
        for (Product p : showCustomerMenu.getWareHouse().getProducts()) {
            tblProducts.getItems().add(p);
        }
    }

    @FXML
    private void priceSort(ActionEvent event) {
        showCustomerMenu.getWareHouse().getProducts().sort(Comparator.comparingDouble(Product::getPrice));
        tblProducts.getItems().clear();
        for (Product p : showCustomerMenu.getWareHouse().getProducts()) {
            tblProducts.getItems().add(p);
        }
    }

    @FXML
    private void typeSort(ActionEvent event) {
        showCustomerMenu.getWareHouse().getProducts().sort((product1, product2) -> product1.getType().compareTo(product2.getType()));
        tblProducts.getItems().clear();
        for (Product p : showCustomerMenu.getWareHouse().getProducts()) {
            tblProducts.getItems().add(p);
        }
    }

}
