package org.example.tictactoe.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GamePlayController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
