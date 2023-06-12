package javafxtest;

import Datenhaltung.ApplicationState;
import Datenhaltung.SerializationUtil;
import GUI.showCustomerMenu;
import GUI.showOwnerMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavafxTest extends Application {
    
    static ApplicationState applicationState;
    static  showCustomerMenu showCustomerMenu;
    static   showOwnerMenu showOwnerMenu;
    static  String filePath;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * parameter args the command line arguments
     */
    public static void main(String[] args) {
        filePath = "app_state.ser";

        // Load the application state from a file
       applicationState = SerializationUtil.loadAppStateFromFile(filePath);
       if(applicationState==null){
           applicationState=new ApplicationState();
           SerializationUtil.saveAppStateToFile(applicationState, filePath);
       }
        applicationState.updateNextIds();
        // ... continue using the loadedAppState object
        showOwnerMenu=new showOwnerMenu(applicationState.getWarehouse());
        showCustomerMenu=new showCustomerMenu(applicationState.getWarehouse(),applicationState.getOwners());
        launch(args);
    }
    
}
