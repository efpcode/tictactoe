package org.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    final int width = 960;
    final int height = 1440;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader gameMenu = new FXMLLoader(GameApplication.class.getResource("game-menu-view.fxml"));
        Parent root = gameMenu.load();
        Scene menuScene = new Scene(root, width, height);


        FXMLLoader gameView = new FXMLLoader(GameApplication.class.getResource("game-view.fxml"));
        Parent view = gameView.load();
        Scene gameScene = new Scene(view, width, height);


        stage.setTitle("Welcome to TicTacToe!");
        stage.setScene(menuScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
