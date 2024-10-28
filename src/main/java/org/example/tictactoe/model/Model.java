package org.example.tictactoe.model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.tictactoe.view.controllers.GamePlayController;

import static org.example.tictactoe.model.BoardState.*;


public class Model {





    public void disableRightButton(Button button, BoardState boardState) {
        switch (boardState){
            case PLAYER_VS_COMPUTER, ONLINE_PLAY -> button.setDisable(true);
            case PLAYER_VS_PLAYER, UNSET -> button.setDisable(false);


        }
    }
}
