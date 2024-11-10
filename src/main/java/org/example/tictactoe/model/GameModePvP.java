package org.example.tictactoe.model;

import javafx.scene.image.Image;

import static org.example.tictactoe.model.GameState.*;
import static org.example.tictactoe.model.PlayerToken.*;

public class GameModePvP implements GameModable {


    private final GameState isType = PvsP;
    private Model model;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }



    public GameModePvP (Model model) {
         this.model = model;

    }

    public static GameModePvP createGameModePvP(Model model) {
        return new GameModePvP(model);
    }

    @Override
    public void move(SquaredMatrixCoordinates coordinates) {
        Player playerMove = Player.valueOf(coordinates, model.getCurrentPlayer());
        if(model.getGameState() == PLAY ) {
            int imageIndexPlayed = playerMove.getLinearRepresentation();
            var boardTile = model.gameResults.getPlayers().get(imageIndexPlayed);
            var imageToUpdateBoard = getImageForPlayer();

            if (boardTile.token() == EMPTY) {
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
            case CROSS -> model.cross;
            case CIRCLE -> model.circle;
            case EMPTY ->  model.empty;
        };
    }

    @Override
    public GameState getGameType() {
        return isType;
    }
}
