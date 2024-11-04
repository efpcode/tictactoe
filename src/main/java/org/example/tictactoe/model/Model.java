package org.example.tictactoe.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.*;

import static org.example.tictactoe.model.GameState.*;
import static org.example.tictactoe.model.PlayerToken.*;


public class Model {
    private static final int NUMBER_OF_PLAYERS = 2;
    private static final int MATRIX_SIDE_LENGTH = 3;
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

    public ObservableList<Image> getImages() {
        return images.get();
    }

    public ListProperty<Image> imagesProperty() {
        return images;
    }

    public void setImages(ObservableList<Image> images) {
        this.images.set(images);
    }

    private ListProperty<Image> images = new SimpleListProperty<>(FXCollections.observableArrayList());

    Image empty;
    Image robot;
    Image cross;
    Image circle;

    public Model() {
        empty = new Image(getClass().getResource("/org/example/tictactoe/images/empty.png").toExternalForm());
        robot = new Image(getClass().getResource("/org/example/tictactoe/images/robot.png").toExternalForm());
        cross = new Image(getClass().getResource("/org/example/tictactoe/images/cross.png").toExternalForm());
        circle = new Image(getClass().getResource("/org/example/tictactoe/images/circle.png").toExternalForm());

        for (int i = 0; i < (MATRIX_SIDE_LENGTH * MATRIX_SIDE_LENGTH); i++) {
            images.add(empty);

        }

    }


    public String getRoundSPlayed() {
        return roundSPlayed.get();
    }

    public StringProperty roundSPlayedProperty() {
        return roundSPlayed;
    }

    public void setRoundSPlayed(String roundSPlayed) {
        this.roundSPlayed.set(roundSPlayed);
    }

    public void randomFirstMover() {
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
        // if no Player won switchPlayers
        // updateScore()
        // RestGame()
        // ExitGame()


    }

    public BoardState getBoardState() {
        return boardState;
    }

    public void setBoardState(BoardState boardState) {
        this.boardState = boardState;
    }




    public void switchPlayer(){
        var player = getCurrentPlayer();
        if (getGameState()==PLAYING && player.equals(CIRCLE)){
            setCurrentPlayer(CROSS);
        } else if (getGameState()==PLAYING && player.equals(CROSS)) {
            setCurrentPlayer(CIRCLE);

        }
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

    public void gamePauseOrActiveStateSwitcher() {
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

    public void updateStartButtonText(Button button) {
        gamePauseOrActiveStateSwitcher();
        switch (button.getText()) {
            case "Start", "Pause" -> button.setText("Playing");
            case "Playing" -> button.setText("Pause");
        }
    }

    public String isWinner(PlayerToken token) {
        HashMap<PlayerToken, String> winners = new HashMap<>();
        winners.put(CROSS, "cross");
        winners.put(CIRCLE, "circle");
        String boardMessage = "Round " + roundCounter;

        if (detectWinner(token) && getBoardState() == BoardState.PLAYER_VS_COMPUTER && getGameState() == PLAYING) {
            boardMessage += "Computer Won!";

        } else if (detectWinner(token) && getGameState() == PLAYING) {
            boardMessage += "Computer " + winners.getOrDefault(token, "Computer Won!");

        } else if (gameResults.tieGame() && getGameState() == PLAYING) {
            boardMessage += "Draw";

        }

        return boardMessage;


    }

    public boolean detectWinner(PlayerToken token) {
        return gameResults.diagonalWinner(token) || gameResults.rowWinner(token) || gameResults.columnWinner(token) || gameResults.invertedDiagonalWinner(token);
    }

    public void updateGameBoard(Player move){

        int imageIndexPlayed = move.getLinearRepresentation();
        var boardTile = gameResults.getPlayers().get(imageIndexPlayed);
        var imageToUpdateBoard = getImageForPlayer();

        if(boardTile.token()== EMPTY){
            gameResults.addPlayer(move);
            images.set(imageIndexPlayed, imageToUpdateBoard);
            switchPlayer();
        };


    }


    public void pickedGridCell(int row, int column) {
        Player move = Player.of(row, column, getCurrentPlayer());
        updateGameBoard(move);

    }

    public Image getImageForPlayer() {
        if (getCurrentPlayer().equals(CROSS) && getBoardState()==BoardState.PLAYER_VS_COMPUTER)
            return robot;
        else if (getCurrentPlayer().equals(CIRCLE) && getBoardState()==BoardState.PLAYER_VS_PLAYER || getBoardState()==BoardState.PLAYER_VS_COMPUTER)
            return circle;
        else if (getCurrentPlayer().equals(CROSS) && getBoardState()==BoardState.PLAYER_VS_PLAYER)
            return cross;
        else return empty;
    }

    public void startGame() {
        gameResults.initializeGameBoard();
        if (gameResults.isGameBoardEmpty()) {
            randomFirstMover();
        }
    }

}
