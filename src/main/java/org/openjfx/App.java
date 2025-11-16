package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
public void start(Stage stage) throws Exception {

    System.out.println("DEBUG FXML: " + getClass().getResource("/inventory.fxml"));

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventory.fxml"));
    Scene scene = new Scene(loader.load());

    stage.setScene(scene);
    stage.show();
}


    public static void main(String[] args) {
        launch();
    }
}
