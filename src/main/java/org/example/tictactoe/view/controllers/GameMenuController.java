package org.example.tictactoe.view.controllers;

import javafx.scene.input.MouseEvent;
import org.example.tictactoe.model.Model;
import static org.example.tictactoe.model.BoardState.*;

public class GameMenuController {
    private Model model = new Model();

    public Model getModel() {
        return model;
    }

    public void gameQuit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void gamePVsP(MouseEvent mouseEvent) {
        model.changeScene(PLAYER_VS_PLAYER);
    }
}
