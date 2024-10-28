package org.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.tictactoe.model.BoardState;
import org.example.tictactoe.model.ChangeLayout;

import java.io.IOException;

import static org.example.tictactoe.model.BoardState.*;

public class GameApplication extends Application {
    final int width = 960;
    final int height = 960;
    private Stage scene;
    private BoardState boardState = UNSET;

    @Override
    public void start(Stage stage) throws IOException {
        scene = stage;

        switchScene("fxml/GameMenu.fxml", "css/gameMenu.css");

        stage.setTitle("Welcome to TicTacToe!");
        stage.show();
    }


    public void switchScene(String fxmlPath, String cssPath) {

         try {
             FXMLLoader gameView = new FXMLLoader(GameApplication.class.getResource(fxmlPath));
             Parent view = gameView.load();
             ChangeLayout controller = gameView.getController();
             controller.setView(this);
             scene.setScene(new Scene(view, width, height));
             scene.getScene().getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
         } catch (IOException e) {
             e.printStackTrace();
         }



     }

    public void setBoardState(BoardState boardState) {
        this.boardState = boardState;
    }

    public BoardState getBoardState() {
        return boardState;
    }


    public static void main(String[] args) {
        launch();
    }
}
