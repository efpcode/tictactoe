package org.example.tictactoe.model;

import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.List;


public class Model {


    private BoardState boardState;


    public void gameLoop() {
        // Pseudocode with intended parts.
//        start();
//        pickedPlayers();
//        playerMove();
//        gameResult;

    }

    public BoardState getBoardState() {
        return boardState;
    }

    public void setBoardState(BoardState boardState) {
        this.boardState = boardState;
    }

    public void RightPaneButtonEnabledOrDisabled(Button button, BoardState boardState){
        switch (boardState){
            case PLAYER_VS_COMPUTER, ONLINE_PLAY -> button.setDisable(true);
            case PLAYER_VS_PLAYER, UNSET -> button.setDisable(false);

        }


    }

    public void BoardPaneShower(List<Node> nodes){
        nodes.stream().forEach(node -> {node.setVisible(true);});
    }

    public void BoardPaneHider(List<Node> nodes){
        nodes.stream().forEach(node -> {node.setVisible(false);});
    }


}
