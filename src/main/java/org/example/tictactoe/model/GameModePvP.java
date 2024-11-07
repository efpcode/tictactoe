package org.example.tictactoe.model;

import static org.example.tictactoe.model.PlayerToken.*;

public class GameModePvP implements GameModal {
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private Model model;

    public GameModePvP(Model model) {
        this.model = model;


    }

    @Override
    public void move(Player playerMove) {
        if() {
            int imageIndexPlayed = playerMove.getLinearRepresentation();
            var boardTile = model.gameResults.getPlayers().get(imageIndexPlayed);
            var imageToUpdateBoard = model.getImageForPlayer();

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
}
