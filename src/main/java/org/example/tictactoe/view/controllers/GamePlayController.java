package org.example.tictactoe.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.tictactoe.model.GameState;
import org.example.tictactoe.model.Model;
import org.example.tictactoe.model.PlayerToken;

import java.util.ArrayList;
import java.util.List;

import static org.example.tictactoe.model.BoardState.*;

public class GamePlayController  {


    public Button startButton;
    public Button rightButton;
    public Button leftButton;
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
        model.BoardPaneHider(nodes);
    }


    public void quitGame() {
        System.exit(0);

    }
    public void getImageOne(MouseEvent mouseEvent) {

        model.pickedGridCell(0,0);

    }

    public void getImageTwo(MouseEvent mouseEvent) {
        model.pickedGridCell(0,1);

    }

    public void getImageThree(MouseEvent mouseEvent) {

        model.pickedGridCell(0,2);

    }

    public void getImageFour(MouseEvent mouseEvent) {

        model.pickedGridCell(1,0);

    }

    public void getImageFive(MouseEvent mouseEvent) {
        model.pickedGridCell(1,1);

    }

    public void getImageSix(MouseEvent mouseEvent) {

        model.pickedGridCell(1,2);

    }
    public void getImageSeven(MouseEvent mouseEvent) {

        model.pickedGridCell(2,0);

    }

    public void getImageEight(MouseEvent mouseEvent) {
        model.pickedGridCell(2,1);

    }

    public void getImageNine(MouseEvent mouseEvent) {

        model.pickedGridCell(2,2);

    }


    public void gamePVP(MouseEvent mouseEvent) {
        model.setBoardState(PLAYER_VS_PLAYER);
        model.RightPaneButtonEnabledOrDisabled(rightButton, PLAYER_VS_PLAYER);
        model.BoardPaneShower(nodes);

    }

    public void gamePVNPC(MouseEvent mouseEvent) {
        model.setBoardState(PLAYER_VS_COMPUTER);
        model.RightPaneButtonEnabledOrDisabled(rightButton, PLAYER_VS_COMPUTER);
        model.playerSelected(PlayerToken.CROSS, rightButton);
        model.BoardPaneShower(nodes);
    }

    public void gameOnline(MouseEvent mouseEvent) {
        model.setBoardState(ONLINE_PLAY);
        model.RightPaneButtonEnabledOrDisabled(rightButton, ONLINE_PLAY);
        model.BoardPaneShower(nodes);
    }

    public void playerSelectedLeft(MouseEvent mouseEvent) {
        model.playerLeftSelected(mouseEvent);
        model.enableStartButton(startButton);
    }

    public void playerSelectedRight(MouseEvent mouseEvent) {
        model.playerRightSelected(mouseEvent);
        model.enableStartButton(startButton);
    }

    public void startButtonPressed(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        model.updateStartButtonText(button);
        model.startGame();
        if (model.getGameState() == GameState.PLAYING){
            leftButton.setDisable(false);
            rightButton.setDisable(false);
        }

    }


}
