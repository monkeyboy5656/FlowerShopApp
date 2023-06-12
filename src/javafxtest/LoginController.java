package javafxtest;

import Fachlogik.Customer;
import Fachlogik.Owner;
import Fachlogik.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import static javafxtest.JavafxTest.applicationState;

public class LoginController implements Initializable {

    static User currentUser;
    private Label label;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private RadioButton rbOwner;
    @FXML
    private RadioButton rbCustomer;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnExit;
    @FXML
    private ToggleGroup rbToggle;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("Click!");
        label.setText("Hello!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void registerCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterCustomer.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        if (tfPassword.getText().isEmpty()) {
            Utilities.showError("Login", "Passwort eingeben!");
        } else {
            currentUser = null;
            if (rbCustomer.isSelected()) {
                boolean found = false;

                for (int i = 0; i < applicationState.getCustomers().size(); i++) {
                    if (applicationState.getCustomers().get(i).getPassword().equals(tfPassword.getText())) {
                        currentUser = applicationState.getCustomers().get(i);
                        found = true;
                        break;

                    }
                }
                if (!found) {
                    Utilities.showError("Login","Bitte unter Option 3 regestrieren");
                }
            } else {
                String pass = tfPassword.getText();
                if (JavafxTest.applicationState.getOwners().getPassword().equals(pass)) {
                    currentUser = JavafxTest.applicationState.getOwners();
                } else {
                    Utilities.showError("Login", "Unerlaubter Zugriff");
                }
            }
            if (currentUser instanceof Owner) {
                Utilities.showInfo("Login", "Willkommen Alma :)");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("OwnerMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.show();
            } else if (currentUser instanceof Customer) {
                Utilities.showInfo("Login", "Willkommen " + currentUser.getName());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.show();
            }
        }

    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

}
