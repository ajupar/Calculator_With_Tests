package nelilaskin;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nelilaskin.ui.MasterController;


public class MainApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
    	
    	  	
    	MasterController masterController = new MasterController(); 	
    	
    	masterController.loadCalculatorUI(stage);    	
    	
    	//    	
//    	// Pelin ajaminen käynnistyy tästä. MasterController huolehtii pelin kulusta.
//    	masterController.loadRootGameUI(stage);

    }

//    static void setRoot(String fxml) throws IOException {
//        scene.setRoot(loadFXML(fxml));
//    }
//    
//    private static Parent loadFXML(String fxml) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxml));
//        return fxmlLoader.load();
//    }

}
