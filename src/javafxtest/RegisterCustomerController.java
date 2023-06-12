package javafxtest;

import Fachlogik.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static javafxtest.JavafxTest.applicationState;

public class RegisterCustomerController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void save(ActionEvent event) {
        Customer c = new Customer(tfName.getText(), tfEmail.getText(), tfPassword.getText());
        applicationState.getCustomers().add(c);
        Utilities.showInfo("Regestrierung Kunde", "Erfolgreich Registriert");
        Utilities.saveState();
        tfName.setText("");
        tfEmail.setText("");
        tfPassword.setText("");
        
    }

    private boolean isValid() {
        if (tfName.getText().isEmpty()) {
            Utilities.showError("Regestrierung Kunde", "Bitte Namen eingeben");
            return false;
        } else if (tfEmail.getText().isEmpty()) {
            Utilities.showError("Regestrierung Kunde", "Bitte email eingeben");
            return false;
        } else if (tfPassword.getText().isEmpty()) {
            Utilities.showError("Regestrierung Kunde", "Bitte Passwort eingeben");
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

}
