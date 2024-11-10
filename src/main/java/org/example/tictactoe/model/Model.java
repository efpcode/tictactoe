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
import java.util.stream.Collectors;

import static org.example.tictactoe.model.GameState.*;


public class Model {
    private static final int NUMBER_OF_PLAYERS = 2;
    private static final int MATRIX_SIDE_LENGTH = 3;

    private GameState gameState = PAUSE;
    private PlayerToken currentPlayer;
    private List<PlayerToken> players = new ArrayList<>();
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

    public GameResults getGameResults() {
        return gameResults;
    }

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

    public Model(Random random){
        this.random = random;
        empty = new Image(getClass().getResource("/org/example/tictactoe/images/test.png").toExternalForm());
        robot = new Image(getClass().getResource("/org/example/tictactoe/images/test.png").toExternalForm());
        cross = new Image(getClass().getResource("/org/example/tictactoe/images/test.png").toExternalForm());
        circle = new Image(getClass().getResource("/org/example/tictactoe/images/test.png").toExternalForm());

        for (int i = 0; i < MATRIX_SIDE_LENGTH*MATRIX_SIDE_LENGTH ; i++) {
            images.add(empty);

        }

    }


    public Model() {
        empty = new Image(getClass().getResource("/org/example/tictactoe/images/empty2.png").toExternalForm());
        robot = new Image(getClass().getResource("/org/example/tictactoe/images/robot.png").toExternalForm());
        cross = new Image(getClass().getResource("/org/example/tictactoe/images/cross.png").toExternalForm());
        circle = new Image(getClass().getResource("/org/example/tictactoe/images/circle.png").toExternalForm());
        addAllImagesEmpty();

    }

    private void addAllImagesEmpty() {
        for (int i = 0; i < (MATRIX_SIDE_LENGTH * MATRIX_SIDE_LENGTH); i++) {
            images.add(empty);

        }
    }
    public void setAllImagesEmpty() {
        for (int i = 0; i < (MATRIX_SIDE_LENGTH * MATRIX_SIDE_LENGTH); i++) {
            images.set(i, empty);
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


    public void playerSelected(PlayerToken token) {
        if (players.size() < NUMBER_OF_PLAYERS) {
            players.add(token);
        }

    }


    public void playerYieldLost(PlayerToken token) {
        switch (token) {
            case PlayerToken.CIRCLE -> isWinner(WIN, PlayerToken.CIRCLE);
            case PlayerToken.CROSS -> isWinner(WIN, PlayerToken.CROSS);
        }

    }

    public String updateBanner(GameState state, PlayerToken token) {
        String prompt = "Winner is: ";

        if(state.equals(WIN) && gameMode.getGameType().equals(PvsCPU)){
           String winner = token.equals(PlayerToken.CROSS) ? "CPU":"CIRCLE";
           prompt += winner;

        } else if (state.equals(WIN) && gameMode.getGameType().equals(PvsP)) {
            String winner = token.equals(PlayerToken.CROSS) ? "CROSS":"CIRCLE";
            prompt += winner;
        }
        return prompt;

    }

    public void isWinner(GameState state, PlayerToken token){
        if(state.equals(DRAW)){
            updateBanner(DRAW);
        } else if (state.equals(WIN)) {
            var winner = updateBanner(state, token);
            if(token.equals(PlayerToken.CROSS) && getGameState().equals(PLAY)){
                scoreCross++;
                setCrossPoints("Score: "+ scoreCross);
            }else if(token.equals(PlayerToken.CIRCLE) && getGameState().equals(PLAY)) {
                scoreCircle++;
                setCirclePoints("Score: " + scoreCircle);
            }
            if(getGameState().equals(PLAY)){
                roundCounter++;
                setRoundSPlayed("Round: "+ roundCounter +" || " + winner);
                setGameState(WIN);
            }
        }


    }

    public void updateBanner(){
        String banner = roundSPlayed.get();
        String [] neturalBanner = banner.split("\\|");
        setRoundSPlayed(neturalBanner[0]);
    }

    public void updateBanner(GameState state){
        if(state.equals(DRAW) && getGameState().equals(PLAY)){
            roundCounter++;
            setRoundSPlayed("Round: " + roundCounter+" || "+ "DRAW");
            setGameState(DRAW);

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

    public void playerMove(SquaredMatrixCoordinates coordinates) {
        var previousPlayer = getCurrentPlayer();
        getGameMode().move(coordinates);

        if (detectWinner(previousPlayer)) {
            isWinner(WIN, previousPlayer);


        } else if (detectDraw()) {
            updateBanner(DRAW);

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
