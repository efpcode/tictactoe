package org.example.tictactoe.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.example.tictactoe.model.ChangeLayout;
import org.example.tictactoe.model.Model;
import static org.example.tictactoe.model.BoardState.*;

public class GameMenuController extends ChangeLayout{
    private Model model = new Model();

    public Model getModel() {
        return model;
    }
    @FXML
    public void initialize() {
        Window.getWindows();
    }

    public void gameQuit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void gamePVsP(MouseEvent mouseEvent) {
        model.changeScene(PLAYER_VS_PLAYER);
    }


}
