package org.example.tictactoe.view.controllers;

import javafx.scene.input.MouseEvent;
import org.example.tictactoe.model.ChangeLayout;

import static org.example.tictactoe.model.BoardState.*;


public class GameMenuController extends ChangeLayout{



    public void gameQuit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void gamePVP(MouseEvent mouseEvent) {
        System.out.println(getView().getBoardState());
        getView().switchScene("fxml/GamePlay.fxml");
        getView().setBoardState(PLAYER_VS_PLAYER);
        System.out.println(getView().getBoardState());

    }


}
