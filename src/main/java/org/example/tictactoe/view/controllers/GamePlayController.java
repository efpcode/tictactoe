package org.example.tictactoe.view.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.example.tictactoe.model.BoardState;
import org.example.tictactoe.model.ChangeLayout;
import org.example.tictactoe.model.Model;

public class GamePlayController extends ChangeLayout {
    @FXML
    public void initialize() {


    }

    Model model = new Model();



    public void getGoBackToMenu() {
        getView().switchScene("fxml/GameMenu.fxml", "css/gameMenu.css");
        getView().setBoardState(BoardState.UNSET);
    }

    public void quitGame() {
        System.exit(0);

    }
}
