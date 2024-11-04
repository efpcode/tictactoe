package org.example.tictactoe.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.tictactoe.model.BoardState;
import org.example.tictactoe.model.GameState;
import org.example.tictactoe.model.Model;
import org.example.tictactoe.model.PlayerToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        BoardPaneHider(nodes);
    }


    public void quitGame() {
        System.exit(0);

    }

    public void RightPaneButtonEnabledOrDisabled(Button button, BoardState boardState) {
        if (Objects.requireNonNull(boardState) == BoardState.PLAYER_VS_COMPUTER || boardState == BoardState.ONLINE_PLAY || button.getText().equals("Yield"))
            button.setDisable(true);
        button.setDisable(false);
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



    public void gamePVP(MouseEvent mouseEvent) {
        model.setBoardState(PLAYER_VS_PLAYER);
        RightPaneButtonEnabledOrDisabled(rightButton, PLAYER_VS_PLAYER);
        BoardPaneShower(nodes);

    }

    public void gamePVNPC(MouseEvent mouseEvent) {
        model.setBoardState(PLAYER_VS_COMPUTER);
        RightPaneButtonEnabledOrDisabled(rightButton, PLAYER_VS_COMPUTER);
        model.playerSelected(PlayerToken.CROSS, rightButton);
        BoardPaneShower(nodes);
    }

    public void gameOnline(MouseEvent mouseEvent) {
        model.setBoardState(ONLINE_PLAY);
        RightPaneButtonEnabledOrDisabled(rightButton, ONLINE_PLAY);
        BoardPaneShower(nodes);
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


    public void getImage(MouseEvent mouseEvent) {
        if(model.getGameState() == GameState.PLAYING) {
            ImageView tileImage = (ImageView) mouseEvent.getSource();
            var rowAndColumn = getGridCoordinates(tileImage.getId());
            model.pickedGridCell(rowAndColumn.getFirst(), rowAndColumn.getLast());
        }

    }

    public List<Integer> getGridCoordinates(String imageName) {
        List<Integer> coordinates = new ArrayList<>(2);
        switch (imageName) {
            case "image0" -> coordinates.addAll(List.of(0, 0));
            case "image1" -> coordinates.addAll(List.of(0, 1));
            case "image2" -> coordinates.addAll(List.of(0, 2));
            case "image3" -> coordinates.addAll(List.of(1, 0));
            case "image4" -> coordinates.addAll(List.of(1, 1));
            case "image5" -> coordinates.addAll(List.of(1, 2));
            case "image6" -> coordinates.addAll(List.of(2, 0));
            case "image7" -> coordinates.addAll(List.of(2, 1));
            case "image8" -> coordinates.addAll(List.of(2, 2));
        }

        return coordinates;

    }
}
