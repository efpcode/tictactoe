package org.example.tictactoe.model;

import javafx.scene.image.Image;

import java.util.Random;

import static org.example.tictactoe.model.GameState.*;
import static org.example.tictactoe.model.PlayerToken.*;

public class GameModePvsCPU implements GameModable {


    protected final GameState isType = PvsCPU;
    private Random random = new Random();
    private Model model;



    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;

    }


    public GameModePvsCPU(Model model) {
        this.model = model;

    }

    public static GameModePvsCPU createGameModePvsCPU(Model model) {
        return new GameModePvsCPU(model);
    }

    public SquaredMatrixCoordinates cpuMove() {
        var availablePositions = model.gameResults.availablePositions();
        if (availablePositions.isEmpty()) {
            model.setGameState(DRAW);
            return new SquaredMatrixCoordinates(0, 0);
        }
        var positionIndex = random.nextInt(availablePositions.size());
        if (model.gameResults.isGameBoardEmpty())
            positionIndex = 4;
        var position = availablePositions.get(positionIndex);
        return new SquaredMatrixCoordinates(position.row(), position.column());


    }

    @Override
    public void move(SquaredMatrixCoordinates coordinates) {
        if (model.getCurrentPlayer().equals(CROSS)) {
            coordinates = cpuMove();
        }
        Player playerMove = Player.valueOf(coordinates, model.getCurrentPlayer());
        if (model.getGameState().equals(PLAY)) {
            int imageIndexPlayed = playerMove.getLinearRepresentation();
            var boardTile = model.gameResults.getPlayers().get(imageIndexPlayed);
            var imageToUpdateBoard = getImageForPlayer();

            if (boardTile.token().equals(EMPTY)) {
                model.gameResults.addPlayer(playerMove);
                model.images.set(imageIndexPlayed, imageToUpdateBoard);
                this.switchPlayer();

            }
        }
    }


    @Override
    public void switchPlayer() {
        switch (model.getCurrentPlayer()) {
            case CIRCLE -> model.setCurrentPlayer(CROSS);
            case CROSS -> model.setCurrentPlayer(CIRCLE);
        }

    }

    @Override
    public Image getImageForPlayer() {
        return switch (model.getCurrentPlayer()) {
            case CROSS -> model.robot;
            case CIRCLE -> model.circle;
            case EMPTY -> model.empty;
        };
    }

    @Override
    public GameState getGameType() {
        return isType;
    }


}
