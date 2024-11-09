package org.example.tictactoe.view.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.tictactoe.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.example.tictactoe.model.GameState.*;

public class GamePlayController  {


    public Button startButton;
    public Button rightButton;
    public Button leftButton;
    public Button PvPfx;
    public Button PvCPUfx;
    public Label labelCircle;
    public Label labelCross;
    @FXML private VBox rightPane;
    @FXML private VBox leftPane;
    @FXML private GridPane centerPane;
    private final List<Node> nodes = new ArrayList<>();
    private Model model = new Model();
    public Model getModel() {
        return model;
    }

    public void initialize() {
        nodes.add(rightPane);
        nodes.add(leftPane);
        nodes.add(centerPane);
        startButton.setDisable(true);
        boardPaneHider(nodes);
        gameLoop();


    }

    private void gameLoop() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(Math.random()*3000),
                        (ActionEvent event) -> {

                            if(model.getGameState().equals(PLAY) && model.getGameMode().getGameType().equals(PvsCPU) && model.getCurrentPlayer().equals(PlayerToken.CROSS)){
                            var coordinates = getGridCoordinates("image4");
                            model.playerMove(coordinates);
                            labelHighlighter(model.getCurrentPlayer());

                            }
                            GamePlayController.this.gameLoop();

                        }
                        )
        );
        timeline.play();
    }

    public void quitGame() {
        System.exit(0);

    }



    public void boardPaneShower(List<Node> nodes) {
        nodes.forEach(node -> {
            node.setVisible(true);
        });
    }

    public void boardPaneHider(List<Node> nodes) {
        nodes.forEach(node -> {
            node.setVisible(false);
        });
    }



    public void gamePVP(MouseEvent mouseEvent) {
        getModel().setGameMode(GameModePvP.createGameModePvP(getModel()));
        boardPaneShower(nodes);
        PvCPUfx.setDisable(true);



    }

    public void gamePVNPC(MouseEvent mouseEvent) {
        boardPaneShower(nodes);
        PvPfx.setDisable(true);
        getModel().setGameMode(GameModePvsCPU.createGameModePvsCPU(getModel()));
        model.playerRightSelected(PlayerToken.CROSS);
        model.playerLeftSelected(PlayerToken.CIRCLE);
        disableButton(rightButton);
        disableButton(leftButton);
        leftButton.setVisible(false);
        rightButton.setVisible(false);
        enableStartButton();
    }

    public void gameRest(MouseEvent mouseEvent) {
        model.getGameResults().removeAllPlayers();
        model.setAllImagesEmpty();
        model.updateBanner();
        model.setGameState(PAUSE);
        model.setStartButtonSelect("Play");
        startButton.setDisable(false);
        enableButton(leftButton);
        enableButton(rightButton);






    }

    public void playerSelectedLeft(MouseEvent mouseEvent) {
        model.playerLeftSelected(PlayerToken.CIRCLE);
        enableStartButton();
        disableButton(leftButton);
        model.playerYieldLost(PlayerToken.CROSS);
        playerSelectDisableOrEnable(leftButton);
        playerSelectDisableOrEnable(rightButton);
    }

    public void playerSelectedRight(MouseEvent mouseEvent) {
      model.playerRightSelected(PlayerToken.CROSS);
      enableStartButton();
      disableButton(rightButton);
      model.playerYieldLost(PlayerToken.CIRCLE);
      playerSelectDisableOrEnable(rightButton);
      playerSelectDisableOrEnable(leftButton);


    }

    public void playerSelectDisableOrEnable(Button button){
        if(model.getGameState().equals(WIN) || model.getGameState().equals(DRAW))
            disableButton(button);
        else if(model.getGameState().equals(PLAY))
            enableButton(button);

    }

    public void startButtonPressed(MouseEvent mouseEvent) {
        model.startGame();
        if (model.getGameState() == GameState.PLAY) {
            labelHighlighter(model.getCurrentPlayer());

            enableButton(leftButton);
            enableButton(rightButton);
        }

    }


    public Button getLeftButton() {
        return leftButton;
    }

    public Button getRightButton() {
        return rightButton;
    }

    public void enableStartButton() {
        if (getRightButton().getText().equals("Yield") && getLeftButton().getText().equals("Yield")) {
            startButton.setDisable(false);
        }
    }

    public void disableButton(Button button) {
        button.setDisable(true);

    }

    public void enableButton(Button button) {
        button.setDisable(false);
    }


    public void getImage(MouseEvent mouseEvent) {
        if(model.getGameState() == PLAY) {
            ImageView tileImage = (ImageView) mouseEvent.getSource();
            var rowAndColumn = getGridCoordinates(tileImage.getId());
            model.playerMove(rowAndColumn);
            labelHighlighter(model.getCurrentPlayer());

        }

    }

    public void labelHighlight(Label label) {
        label.setStyle("-fx-effect: dropshadow(three-pass-box, darkcyan, 20, 0.68, 2,2) ");
    }

    public void labelDeHighlight(Label label) {
        label.setStyle("-fx-background-color: none;");
    }

    public void labelHighlighter(PlayerToken playerToken) {
        if(playerToken == PlayerToken.CROSS) {
            labelHighlight(labelCross);
            labelDeHighlight(labelCircle);
        }else if(playerToken == PlayerToken.CIRCLE){
            labelHighlight(labelCircle);
            labelDeHighlight(labelCross);
        }
    }



    public SquaredMatrixCoordinates getGridCoordinates(String imageName) {
       return switch (imageName) {
            case "image0" -> new SquaredMatrixCoordinates(0, 0);
            case "image1" -> new SquaredMatrixCoordinates(0, 1);
            case "image2" -> new SquaredMatrixCoordinates(0, 2);
            case "image3" -> new SquaredMatrixCoordinates(1, 0);
            case "image4" -> new SquaredMatrixCoordinates(1, 1);
            case "image5" -> new SquaredMatrixCoordinates(1, 2);
            case "image6" -> new SquaredMatrixCoordinates(2, 0);
            case "image7" -> new SquaredMatrixCoordinates(2, 1);
            case "image8" -> new SquaredMatrixCoordinates(2, 2);
           case null, default -> throw new IllegalStateException("Unexpected value: " + imageName);
        };

    }
}
