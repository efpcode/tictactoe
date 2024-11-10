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

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader gameView = new FXMLLoader(getClass().getResource("fxml/GamePlay.fxml"));
        Parent root = gameView.load();
        stage.setScene(new Scene(root, width, height));
        stage.setTitle("Welcome to TicTacToe!");
        stage.show();
    }




    public static void main(String[] args) {
        launch();
    }
}
