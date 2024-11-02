package org.example.tictactoe.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.example.tictactoe.model.GameState.*;
import static org.example.tictactoe.model.PlayerToken.*;


public class Model {
    private static final int NUMBER_OF_PLAYERS = 2;
    private GameState gameState = PAUSED;
    private BoardState boardState = BoardState.UNSET;
    private PlayerToken currentPlayer;
    private List<PlayerToken> players = new ArrayList<>();
    private int scoreCross = 0;
    private int scoreCircle = 0;
    private int roundCounter = 0;
    private StringProperty roundSPlayed = new SimpleStringProperty("Round 0");
    private StringProperty crossPoints = new SimpleStringProperty("Score: 0");
    private StringProperty circlePoints = new SimpleStringProperty("Score: 0");
    private Random random = new Random();
    final private GameResults gameResults = new GameResults();

    public String getRoundSPlayed() {
        return roundSPlayed.get();
    }

    public StringProperty roundSPlayedProperty() {
        return roundSPlayed;
    }

    public void setRoundSPlayed(String roundSPlayed) {
        this.roundSPlayed.set(roundSPlayed);
    }

    public void randomFirstMover(){
        int indexRandom = random.nextInt(NUMBER_OF_PLAYERS);
        setCurrentPlayer(players.get(indexRandom));

    }

    public PlayerToken getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerToken currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public String getCrossPoints() {
        return crossPoints.get();
    }

    public StringProperty crossPointsProperty() {
        return crossPoints;
    }

    public void setCrossPoints(String crossPoints) {
        this.crossPoints.set(crossPoints);
    }

    public String getCirclePoints() {
        return circlePoints.get();
    }

    public StringProperty circlePointsProperty() {
        return circlePoints;
    }

    public void setCirclePoints(String circlePoints) {
        this.circlePoints.set(circlePoints);
    }

    public void gameLoop() {
        // initial gameState PAUSE
        // setupGame() Select Players
        // start() ->  gameState Playing
        // move()
        // detectWinner()
        // updateScore()
        // RestGame
        // ExitGame()


    }

    public BoardState getBoardState() {
        return boardState;
    }

    public void setBoardState(BoardState boardState) {
        this.boardState = boardState;
    }

    public void RightPaneButtonEnabledOrDisabled(Button button, BoardState boardState) {
        if (Objects.requireNonNull(boardState) == BoardState.PLAYER_VS_COMPUTER || boardState == BoardState.ONLINE_PLAY || button.getText().equals("Yield")) {
            button.setDisable(true);
        } else if (boardState == BoardState.PLAYER_VS_PLAYER || boardState == BoardState.UNSET) {
            button.setDisable(false);
        }


    }

    public void BoardPaneShower(List<Node> nodes) {
        nodes.forEach(node -> {
            node.setVisible(true);
        });
    }

    public void BoardPaneHider(List<Node> nodes) {
        nodes.forEach(node -> {
            node.setVisible(false);
        });
    }

    public void playerSelected(PlayerToken token, Button button) {
        if (players.size() < NUMBER_OF_PLAYERS) {
            players.add(token);
        }
        button.setDisable(true);
        button.setText("Yield");

    }

    public void playerYieldLost(PlayerToken token, Button button) {
        button.setDisable(true);
        switch (token) {
            case CIRCLE -> setCrossPoints("Score: " + ++scoreCross);
            case CROSS -> setCirclePoints("Score: " + ++scoreCircle);
        }

    }




    public void playerLeftSelected(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        String buttonText = button.getText();
        switch (buttonText) {
            case "Select" -> playerSelected(CIRCLE, button);
            case "Yield" -> playerYieldLost(CIRCLE, button);
        }

    }

    public void playerRightSelected(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        String buttonText = button.getText();
        switch (buttonText) {
            case "Select" -> playerSelected(CROSS, button);
            case "Yield" -> playerYieldLost(CROSS, button);
        }

    }

    public void gamePauseOrActiveStateSwitcher(){
        switch (getGameState()) {
            case PAUSED -> setGameState(PLAYING);
            case PLAYING -> setGameState(PAUSED);
        }


    }

    public void enableStartButton(Button button) {
        if (players.size() == 2) {
            button.setDisable(false);
        }
    }

    public void updateStartButtonText(Button button){
        gamePauseOrActiveStateSwitcher();
        switch (button.getText()) {
            case "Start", "Pause" -> button.setText("Playing");
            case "Playing" -> button.setText("Pause");
        }
    }

//    public boolean detectWinner(){
//
//    }

}
