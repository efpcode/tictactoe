package org.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.tictactoe.model.Sceneable;

import java.io.IOException;

public class GameApplication extends Application implements Sceneable {
    final int width = 960;
    final int height = 1440;
    private Stage scene;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader gameMenu = new FXMLLoader(GameApplication.class.getResource("fxml/GameMenu.fxml"));
        Parent root = gameMenu.load();
        Scene menuScene = new Scene(root, width, height);


        FXMLLoader gameView = new FXMLLoader(GameApplication.class.getResource("fxml/GamePlay.fxml"));
        Parent view = gameView.load();
        Scene gameScene = new Scene(view, width, height);


        stage.setTitle("Welcome to TicTacToe!");
        stage.setScene(menuScene);
        stage.show();
    }

    @Override
    public Stage getScene() {
        return this.scene;
    }

    public void setScene(Stage stage) {
        this.scene = stage;
    }

    public static void main(String[] args) {
        launch();
    }
}
