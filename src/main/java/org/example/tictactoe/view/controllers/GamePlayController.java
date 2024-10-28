package org.example.tictactoe.view.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.tictactoe.model.Model;

import java.util.ArrayList;
import java.util.List;

import static org.example.tictactoe.model.BoardState.*;

public class GamePlayController  {


    public Button rightButton;
    public Button leftButton;
    @FXML
    private VBox rightPane;
    @FXML private VBox leftPane;
    @FXML private GridPane centerPane;
    private final List<Node> nodes = new ArrayList<>();

    Model model = new Model();

    public void initialize() {
        nodes.add(rightPane);
        nodes.add(leftPane);
        nodes.add(centerPane);
        model.BoardPaneHider(nodes);
        model.setBoardState(UNSET);
    }


    public void quitGame() {
        System.exit(0);

    }

    public void gamePVP(MouseEvent mouseEvent) {
        model.setBoardState(PLAYER_VS_PLAYER);
        model.RightPaneButtonEnabledOrDisabled(rightButton, PLAYER_VS_PLAYER);
        model.BoardPaneShower(nodes);

    }

    public void gamePVNPC(MouseEvent mouseEvent) {
        model.setBoardState(PLAYER_VS_COMPUTER);
        model.RightPaneButtonEnabledOrDisabled(rightButton, PLAYER_VS_COMPUTER);
        model.BoardPaneShower(nodes);
    }

    public void gameOnline(MouseEvent mouseEvent) {
        model.setBoardState(ONLINE_PLAY);
        model.RightPaneButtonEnabledOrDisabled(rightButton, ONLINE_PLAY);
        model.BoardPaneShower(nodes);
    }
}
