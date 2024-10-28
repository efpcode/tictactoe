package org.example.tictactoe.view.controllers;

import javafx.scene.input.MouseEvent;
import org.example.tictactoe.model.ChangeLayout;

import static org.example.tictactoe.model.BoardState.*;


public class GameMenuController extends ChangeLayout{



    public void gameQuit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void gamePVP(MouseEvent mouseEvent) {
        getView().switchScene("fxml/GamePlay.fxml", "css/gamePlay.css");
        getView().setBoardState(PLAYER_VS_PLAYER);

    }


    public void gamePVNPC(MouseEvent mouseEvent) {
        getView().switchScene("fxml/GamePlay.fxml", "css/gamePlay.css");
        getView().setBoardState(PLAYER_VS_COMPUTER);
    }

    public void gameOnline(MouseEvent mouseEvent) {
        getView().switchScene("fxml/GamePlay.fxml","css/gamePlay.css");
        getView().setBoardState(ONLINE_PLAY);
    }
}
