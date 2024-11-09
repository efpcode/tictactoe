package org.example.tictactoe.view.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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
    @FXML private VBox rightPane;
    @FXML private VBox leftPane;
    @FXML private GridPane centerPane;
    private GamePlayController gamePlayController;
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
        model.addButton(0, startButton);
        model.addButton(1, rightButton);
        model.addButton(2, leftButton);
        BoardPaneHider(nodes);


    }


    private void startRandomEvent() {
        Timeline timeLine = new Timeline(
                new KeyFrame(
                        Duration.millis(Math.random() * 1000),
                        (ActionEvent event) -> {
                            GamePlayController.this.clickAtCenterPosition(nodes.getLast());

                        }
                )
        );
        timeLine.play();
    }


    public void quitGame() {
        System.exit(0);

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
        getModel().setGameMode(GameModePvP.createGameModePvP(getModel()));
        BoardPaneShower(nodes);



    }

    public void gamePVNPC(MouseEvent mouseEvent) {
        getModel().setGameMode(GameModePvsCPU.createGameModePvsCPU(getModel()));
        BoardPaneShower(nodes);
    }

    public void gameOnline(MouseEvent mouseEvent) {

        BoardPaneShower(nodes);
    }

    public void playerSelectedLeft(MouseEvent mouseEvent) {
        model.playerLeftSelected(PlayerToken.CIRCLE);
    }

    public void playerSelectedRight(MouseEvent mouseEvent) {
      model.playerRightSelected(PlayerToken.CROSS);
    }

    public void startButtonPressed(MouseEvent mouseEvent) {
        model.startGame();
        if (model.getGameState() == GameState.PLAY) {
            leftButton.setDisable(false);
            rightButton.setDisable(false);
        }

    }




    public void clickAtCenterPosition(Node n) {
        n.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                n.getLayoutX(), n.getLayoutY(), n.getLayoutX(), n.getLayoutY(), MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null));



    }






    public void getImage(MouseEvent mouseEvent) {
        if(model.getGameState() == PLAY) {
            ImageView tileImage = (ImageView) mouseEvent.getSource();
            var rowAndColumn = getGridCoordinates(tileImage.getId());
            var previousPlayer = model.getCurrentPlayer();
            model.getGameMode().move(rowAndColumn);
            if (model.detectWinner(previousPlayer)) {
                model.setGameState(WIN);
                System.out.println(previousPlayer);

            } else if (model.detectDraw()) {
                model.setGameState(DRAW);
                System.out.println("DRAW");

            }
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
