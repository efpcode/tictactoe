package org.example.tictactoe.view.controllers;

import javafx.fxml.FXML;

public class GameMainController {
    @FXML private GameMenuController gameMenuController;
    @FXML private GamePlayController gamePlayController;

    public GameMenuController getGameMenuController() {
        return gameMenuController;
    }
}
