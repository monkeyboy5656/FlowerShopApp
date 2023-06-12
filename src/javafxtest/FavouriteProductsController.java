package javafxtest;

import Fachlogik.Customer;
import Fachlogik.FavoriteProduct;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static javafxtest.JavafxTest.showCustomerMenu;
import static javafxtest.LoginController.currentUser;


public class FavouriteProductsController implements Initializable {

    @FXML
    private TableView<Product> tblFav;
    @FXML
    private TableColumn<?, ?> tcID;
    @FXML
    private TableColumn<?, ?> tcName;
    @FXML
    private TableColumn<?, ?> tcPrice;
    @FXML
    private TableColumn<?, ?> tcColor;
    @FXML
    private TableColumn<?, ?> tcQuantity;
    @FXML
    private TableColumn<?, ?> tcType;

    /**
     * Initializes controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Customer cus = (Customer) currentUser;
        ObservableList<Product> items = FXCollections.observableArrayList();
        for(FavoriteProduct fp: cus.viewFavorites()){
                items.add(fp.getProduct());
        }
        tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tblFav.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblFav.setItems(items);
    }    

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("WareHouseCustomer.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private void removeFavourites(ActionEvent event) {
        Product p = tblFav.getSelectionModel().getSelectedItem();
        if(p==null){
            Utilities.showError("Entferne Favoriten", "Bitte ein Produkt auswählen!");
        }else{
            Customer cus = (Customer) currentUser;
            cus.removeFavorite(p);
            tblFav.getItems().remove(p);
            Utilities.saveState();
            Utilities.showInfo("Entferne Favoriten", "Erfolgreich entfernt!");
        }
    }
    
}
