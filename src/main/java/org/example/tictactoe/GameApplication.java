package org.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.tictactoe.model.ChangeLayout;

import java.io.IOException;

public class GameApplication extends Application {
    final int width = 960;
    final int height = 1440;
    private Stage scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = stage;

        switchScene("fxml/GameMenu.fxml");

        stage.setTitle("Welcome to TicTacToe!");
        stage.show();
    }

     public void switchScene(String fxmlPath) {

         try {
             FXMLLoader gameView = new FXMLLoader(GameApplication.class.getResource(fxmlPath));
             Parent view = gameView.load();
             ChangeLayout controller = gameView.getController();
             controller.setView(this);
             scene.setScene(new Scene(view, width, height));
         } catch (IOException e) {
             e.printStackTrace();
         }


     }

    public static void main(String[] args) {
        launch();
    }
}
