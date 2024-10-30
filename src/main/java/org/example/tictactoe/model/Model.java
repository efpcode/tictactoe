package org.example.tictactoe.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static org.example.tictactoe.model.GameState.*;
import static org.example.tictactoe.model.PlayerToken.*;


public class Model {




    private GameState gameState = PAUSED;
    private BoardState boardState;
    List<Player> players = new ArrayList<>();
    private int scoreCross = 0;
    private int scoreCircle = 0;
    private StringProperty pointsCross = new SimpleStringProperty("Score: 0");
    private StringProperty pointsCircle = new SimpleStringProperty("Score: 0");

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public String getPointsCross() {
        return pointsCross.get();
    }

    public StringProperty pointsCrossProperty() {
        return pointsCross;
    }

    public void setPointsCross(String pointsCross) {
        this.pointsCross.set(pointsCross);
    }

    public String getPointsCircle() {
        return pointsCircle.get();
    }

    public StringProperty pointsCircleProperty() {
        return pointsCircle;
    }

    public void setPointsCircle(String pointsCircle) {
        this.pointsCircle.set(pointsCircle);
    }

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
        nodes.forEach(node -> {node.setVisible(true);});
    }

    public void BoardPaneHider(List<Node> nodes){
        nodes.forEach(node -> {node.setVisible(false);});
    }

    public void playerCreated(PlayerToken token, Button button){
        Player player = new Player(new Matrix2dPositions(), token);
        players.add(player);
        button.setText("Yield");

    }

    public void playerYieldLost(PlayerToken token, Button button){
        button.setDisable(true);
        switch (token){
            case CIRCLE -> setPointsCross("Score: "+ ++scoreCross);
            case CROSS -> setPointsCircle("Score: "+ ++scoreCircle);
        }

    }




    public void playerLeftSelected(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        String buttonText = button.getText();
        switch (buttonText){
            case "Select" -> playerCreated(CIRCLE, button);
            case "Yield" -> playerYieldLost(CIRCLE, button);
        }

    }

    public void playerRightSelected(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        String buttonText = button.getText();
        switch (buttonText){
            case "Select" -> playerCreated(CROSS, button);
            case "Yield" -> playerYieldLost(CROSS, button);
        }

    }

}
