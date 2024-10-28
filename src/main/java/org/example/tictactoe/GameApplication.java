package org.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    final int width = 960;
    final int height = 960;
    private Stage scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = stage;
        FXMLLoader gameView = new FXMLLoader(getClass().getResource("fxml/GamePlay.fxml"));
        Parent root = gameView.load();
        scene.setScene(new Scene(root, width, height));
        scene.setTitle("Welcome to TicTacToe!");
        scene.show();
    }




    public static void main(String[] args) {
        launch();
    }
}
