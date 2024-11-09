package org.example.tictactoe.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.util.*;

import static org.example.tictactoe.model.GameState.*;


public class Model {
    private static final int NUMBER_OF_PLAYERS = 2;
    private static final int MATRIX_SIDE_LENGTH = 3;

    private GameState gameState = PAUSE;
    private PlayerToken currentPlayer;
    private Set<PlayerToken> players = new HashSet<>();
    private Random random = new Random();
    private GameModable gameMode;

    private int scoreCross = 0;
    private int scoreCircle = 0;
    private int roundCounter = 0;
    private String startButtonText = "Start";
    private String leftButtonText = "Select";
    private String rightButtonText = "Select";


    public String getCircleSelect() {
        return circleSelect.get();
    }

    public StringProperty circleSelectProperty() {
        return circleSelect;
    }

    public void setCircleSelect(String circleSelect) {
        this.circleSelect.set(circleSelect);
    }

    public String getCrossSelect() {
        return crossSelect.get();
    }

    public StringProperty crossSelectProperty() {
        return crossSelect;
    }

    public void setCrossSelect(String crossSelect) {
        this.crossSelect.set(crossSelect);
    }

    public String getStartButtonSelect() {
        return startButtonSelect.get();
    }

    public StringProperty startButtonSelectProperty() {
        return startButtonSelect;
    }

    public void setStartButtonSelect(String startButtonSelect) {
        this.startButtonSelect.set(startButtonSelect);
    }

    private StringProperty startButtonSelect = new SimpleStringProperty(startButtonText);
    private StringProperty circleSelect = new SimpleStringProperty(leftButtonText);
    private StringProperty crossSelect = new SimpleStringProperty(rightButtonText);
    private StringProperty roundSPlayed = new SimpleStringProperty("Round 0");
    private StringProperty crossPoints = new SimpleStringProperty("Score: 0");
    private StringProperty circlePoints = new SimpleStringProperty("Score: 0");

    protected ListProperty<Image> images = new SimpleListProperty<>(FXCollections.observableArrayList());
    protected ListProperty<Button> buttons = new SimpleListProperty<>(FXCollections.observableArrayList());
    protected final GameResults gameResults = new GameResults();

    Image empty;
    Image robot;
    Image cross;
    Image circle;

    public GameModable getGameMode() {
        return gameMode;
    }


    public void setGameMode(GameModable gameMode) {
        this.gameMode = gameMode;
    }


    public ObservableList<Image> getImages() {
        return images.get();
    }


    public ListProperty<Image> imagesProperty() {
        return images;
    }


    public void setImages(ObservableList<Image> images) {
        this.images.set(images);
    }


    public Model() {
        empty = new Image(getClass().getResource("/org/example/tictactoe/images/empty.png").toExternalForm());
        robot = new Image(getClass().getResource("/org/example/tictactoe/images/robot.png").toExternalForm());
        cross = new Image(getClass().getResource("/org/example/tictactoe/images/cross.png").toExternalForm());
        circle = new Image(getClass().getResource("/org/example/tictactoe/images/circle.png").toExternalForm());

        for (int i = 0; i < (MATRIX_SIDE_LENGTH * MATRIX_SIDE_LENGTH); i++) {
            images.add(empty);

        }

    }


    public ObservableList<Button> getButtons() {
        return buttons.get();
    }


    public ListProperty<Button> buttonsProperty() {
        return buttons;
    }


    public void setButtons(ObservableList<Button> buttons) {
        this.buttons.set(buttons);
    }

    public void addButton(int index, Button button) {
        buttons.add(index, button);
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
        setCurrentPlayer(players.stream().toList().get(indexRandom));

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


    public void playerSelected(PlayerToken token) {
        if (players.size() <= NUMBER_OF_PLAYERS) {
            players.add(token);
        }
        if(players.size()== NUMBER_OF_PLAYERS) {
            buttons.getFirst().setDisable(false);
        }
    }


    public void playerYieldLost(PlayerToken token) {
        switch (token) {
            case CIRCLE -> setCrossPoints("Score: " + ++scoreCross);
            case CROSS -> setCirclePoints("Score: " + ++scoreCircle);
        }

    }


    public void playerLeftSelected(PlayerToken token) {
      playerSelected(token);
      setCircleSelect("Yield");

    }


    public void playerRightSelected(PlayerToken token) {
        playerSelected(token);
        setCrossSelect("Yield");
        }


    public void gamePauseOrActiveStateSwitcher() {
        switch (getGameState()) {
            case PAUSE -> setGameState(PLAY);
            case PLAY -> setGameState(PAUSE);
        }


    }


    public boolean detectWinner(PlayerToken token) {
        return gameResults.diagonalWinner(token) || gameResults.rowWinner(token) || gameResults.columnWinner(token) || gameResults.invertedDiagonalWinner(token);
    }

    public boolean detectDraw(){
        return gameResults.tieGame();
    }


    public void startGame() {
        gamePauseOrActiveStateSwitcher();
        switch (getStartButtonSelect()){
            case "Start", "Play" -> setStartButtonSelect("Pause");
            case "Pause" -> setStartButtonSelect("Play");

        }
        gameResults.initializeGameBoard();
        if (gameResults.isGameBoardEmpty()) {
            randomFirstMover();
        }
    }

}
