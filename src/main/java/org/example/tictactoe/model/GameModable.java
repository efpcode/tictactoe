package org.example.tictactoe.model;

import javafx.scene.image.Image;

public interface GameModable {

    void move (SquaredMatrixCoordinates coordinates);
    void switchPlayer();
    Image getImageForPlayer ();
    GameState getGameType();

}
