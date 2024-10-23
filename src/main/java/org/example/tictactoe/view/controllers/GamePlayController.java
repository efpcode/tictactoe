package org.example.tictactoe.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.tictactoe.model.ChangeLayout;

public class GamePlayController extends ChangeLayout {
    @FXML public Button goBackToMenu;

    public void getGoBackToMenu() {
        getView().switchScene("fxml/GameMenu.fxml");
    }
}
